package com.example.osama.dashbike.Repository;

import android.content.Context;

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

public class RegistrationRepository {
    Context context;
    private RegisterListener registerListener;
    private String username, email, password1, password2, contactno;

    public RegistrationRepository(Context context, RegisterListener registerListener) {
        this.context = context;
        this.registerListener = registerListener;
    }
    public void Register () throws JSONException {
        String registration_url = ApplicationConstants.POST_REGISTRATION;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("email", email);
        jsonObject.put("number",contactno);
        jsonObject.put("password1", password1);
        jsonObject.put("password2", password2);
        jsonObject.put("user_type", "Client");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, registration_url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (registerListener != null) {
                   registerListener.onDataReceived(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                registerListener.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
    public void getRegistration(String username, String email, String contactno, String password1, String password2) {
        this.username = username;
        this.email = email;
        this.contactno = contactno;
        this.password1 = password1;
        this.password2 = password2;
    }
}