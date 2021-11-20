package com.example.co2tokens;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiLink {

    private static final String TAG = ApiLink.class.getCanonicalName();
    private Context context;
    SharedPreferences sharedPref;


    public static ApiLink getInstance( Context context ) {
        if( context != null ) {
            return new ApiLink( context );
        }
        return null;
    }


    private ApiLink( Context context ) {
        this.context = context;

        sharedPref = PreferenceManager.getDefaultSharedPreferences( context );

        if( sharedPref.getString( "mnemonic", "" ).isEmpty() ) {
            initWallet();
        }
    }


    private void initWallet() {

    }




    public void getBalance( OverviewCallback back ){
        String postUrl = "https://reqres.in/api/users";
        RequestQueue requestQueue = Volley.newRequestQueue( context );

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", "Jonathan");
            postData.put("job", "Software Engineer");

        } catch ( JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, postUrl, postData,
                response -> {
                    Log.i( TAG, response.toString() );
                    try {
                        back.pushBalance( response.getDouble( "Key" ) );
                    } catch( JSONException e ) {
                        e.printStackTrace();
                    } catch( NullPointerException e ) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );

        requestQueue.add(jsonObjectRequest);
    }




    public interface OverviewCallback {
        public void pushBalance( double balance );
        public void pushTxs( String txs );
    }



}
