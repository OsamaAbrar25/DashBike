package com.example.osama.dashbike.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.osama.dashbike.Models.BikeDetailsInfo;
import com.example.osama.dashbike.Utility.ApplicationConstants;
import com.example.osama.dashbike.Utility.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BikeDetailsRepository {
    private Context context;
    private String bikeId, dealerId;
    private MutableLiveData<ArrayList<BikeDetailsInfo>> mutableLiveData = new MutableLiveData<>();
    private ArrayList<BikeDetailsInfo> arrayList;

    public BikeDetailsRepository(Context context) {
        this.context = context;
    }

    public LiveData<ArrayList<BikeDetailsInfo>> getbikedetail(){
        bikeId = PreferenceManager.getDefaultSharedPreferences(context).getString("BIKEID", null);
        dealerId = PreferenceManager.getDefaultSharedPreferences(context).getString("DEALERID", null);
        //SERVER URL
        String url = ApplicationConstants.GET_BIKE_DETAIL +dealerId+"/"+bikeId;
        arrayList = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String id, bike_model, dealer, description, count, bike_rate_hr, bike_rate_h, bike_rate_f,
                                    bike_img, bike_isAvailable, thumbnail;
                            id = response.getString("id");
                            bike_model = response.getString("bike_model");
                            dealer = response.getString("dealer");
                            description = response.getString("description");
                            count = response.getString("count");
                            bike_rate_hr = response.getString("bike_rate_hr");
                            bike_rate_h = response.getString("bike_rate_h");
                            bike_rate_f = response.getString("bike_rate_f");
                            bike_img = response.getString("bike_img");
                            bike_isAvailable = response.getString("bike_isAvailable");
                            thumbnail = response.getString("thumbnail");
                            BikeDetailsInfo bikeDetailsInfo = new BikeDetailsInfo(id, bike_model, dealer, description,
                                    count, bike_rate_hr, bike_rate_h, bike_rate_f, bike_img, bike_isAvailable, thumbnail);
                            arrayList.add(bikeDetailsInfo);
                            mutableLiveData.postValue(arrayList);
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
        return mutableLiveData;
    }
}
