package org.co2tokens.app;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiLink {

    private static final String TAG = ApiLink.class.getCanonicalName();
    private final Context context;

    private ApiLink( Context context ) {
        this.context = context;
    }

    public static ApiLink getInstance( Context context ) {
        if( context != null ) {
            return new ApiLink( context );
        }
        return null;
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
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse( JSONObject response ) {
                        Log.i( TAG, response.toString() );
                        try {
                            back.pushBalance( response.getDouble( "Key" ) );
                        } catch( JSONException e ) {
                            e.printStackTrace();
                        } catch( NullPointerException e ) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse( VolleyError error ) {
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }


    public interface OverviewCallback {
        public void pushBalance( double balance );
        public void pushTxs( String txs );
    }



}
