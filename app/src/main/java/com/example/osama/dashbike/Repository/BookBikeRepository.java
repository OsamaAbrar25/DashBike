package com.example.osama.dashbike.Repository;

import android.content.Context;
import android.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.osama.dashbike.Utility.ApplicationConstants;
import com.example.osama.dashbike.Utility.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookBikeRepository {
    private Context context;
    private String pickup_time, booking_time, duration, transaction_amt, ord_id, bike_model, client, dealer, duration_unit;

    public BookBikeRepository(Context context) {
        this.context = context;
    }

    public void setBookingDetails(String bike_model, String pickup_time, String booking_time, String duration, String transaction_amt, String ord_id, String client, String duration_unit){
        this.pickup_time = pickup_time;
        this.booking_time = booking_time;
        this.duration = duration;
        this.transaction_amt = transaction_amt;
        this.ord_id = ord_id;
        this.bike_model = bike_model;
        this.dealer = PreferenceManager.getDefaultSharedPreferences(context).getString("DEALERID", null);
        this.client = client;
        this.duration_unit = duration_unit;
    }

    public void bookBike (){
        String url = ApplicationConstants.POST_BOOK_BIKE;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("dealer", "3"/*dealer*/);
            jsonObject.put("bike_model","1"/*bike_model*/);
            jsonObject.put("pickup_time",pickup_time);
            jsonObject.put("booking_time",booking_time);
            jsonObject.put("duration", duration);
            jsonObject.put("client",client);
            jsonObject.put("transaction_amt", transaction_amt);
            jsonObject.put("ord_id", ord_id);
            jsonObject.put("transaction_id", "ggddg34");
            jsonObject.put("status", "pending");
            jsonObject.put("duration_unit", duration_unit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String key = PreferenceManager.getDefaultSharedPreferences(context).getString("KEY", null);
                headers.put("Authorization","TOKEN "+key);
                //return super.getHeaders();
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public void getIdAndSetTransactionId(final String trxn_id) {
        String url = ApplicationConstants.POST_BOOK_BIKE;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String id = response.getString("id");
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putString("BOOKING_PRIMARY_KEY", id).apply();
                    setTransactionId(trxn_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String key = PreferenceManager.getDefaultSharedPreferences(context).getString("KEY", null);
                headers.put("Authorization","TOKEN "+key);
                //return super.getHeaders();
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public void setTransactionId(String trxn_id) {
        String id = PreferenceManager.getDefaultSharedPreferences(context).getString("BOOKING_PRIMARY_KEY", null);
        String url = ApplicationConstants.POST_BOOK_BIKE+"/"+id;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("transaction_id", trxn_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String key = PreferenceManager.getDefaultSharedPreferences(context).getString("KEY", null);
                headers.put("Authorization","TOKEN "+key);
                //return super.getHeaders();
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

}
