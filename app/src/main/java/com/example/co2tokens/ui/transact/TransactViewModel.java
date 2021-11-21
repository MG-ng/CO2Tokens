package com.example.co2tokens.ui.transact;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.co2tokens.ApiLink;
import com.example.co2tokens.ApiLink.BalanceCallback;
import com.example.co2tokens.ApiLink.TxsCallback;

public class TransactViewModel extends AndroidViewModel implements BalanceCallback, TxsCallback {

    private ApiLink link;

    private MutableLiveData<String> mAddress;


    public TransactViewModel( Application application ) {
        super( application );

        mAddress = new MutableLiveData<>();


        link = ApiLink.getInstance( getApplication().getApplicationContext() );

        link.getBalance( this, false );
        mAddress = new MutableLiveData<>();
    }


    public LiveData<String> getAddress() {
        mAddress.postValue( ApiLink.getAddress() );
        return mAddress;
    }


    @Override
    public void pushBalance( double balance ) {

    }

    @Override
    public void pushTxs( String txs ) {

    }

    @Override
    public void pushError( Error e ) {

    }
}