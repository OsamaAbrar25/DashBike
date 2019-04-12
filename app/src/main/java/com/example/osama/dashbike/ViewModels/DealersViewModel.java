package com.example.osama.dashbike.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.osama.dashbike.Models.DealersInfo;
import com.example.osama.dashbike.Repository.DealersRepository;

import java.util.ArrayList;

public class DealersViewModel extends AndroidViewModel {

    DealersRepository dealersRepository;
    LiveData<ArrayList<DealersInfo>> dealersinfolistobservable;

    public DealersViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<DealersInfo>> getdealersinfolistobservable(){
        dealersRepository = new DealersRepository(getApplication());

        dealersinfolistobservable = dealersRepository.getArrayList();
        if (dealersinfolistobservable==null){
            dealersinfolistobservable = new MutableLiveData<>();
        }
        return dealersinfolistobservable;

    }
}
