package io.github.willguimont.juggling.ui.time;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class TimeNoDropModel extends ViewModel {

    private static final long TICK_DELAY_MS = 10;

    private final MutableLiveData<Long> currentTime = new MutableLiveData<>();
    private long startTime;
    private final Handler handler = new Handler();
    private boolean isRunning;

    private final Runnable tick = new Runnable() {
        @Override
        public void run() {
            currentTime.setValue(System.currentTimeMillis());
            synchronized (currentTime) {
                currentTime.notifyAll();
            }
            handler.postDelayed(tick, TICK_DELAY_MS);
        }
    };

    void reset() {
        startTime = System.currentTimeMillis();
        handler.removeCallbacks(tick);
        currentTime.setValue(startTime);
        isRunning = false;
    }

    void stop() {
        handler.removeCallbacks(tick);
        isRunning = false;
        Log.i("TimeNoDropModel", "stop");
    }

    void start() {
        startTime = System.currentTimeMillis();
        handler.postDelayed(tick, 0);
        isRunning = true;
    }

    boolean getIsRunning() {
        return isRunning;
    }

    LiveData<Long> getNoDropMs() {
        return Transformations.map(currentTime, x -> x - startTime);
    }
}