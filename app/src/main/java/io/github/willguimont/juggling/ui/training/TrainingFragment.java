package io.github.willguimont.juggling.ui.training;

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

public class TrainingFragment extends Fragment {

    private TrainingModel trainingModel;
    private LoudSoundModel loudSoundModel;

    public static TrainingFragment newInstance() {
        return new TrainingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider viewModelProvider = new ViewModelProvider(getActivity());
        trainingModel = viewModelProvider.get(TrainingModel.class);
        trainingModel.reset();
        loudSoundModel = viewModelProvider.get(LoudSoundModel.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        loudSoundModel.setOnLoudSoundDelayMs(1000);
        loudSoundModel.setOnLoudSoundAction(() -> trainingModel.drop());
        loudSoundModel.setPollIntervalMs(100);
        loudSoundModel.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        loudSoundModel.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        loudSoundModel.stop();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.training_page, container, false);

        final TextView numDropsTextView = root.findViewById(R.id.train_number_of_drops_label);
        trainingModel.getNumberOfDrops().observe(
                getViewLifecycleOwner(),
                (numDrops) -> numDropsTextView.setText(String.format(Locale.getDefault(),
                        "%d", numDrops)));

        final Button resetButton = root.findViewById(R.id.train_reset_button);
        resetButton.setOnClickListener((view) -> {
            trainingModel.reset();
            loudSoundModel.reset();
        });

        return root;
    }
}