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
import com.example.osama.dashbike.Models.DealersInfo;
import com.example.osama.dashbike.Utility.ApplicationConstants;
import com.example.osama.dashbike.Utility.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DealersRepository {
    private Context context;
    private MutableLiveData<ArrayList<DealersInfo>> mutableLiveData = new MutableLiveData<>();
    private ArrayList<DealersInfo> arrayList;

    public DealersRepository(Context context) {
        this.context = context;
    }

    public LiveData<ArrayList<DealersInfo>> getArrayList(){
        String json_url = ApplicationConstants.GET_DEALERS;

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
                                String c = jsonObject.getString("thumbnail");
                                String a = jsonObject.getString("id");
                                String b = jsonObject.getString("extra_info");
                                DealersInfo dealersInfo = new DealersInfo(a, b, c);
                                arrayList.add(dealersInfo);
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

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return mutableLiveData;
    }
}
