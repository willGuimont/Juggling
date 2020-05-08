package io.github.willguimont.juggling.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimeNoDropModel extends ViewModel {

    public static final int TICK_DURATION_MS = 10;

    private MutableLiveData<Long> numDecisecondsNoDrop = new MutableLiveData<>();
    private MutableLiveData<Boolean> isRunning = new MutableLiveData<>();

    void reset() {
        numDecisecondsNoDrop.setValue((long) 0);
        isRunning.setValue(false);
    }

    void tick() {
        Long lastValue = numDecisecondsNoDrop.getValue();
        numDecisecondsNoDrop.setValue(lastValue + 1);
    }

    LiveData<Long> getDeciseconds() {
        return numDecisecondsNoDrop;
    }
}