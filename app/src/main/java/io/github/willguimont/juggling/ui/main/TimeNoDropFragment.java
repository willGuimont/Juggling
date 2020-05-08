package io.github.willguimont.juggling.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.github.willguimont.juggling.R;
import io.github.willguimont.juggling.sound.LoudSoundDetector;

public class TimeNoDropFragment extends Fragment {

    private TimeNoDropModel timeNoDropModel;
    private LoudSoundDetector loudSoundDetector;

    static TimeNoDropFragment newInstance() {
        return new TimeNoDropFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeNoDropModel = new ViewModelProvider(this).get(TimeNoDropModel.class);
        loudSoundDetector = new LoudSoundDetector(
                TimeNoDropModel.TICK_DURATION_MS,
                this::onDrop,
                0);
    }

    private void onDrop() {
        // TODO set button text to Reset on drop
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.time_no_drop, container, false);
        final Button resetButton = root.findViewById(R.id.no_drop_button);
        resetButton.setOnClickListener((view) -> {
            // TODO change text
            // Start -> Reset -> Start
        });


        final TextView timeNoDropTextView = root.findViewById(R.id.time_no_drop);
        timeNoDropModel.getDeciseconds().observe(
                getViewLifecycleOwner(),
                (deciseconds) -> {
                    // TODO format deciseconds to hh:mm.uu
                });

        return root;
    }
}