package io.github.willguimont.juggling.ui.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import io.github.willguimont.juggling.R;
import io.github.willguimont.juggling.sound.SoundMeter;

public class TrainingFragment extends Fragment {

    private static final int RECORD_AUDIO_PERMISSION = 0;
    private static final int POLL_INTERVAL = 200;

    private TrainingModel trainingModel;

    private boolean isRunning = false;
    private Handler handler = new Handler();
    private SoundMeter soundMeter;
    private double threshold = 6;

    private Runnable sleepTask = () -> {
        start();
    };

    private Runnable pollTask = new Runnable() {
        @Override
        public void run() {
            double amp = soundMeter.getAmplitude();

            if (amp > threshold) {
                trainingModel.drop();
            }

            handler.postDelayed(pollTask, POLL_INTERVAL);
        }
    };

    public static TrainingFragment newInstance() {
        return new TrainingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trainingModel = new ViewModelProvider(this).get(TrainingModel.class);
        trainingModel.reset();
        createSoundMeter();
    }

    private void createSoundMeter() {
        FragmentActivity activity = getActivity();
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION);
            return;
        }
        if (soundMeter == null) {
            soundMeter = new SoundMeter();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        createSoundMeter();

        if (!isRunning) {
            isRunning = true;
            start();
        }
    }

    private void start() {
        soundMeter.start();
        handler.postDelayed(pollTask, POLL_INTERVAL);
    }

    @Override
    public void onStop() {
        super.onStop();

        stop();
    }

    private void stop() {
        handler.removeCallbacks(sleepTask);
        handler.removeCallbacks(pollTask);
        soundMeter.stop();
        isRunning = false;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.training_page, container, false);
        final TextView textView = root.findViewById(R.id.number_of_drops_label);
        trainingModel.getNumberOfDrops().observe(getViewLifecycleOwner(), (numDrops) -> {
            textView.setText(String.format(Locale.getDefault(), "%d", numDrops));
        });
        return root;
    }
}