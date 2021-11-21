package com.example.co2tokens;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

    private static final String asset = "A7953223131002272000";
    private static final String address = "mxJQUbCb2bsSKwwsnUsBW7PouTfnMsLxyt";
    private static final String pubKey = "03b4dc8df83130205c93e789a543c4b298b0e64b5d4cb8365fca72f2aba880bcf4";
    private static final String privateKey = "cSiDFNJ6EHkJXfBMDc8AWHcFoWghe4BToVHyvH16C6uzYyzeyf4P";



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

    public static String getAddress() {
        return address;
    }


    private Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put( "Content-Type", "application/json" );
        String auth = "Basic " + Base64.encodeToString( "rpc:rpc".getBytes(), Base64.NO_WRAP );
        headers.put( "Authorization", auth );
        return headers;
    }


    public void getBalance( BalanceCallback back, boolean unconfirmed ) {
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
                return ApiLink.this.getHeaders();
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void getCO2Balance( CO2BalanceCallback back ) {
        RequestQueue requestQueue = Volley.newRequestQueue( context );

        JSONObject postData = new JSONObject();
        try {
            postData.put( "method", "get_holders" );
            JSONObject array = new JSONObject( "{ " +
                    "'asset': '" + asset + "'" +
                    "}] }" );

            Log.d( TAG, array.toString() );
            postData.put( "params", array );

            postData.put( "jsonrpc", "2.0" );
            postData.put( "id", 0 );

            Log.i( TAG, "Given data holders: " + postData.toString() );

        } catch ( JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, apiUrl, postData,
                response -> {
                    Log.i( TAG, response.toString() );
                    try {
                        JSONArray holders = response.getJSONArray( "result" );

                        Log.e( TAG, holders.toString() );

                        double amount = 0;
                        for( int i = 0; i < holders.length(); i++ ) {
                            JSONObject obj = holders.getJSONObject( i );

                            String objAddress = obj.getString( "address" );
                            if( objAddress.equals( address ) ) {
                                amount = obj.getLong( "address_quantity" );
                                break;
                            }
                        }

                        back.pushCO2Balance( amount / Math.pow( 10, 8 ) );

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
                return ApiLink.this.getHeaders();
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void burnAmount( BurnCallback back, double amount ) {
        RequestQueue requestQueue = Volley.newRequestQueue( context );

        JSONObject postData = new JSONObject();
        try {
            postData.put( "method", "get_holders" );
            JSONObject array = new JSONObject( "{ " +
                    "'asset': '" + asset + "'" +
                    "}] }" );

            Log.d( TAG, array.toString() );
            postData.put( "params", array );

            postData.put( "jsonrpc", "2.0" );
            postData.put( "id", 0 );

            Log.i( TAG, "Given data holders: " + postData.toString() );

        } catch ( JSONException e) {
            e.printStackTrace();
        }

        back.pushBurnStatus( "Sent " + amount + "\n\nto 1CarbonDioxideBurnXXXXXXXXXXXSuHvy\n" );

/* Not working yet
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, apiUrl, postData,
                response -> {
                    Log.i( TAG, response.toString() );
                    try {
                        JSONArray holders = response.getJSONArray( "result" );

                        Log.e( TAG, holders.toString() );

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
                return ApiLink.this.getHeaders();
            }
        };
        requestQueue.add(jsonObjectRequest);
        */
    }


// curl -X POST https://api.counterparty.io:14001 --user rpc:rpc -H 'Content-Type: application/json; charset=UTF-8' -H 'Accept: application/json, text/javascript' --data-binary '{"method":"get_balances","params":{"filters":[{"field":"address","op":"==","value":"mxJQUbCb2bsSKwwsnUsBW7PouTfnMsLxyt"}]},"jsonrpc":"2.0","id":0}'

    public void getRunningJsonRPC( BalanceCallback back ){

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
                response -> {
                    Log.i( TAG, response.toString() );
                    try {
                        back.pushBalance( response.getDouble( "jsonrpc" ) );
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
                return ApiLink.this.getHeaders();
            }
        };

        requestQueue.add(jsonObjectRequest);
    }



    public interface ErrorCallback {
        void pushError( Error e );
    }

    public interface BalanceCallback extends ErrorCallback {
        void pushBalance( double balance );
    }

    public interface CO2BalanceCallback extends ErrorCallback {
        void pushCO2Balance( double balance );
    }

    public interface TxsCallback extends ErrorCallback {
        void pushTxs( String txs );
    }

    public interface BurnCallback extends ErrorCallback {
        void pushBurnStatus( String status );
    }


}
