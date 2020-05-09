package io.github.willguimont.juggling.sound;

import android.os.Handler;

public class LoudSoundDetector {
    private static final double THRESHOLD = 6;

    private Handler handler = new Handler();
    private SoundMeter soundMeter;
    private Runnable onLoudSoundAction;
    private long pollIntervalMs;
    private long onLoudSoundDelayMs;
    private long timeAtLastLoudMs;

    private Runnable pollTask = new Runnable() {
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

    public LoudSoundDetector(long pollIntervalMs, Runnable onLoudSoundAction, long onLoudSoundDelayMs) {
        soundMeter = new SoundMeter();
        this.pollIntervalMs = pollIntervalMs;
        this.onLoudSoundAction = onLoudSoundAction;
        this.onLoudSoundDelayMs = onLoudSoundDelayMs;
        timeAtLastLoudMs = 0;
    }

    public void start() {
        soundMeter.start();
        reset();
        handler.postDelayed(pollTask, 0);
    }

    public void stop() {
        handler.removeCallbacks(pollTask);
        soundMeter.stop();
    }

    public void reset() {
        timeAtLastLoudMs = 0;
    }
}
