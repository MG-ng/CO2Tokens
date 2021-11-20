package org.co2tokens.app.ui.overview;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.co2tokens.app.ApiLink;

public class OverviewViewModel extends AndroidViewModel implements ApiLink.OverviewCallback {

    private ApiLink link;

    private MutableLiveData<String> mBalance;
    private MutableLiveData<String> mTxs;

    public OverviewViewModel( Application application ) {
        super( application );

        mBalance = new MutableLiveData<>();
        mBalance.setValue( "loading" );

        mTxs = new MutableLiveData<>();
        mTxs.setValue( "{ Some API Content }" );

        link = ApiLink.getInstance( getApplication().getApplicationContext() );

        link.getBalance( this );
    }

    public LiveData<String> getBalance() {
        return mBalance;
    }

    public LiveData<String> getTxs() {
        return mTxs;
    }


    @Override
    public void pushBalance( double balance ) {
        mBalance.postValue( balance + "" );
    }

    @Override
    public void pushTxs( String txs ) {
        mTxs.postValue( txs );
    }
}