package com.example.osama.dashbike.Repository;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.osama.dashbike.Utility.ApplicationConstants;
import com.example.osama.dashbike.Utility.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginRepository {
    private static String TAG = "REPOSITORY";
    private Context context;
    private LoginKeyListener loginKeyListener;
    private String k;
    private String useremail, password;

    public LoginRepository(Context context, LoginKeyListener loginKeyListener) {
        this.context = context;
        this.loginKeyListener = loginKeyListener;
    }

    public void Login() {
        String url = ApplicationConstants.POST_LOGIN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (loginKeyListener != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("key")) {
                            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("KEY", jsonObject.getString("key")).apply();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    loginKeyListener.onLoginKeyReceived(response);
                }
                Log.e("TAG", "onResponse: "+response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", useremail);
                params.put("password", password);
                params.put("user_type", "Client");
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                //Log.e(TAG, "parseNetworkResponse: "+response.headers );
                //String token = response.headers.get("Set-Cookie").toString().substring(10, 32);
                //Log.e(TAG, "Header- Session ID "+response.headers.get("Set-Cookie").toString().substring(10, 42));
                //PreferenceManager.getDefaultSharedPreferences(context).edit().putString("TOKEN", token).apply();

             return super.parseNetworkResponse(response);
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
    public void getLogin (String useremail, String password) {
        this.useremail = useremail;
        this.password = password;

    }

}