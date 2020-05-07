package io.github.willguimont.juggling.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.github.willguimont.juggling.R;

public class TimeNoDropFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private TimeNoDropModel timeNoDropModel;

    static TimeNoDropFragment newInstance() {
        return new TimeNoDropFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeNoDropModel = new ViewModelProvider(this).get(TimeNoDropModel.class);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_no_drop, container, false);
    }
}