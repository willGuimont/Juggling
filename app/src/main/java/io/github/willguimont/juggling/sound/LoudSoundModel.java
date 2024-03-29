package io.github.willguimont.juggling.sound;

import android.os.Handler;

import androidx.lifecycle.ViewModel;

import java.io.File;

public class LoudSoundModel extends ViewModel {
    private static final double THRESHOLD = 6;

    private final Handler handler = new Handler();
    private final SoundMeter soundMeter;
    private Runnable onLoudSoundAction;
    private long pollIntervalMs;
    private long onLoudSoundDelayMs;
    private long timeAtLastLoudMs;
    private static File tmpFile;

    private final Runnable pollTask = new Runnable() {
        @Override
        public void run() {
            double amp = soundMeter.getAmplitude();

            long currentTime = System.currentTimeMillis();
            if (currentTime > timeAtLastLoudMs + onLoudSoundDelayMs && amp > THRESHOLD) {
                onLoudSoundAction.run();
                timeAtLastLoudMs = currentTime;
            }

            handler.postDelayed(pollTask, pollIntervalMs);
        }
    };

    public static void setGlobalTmpOutputFile(File tmpFile) {
        LoudSoundModel.tmpFile = tmpFile;
    }

    public LoudSoundModel() {
        soundMeter = new SoundMeter(tmpFile);
        pollIntervalMs = 100;
        onLoudSoundAction = () -> {
        };
        onLoudSoundDelayMs = 0;
        timeAtLastLoudMs = 0;
    }

    public void start() {
        soundMeter.start();
        reset();
        handler.postDelayed(pollTask, 0);
    }

    public void stop() {
        handler.removeCallbacks(pollTask);
    }

    public void reset() {
        timeAtLastLoudMs = 0;
    }

    public void setPollIntervalMs(long intervalMs) {
        pollIntervalMs = intervalMs;
    }

    public void setOnLoudSoundAction(Runnable action) {
        stop();
        onLoudSoundAction = action;
    }

    public void setOnLoudSoundDelayMs(long onLoudSoundDelayMs) {
        this.onLoudSoundDelayMs = onLoudSoundDelayMs;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        release();
    }

    private void release() {
        soundMeter.stop();
    }
}
