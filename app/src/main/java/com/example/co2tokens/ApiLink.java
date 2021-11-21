package com.example.co2tokens;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiLink {

    private static final String TAG = ApiLink.class.getCanonicalName();

    private static final String apiUrl = "https://api.counterparty.io:14001";

    public static final String address = "mxJQUbCb2bsSKwwsnUsBW7PouTfnMsLxyt";
    public static final String pubKey = "03b4dc8df83130205c93e789a543c4b298b0e64b5d4cb8365fca72f2aba880bcf4";
    public static final String privateKey = "cSiDFNJ6EHkJXfBMDc8AWHcFoWghe4BToVHyvH16C6uzYyzeyf4P";



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
        // TODO
    }


    public void getBalance( OverviewCallback back, boolean unconfirmed ) {

//        $result = $client->execute('get_balances', array('filters' => array('field' => 'address', 'op' => '==', 'value' => '1NFeBp9s5aQ1iZ26uWyiK2AYUXHxs7bFmB')));
//        print("get_balances result:\n");
//        var_dump($result);

        RequestQueue requestQueue = Volley.newRequestQueue( context );

        JSONObject postData = new JSONObject();
        try {
            postData.put( "method", "get_unspent_txouts" );
            JSONObject array = new JSONObject( "{ 'address': '" + address + "', 'unconfirmed': '" + unconfirmed + "'}] }" );

            Log.d( TAG, array.toString() );
            postData.put( "params", array );

            postData.put( "jsonrpc", "2.0" );
            postData.put( "id", 0 );

            Log.i( TAG, "Given data: " + postData.toString() );

        } catch ( JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, apiUrl, postData,
                response -> {
                    Log.i( TAG, response.toString() );
                    try {
                        JSONArray txs = response.getJSONArray( "result" );

                        double sum = 0;
                        for( int i = 0; i < txs.length(); i++ ) {
                            JSONObject obj = txs.getJSONObject( i );

                            double amt = obj.getDouble( "amount" );
                            sum += amt;
                        }

                        back.pushBalance( sum );

                    } catch( JSONException e ) {
                        e.printStackTrace();
                    } catch( NullPointerException e ) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace
        ) {
            // Auth Header!
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put( "Content-Type", "application/json" );
                String auth = "Basic " + Base64.encodeToString( "rpc:rpc".getBytes(), Base64.NO_WRAP );
                headers.put( "Authorization", auth );
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }


// curl -X POST https://api.counterparty.io:14001 --user rpc:rpc -H 'Content-Type: application/json; charset=UTF-8' -H 'Accept: application/json, text/javascript' --data-binary '{"method":"get_balances","params":{"filters":[{"field":"address","op":"==","value":"mxJQUbCb2bsSKwwsnUsBW7PouTfnMsLxyt"}]},"jsonrpc":"2.0","id":0}'

    public void getRunningJsonRPC( OverviewCallback back ){

        RequestQueue requestQueue = Volley.newRequestQueue( context );

        JSONObject postData = new JSONObject();
        try {
            postData.put( "jsonrpc", "2.0" );
            postData.put( "id", "0" );
            postData.put( "method", "get_running_info" );
        } catch ( JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, apiUrl, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse( JSONObject response ) {
                        Log.i( TAG, response.toString() );
                        try {
                            back.pushBalance( response.getDouble( "jsonrpc" ) );
                        } catch( JSONException e ) {
                            e.printStackTrace();
                        } catch( NullPointerException e ) {
                            e.printStackTrace();
                        }
                    }
                },
                Throwable::printStackTrace
        ) {
            // Auth Header!
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                String auth = "Basic " + Base64.encodeToString( "rpc:rpc".getBytes(), Base64.NO_WRAP );
                headers.put("Authorization", auth );
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }




    public interface OverviewCallback {
        public void pushBalance( double balance );
        public void pushTxs( String txs );
    }



}
