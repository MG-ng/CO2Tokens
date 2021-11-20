package com.example.co2tokens.ui.transact;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransactViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TransactViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue( "bc1q... generating" );
    }

    public LiveData<String> getText() {
        return mText;
    }
}