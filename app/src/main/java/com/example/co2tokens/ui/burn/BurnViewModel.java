package com.example.co2tokens.ui.burn;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.co2tokens.ApiLink;

public class BurnViewModel extends AndroidViewModel implements ApiLink.OverviewCallback {

    private ApiLink link;

    private MutableLiveData<String> mAvailable;


    public BurnViewModel( Application application ) {
        super( application );

        link = ApiLink.getInstance( getApplication().getApplicationContext() );


        link.getBalance( this, false );

        mAvailable = new MutableLiveData<>();
    }

    public LiveData<String> getAvailable() {
        return mAvailable;
    }

    @Override
    public void pushBalance( double balance ) {
        mAvailable.setValue( "" + balance );
    }

    @Override
    public void pushTxs( String txs ) {

    }
}