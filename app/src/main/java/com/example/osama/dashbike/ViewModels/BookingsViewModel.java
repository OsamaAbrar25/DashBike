package com.example.osama.dashbike.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.osama.dashbike.Models.BookingsInfo;
import com.example.osama.dashbike.Repository.BookingsRepository;

import java.util.ArrayList;

public class BookingsViewModel extends AndroidViewModel {
    private BookingsRepository bookingsRepository;
    private LiveData<ArrayList<BookingsInfo>> bookingsInfoListObservable;

    public BookingsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<BookingsInfo>> getBookingsInfoListObservable() {
        bookingsRepository = new BookingsRepository(getApplication());
        bookingsInfoListObservable = bookingsRepository.bookBike();
        if (bookingsInfoListObservable == null){
            bookingsInfoListObservable = new MutableLiveData<>();
        }
        return bookingsInfoListObservable;
    }
}