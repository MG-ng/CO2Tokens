package org.co2tokens.app.ui.transfer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransferViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TransferViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue( "This is dashboard fragment" );
    }

    public LiveData<String> getText() {
        return mText;
    }
}