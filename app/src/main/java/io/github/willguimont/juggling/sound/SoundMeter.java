package io.github.willguimont.juggling.sound;

import android.media.MediaRecorder;

import java.io.IOException;

// https://github.com/Santosh-Gupta/SitcomSimulator/blob/335664a71187ea8f2e29ac882afc8d3c2732bd16/noisealert/src/com/androidexample/noisealert/SoundMeter.java#L7
// (modified)
public class SoundMeter {
    static final private double EMA_FILTER = 0.6;

    private MediaRecorder recorder = null;
    private double mEMA = 0.0;

    public void start() {
        if (recorder == null) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile("/dev/null");

            try {
                recorder.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            recorder.start();
            mEMA = 0.0;
        }
    }

    public void stop() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    public double getAmplitude() {
        if (recorder != null)
            return (recorder.getMaxAmplitude() / 2700.0);
        else
            return 0;

    }

    public double getAmplitudeEMA() {
        double amp = getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }
}
