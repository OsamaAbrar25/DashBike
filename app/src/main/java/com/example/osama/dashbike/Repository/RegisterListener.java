package com.example.osama.dashbike.Repository;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface RegisterListener {
    public void onDataReceived(JSONObject response);
    public void onError(VolleyError error);
}
