package com.example.osama.dashbike.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.osama.dashbike.Models.BikesInfo;
import com.example.osama.dashbike.Repository.BikesRepository;

import java.util.ArrayList;

public class BikesViewModel extends AndroidViewModel {
    private BikesRepository bikesRepository;
    private LiveData<ArrayList<BikesInfo>> bikesinfolistobservable;

    public BikesViewModel(@NonNull Application application) {
        super(application);}



    public LiveData<ArrayList<BikesInfo>> getbikesinfolistobservable(){
        bikesRepository = new BikesRepository(getApplication());

        bikesinfolistobservable = bikesRepository.getArrayList();
        if (bikesinfolistobservable==null){
            bikesinfolistobservable = new MutableLiveData<>();
        }
        return bikesinfolistobservable;

    }

}