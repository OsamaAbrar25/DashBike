package com.example.osama.dashbike.Repository;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.osama.dashbike.Utility.ApplicationConstants;
import com.example.osama.dashbike.Utility.VolleySingleton;

import org.json.JSONObject;

public class LogoutRepository {
    private Context context;
    private LogoutListener logoutListener;

    public LogoutRepository(Context context, LogoutListener logoutListener) {
        this.context = context;
        this.logoutListener = logoutListener;
    }

    public void Logout(){
        String logout_url = ApplicationConstants.POST_LOGOUT;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, logout_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("detail")){
                    if (logoutListener != null){
                        logoutListener.onLogout(response);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
