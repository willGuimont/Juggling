package io.github.willguimont.juggling.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import io.github.willguimont.juggling.R;
import io.github.willguimont.juggling.ui.about.AboutFragment;
import io.github.willguimont.juggling.ui.time.TimeNoDropFragment;
import io.github.willguimont.juggling.ui.training.TrainingFragment;


public class MainStatePagerAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.tab_text_training,
            R.string.tab_text_time_no_drop,
            R.string.tab_text_about
    };
    private final Context context;

    public MainStatePagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TrainingFragment.newInstance();
            case 1:
                return TimeNoDropFragment.newInstance();
            case 2:
                return AboutFragment.newInstance();
        }
        throw new RuntimeException("Invalid item number");
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }
}