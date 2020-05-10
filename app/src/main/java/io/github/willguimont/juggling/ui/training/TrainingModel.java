package io.github.willguimont.juggling.ui.training;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrainingModel extends ViewModel {

    private final MutableLiveData<Integer> numberOfDrops = new MutableLiveData<>();

    void reset() {
        numberOfDrops.setValue(0);
    }

    void drop() {
        Integer lastValue = numberOfDrops.getValue();
        numberOfDrops.setValue(lastValue + 1);
    }

    LiveData<Integer> getNumberOfDrops() {
        return numberOfDrops;
    }
}