package com.example.co2tokens.ui.burn;

import static com.example.co2tokens.ApiLink.BurnCallback;
import static com.example.co2tokens.ApiLink.CO2BalanceCallback;
import static com.example.co2tokens.ApiLink.getInstance;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.co2tokens.ApiLink;

public class BurnViewModel extends AndroidViewModel implements CO2BalanceCallback, BurnCallback {

    private ApiLink link;

    private MutableLiveData<String> mAvailable, mBurnStatus;


    public BurnViewModel( Application application ) {
        super( application );

        link = getInstance( getApplication().getApplicationContext() );

        mAvailable = new MutableLiveData<>();

        mBurnStatus = new MutableLiveData<>();
        link.getCO2Balance( this );
    }

    public LiveData<String> getAvailable() {
        return mAvailable;
    }

    public LiveData<String> getBurnStatus() {
        return mBurnStatus;
    }



    public void burnAmt( double amount ) {
        link.burnAmount( this, amount );
    }


    @Override
    public void pushCO2Balance( double balance ) {
        mAvailable.postValue( balance + " CO2T" );
    }

    @Override
    public void pushError( Error e ) {

    }

    @Override
    public void pushBurnStatus( String status ) {
        mBurnStatus.setValue( status );
    }

}