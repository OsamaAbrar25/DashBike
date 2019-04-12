package com.example.osama.dashbike.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.osama.dashbike.Models.BikeDetailsInfo;
import com.example.osama.dashbike.Repository.BikeDetailsRepository;

import java.util.ArrayList;

public class BikeDetailsViewModel extends AndroidViewModel {
    BikeDetailsRepository bikeDetailsRepository;
    LiveData<ArrayList<BikeDetailsInfo>> bikedetailsinfolistobservable;

    public BikeDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<BikeDetailsInfo>> getBikeDetailsInfolistObservable(){
        if (bikedetailsinfolistobservable == null){
            bikedetailsinfolistobservable = new MutableLiveData<>();
        }
        bikeDetailsRepository = new BikeDetailsRepository(getApplication());
        bikedetailsinfolistobservable = bikeDetailsRepository.getbikedetail();
        return bikedetailsinfolistobservable;
    }
}
