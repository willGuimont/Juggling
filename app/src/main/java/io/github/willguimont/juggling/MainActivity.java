package io.github.willguimont.juggling;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.IOException;

import io.github.willguimont.juggling.sound.LoudSoundModel;
import io.github.willguimont.juggling.ui.main.MainStatePagerAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int RECORD_AUDIO_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasPermissions()) {
            init_main_view();
        } else {
            setContentView(R.layout.ask_permission);
            Button askPermissionButton = findViewById(R.id.ask_permission_button);
            askPermissionButton.setOnClickListener(v ->
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasPermissions()) {
            init_main_view();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init_main_view();
            }
        }
    }

    private boolean hasPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void init_main_view() {
        setContentView(R.layout.activity_main);
        MainStatePagerAdapter mainStatePagerAdapter = new MainStatePagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mainStatePagerAdapter);
        viewPager.setOffscreenPageLimit(0);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        try {
            File tmpFile = File.createTempFile("tmp.mp3", null, getApplicationContext().getCacheDir());
            LoudSoundModel.setGlobalTmpOutputFile(tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not create temp file");
        }
    }
}