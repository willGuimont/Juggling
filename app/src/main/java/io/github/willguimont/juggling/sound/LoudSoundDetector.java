package io.github.willguimont.juggling.sound;

import android.os.Handler;

public class LoudSoundDetector {
    private static final int POLL_INTERVAL = 100;
    private static final double THRESHOLD = 6;

    private Handler handler = new Handler();
    private SoundMeter soundMeter;
    private Runnable onLoudSoundAction;
    private Runnable pollTask = new Runnable() {
        @Override
        public void run() {
            double amp = soundMeter.getAmplitude();

            if (amp > THRESHOLD) {
                onLoudSoundAction.run();
            }

            handler.postDelayed(pollTask, POLL_INTERVAL);
        }
    };

    public LoudSoundDetector(Runnable onLoudSoundAction) {
        soundMeter = new SoundMeter();
        this.onLoudSoundAction = onLoudSoundAction;
    }

    public void start() {
        soundMeter.start();
        handler.postDelayed(pollTask, POLL_INTERVAL);
    }

    public void stop() {
        handler.removeCallbacks(pollTask);
        soundMeter.stop();
    }
}
