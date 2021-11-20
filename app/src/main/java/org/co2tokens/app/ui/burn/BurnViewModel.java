package org.co2tokens.app.ui.burn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BurnViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BurnViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue( "This is notifications fragment" );
    }

    public LiveData<String> getText() {
        return mText;
    }
}