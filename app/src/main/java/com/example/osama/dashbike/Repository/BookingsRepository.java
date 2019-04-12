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
import com.example.osama.dashbike.Models.BookingsInfo;
import com.example.osama.dashbike.Utility.ApplicationConstants;
import com.example.osama.dashbike.Utility.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingsRepository {
    private Context context;
    private MutableLiveData<ArrayList<BookingsInfo>> mutableLiveData = new MutableLiveData<>();
    private ArrayList<BookingsInfo> arrayList;
    private String pickup_time, booking_time, duration, transaction_amt, ord_id, transaction_id, status,
            bike_model, client, dealer;

    public BookingsRepository(Context context) {
        this.context = context;
    }

    public LiveData<ArrayList<BookingsInfo>> bookBike(){
        String url = ApplicationConstants.GET_BOOKINGS;
        arrayList = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int i;
                for (i=0 ; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        pickup_time = jsonObject.getString("pickup_time");
                        booking_time = jsonObject.getString("booking_time");
                        duration = jsonObject.getString("duration");
                        transaction_amt = jsonObject.getString("transaction_amt");
                        ord_id = jsonObject.getString("ord_id");
                        transaction_id = jsonObject.getString("transaction_id");
                        status = jsonObject.getString("status");
                        bike_model = jsonObject.getString("bike_model_name");
                        client = jsonObject.getString("client");
                        dealer = jsonObject.getString("dealer_name");
                        BookingsInfo bookingsInfo = new BookingsInfo(pickup_time, booking_time, duration, transaction_amt, ord_id, transaction_id,
                                status, bike_model, client, dealer);
                        arrayList.add(bookingsInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                mutableLiveData.postValue(arrayList);

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
