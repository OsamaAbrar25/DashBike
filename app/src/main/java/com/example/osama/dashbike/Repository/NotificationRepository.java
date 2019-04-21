package com.example.osama.dashbike.Repository;

import android.content.Context;
import android.preference.PreferenceManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.osama.dashbike.Utility.ApplicationConstants;
import com.example.osama.dashbike.Utility.VolleySingleton;


public class NotificationRepository {
    private Context context;

    public NotificationRepository(Context context) {
        this.context = context;
    }

    public void sendToken(String token) {
        String url, user_type, id;
        user_type = "client";
        id = PreferenceManager.getDefaultSharedPreferences(context).getString("CLIENTID", null);
        url = ApplicationConstants.BASE_URL + "/api/v1/insert/" +id+ "/?fcm_token=" +token+ "%user_type=" +user_type;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void sendNotification() {
        String title, body, user_type, id, url;
        title = "Booking Confirmed!";
        body = "Hurray! You have successfully booked your bike.";
        user_type = "client";
        id = PreferenceManager.getDefaultSharedPreferences(context).getString("CLIENTID", null);
        url = ApplicationConstants.BASE_URL + "/api/v1/send/" +id+ "/?user_type=" +user_type+ "%title=" +title+ "%body=" +body;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
