package com.example.co2tokens.ui.burn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BurnViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BurnViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue( "0.00" );
    }

    public LiveData<String> getText() {
        return mText;
    }
}