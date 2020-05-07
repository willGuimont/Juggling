package io.github.willguimont.juggling.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import io.github.willguimont.juggling.R;


public class HomeFragment extends Fragment {

    static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page, container, false);
    }
}