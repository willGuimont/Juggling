package io.github.willguimont.juggling.ui.time;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import io.github.willguimont.juggling.R;
import io.github.willguimont.juggling.sound.LoudSoundModel;

public class TimeNoDropFragment extends Fragment {

    private TimeNoDropModel timeNoDropModel;
    private LoudSoundModel loudSoundModel;
    private Button resetButton;

    public static TimeNoDropFragment newInstance() {
        return new TimeNoDropFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider viewModelProvider = new ViewModelProvider(getActivity());
        timeNoDropModel = viewModelProvider.get(TimeNoDropModel.class);
        loudSoundModel = viewModelProvider.get(LoudSoundModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        loudSoundModel.setOnLoudSoundAction(() -> {
            timeNoDropModel.stop();
            resetButton.setText(R.string.time_no_drop_button_start);
        });
        loudSoundModel.setPollIntervalMs(10);
        loudSoundModel.setOnLoudSoundDelayMs(100);
        loudSoundModel.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        stop();
    }

    private void stop() {
        timeNoDropModel.reset();
        resetButton.setText(R.string.time_no_drop_button_start);
        loudSoundModel.stop();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.time_no_drop, container, false);
        resetButton = root.findViewById(R.id.no_drop_button);
        resetButton.setOnClickListener(v -> {
            if (timeNoDropModel.getIsRunning()) {
                timeNoDropModel.reset();
                resetButton.setText(R.string.time_no_drop_button_start);
            } else {
                timeNoDropModel.start();
                resetButton.setText(R.string.time_no_drop_button_reset);
            }
        });

        final TextView timeNoDropTextView = root.findViewById(R.id.time_no_drop);
        timeNoDropModel.getNoDropMs().observe(getViewLifecycleOwner(), ms -> {
            long dd = (ms / 10) % 100;
            long seconds = ms / 1000;
            long ss = seconds % 60;
            long mm = seconds / 60;

            timeNoDropTextView.setText(String.format(Locale.getDefault(), "%02d:%02d.%02d", mm, ss, dd));
        });

        return root;
    }
}