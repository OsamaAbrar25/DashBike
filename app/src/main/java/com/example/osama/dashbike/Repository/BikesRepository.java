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
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.osama.dashbike.Models.BikesInfo;
import com.example.osama.dashbike.Utility.ApplicationConstants;
import com.example.osama.dashbike.Utility.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BikesRepository {
    private Context context;
    private MutableLiveData<ArrayList<BikesInfo>> mutableLiveData = new MutableLiveData<>();
    private ArrayList<BikesInfo> arrayList;
    private String Id;


    public BikesRepository(Context context){
        this.context = context;
    }

    public LiveData<ArrayList<BikesInfo>> getArrayList(){
        Id = PreferenceManager.getDefaultSharedPreferences(context).getString("DEALERID", null);
        String json_url = ApplicationConstants.GET_BIKES +Id;

        //mutableLiveData = new MutableLiveData<>();
        arrayList = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            int i;
                            for (i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                String a = jsonObject.getString("id");
                                String b = jsonObject.getString("bike_model");
                                String c = jsonObject.getString("bike_rate_hr");
                                String d = jsonObject.getString("bike_img");
                                String e = jsonObject.getString("bike_model_id");
                                BikesInfo bikesinfo = new BikesInfo(a, b, c, d, e);
                                arrayList.add(bikesinfo);
                            }
                            mutableLiveData.postValue(arrayList);
                        } catch (JSONException e){
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

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return mutableLiveData;
    }

}
