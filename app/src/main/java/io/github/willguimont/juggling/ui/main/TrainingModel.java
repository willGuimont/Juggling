package io.github.willguimont.juggling.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrainingModel extends ViewModel {

    private MutableLiveData<Integer> numberOfDrops = new MutableLiveData<>();

    public void reset() {
        numberOfDrops.setValue(0);
    }

    public void drop() {
        numberOfDrops.setValue(numberOfDrops.getValue() + 1);
    }

    public LiveData<Integer> getNumberOfDrops() {
        return numberOfDrops;
    }
}