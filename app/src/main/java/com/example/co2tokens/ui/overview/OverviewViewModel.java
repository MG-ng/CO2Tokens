package com.example.co2tokens.ui.overview;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.co2tokens.ApiLink;

public class OverviewViewModel extends AndroidViewModel implements ApiLink.BalanceCallback, ApiLink.CO2BalanceCallback {

    private ApiLink link;

    private MutableLiveData<String> mBalance, co2Balance;
    private MutableLiveData<String> mTxs;



    public OverviewViewModel( Application application ) {
        super( application );

        mBalance = new MutableLiveData<>();
        mBalance.setValue( "loading..." );

        co2Balance = new MutableLiveData<>();
        co2Balance.setValue( "loading..." );

        mTxs = new MutableLiveData<>();
        mTxs.setValue( "{ Some API Content }" );

        link = ApiLink.getInstance( getApplication().getApplicationContext() );

        link.getBalance( this, true );
        link.getCO2Balance( this );
    }

    public LiveData<String> getBalance() {
        return mBalance;
    }

    public LiveData<String> getCO2Balance() {
        return co2Balance;
    }

    public LiveData<String> getTxs() {
        return mTxs;
    }


    @Override
    public void pushBalance( double balance ) {
        mBalance.postValue( balance + " BTC" );
    }

    @Override
    public void pushCO2Balance( double balance ) {
        co2Balance.postValue( balance + " CO2T" );
    }

    @Override
    public void pushError( Error e ) {

    }
}