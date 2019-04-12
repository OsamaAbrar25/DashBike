package com.example.osama.dashbike.Views.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.osama.dashbike.Models.BookingsInfo;
import com.example.osama.dashbike.R;
import com.example.osama.dashbike.ViewModels.BookingsViewModel;
import com.example.osama.dashbike.Views.Adapters.BookingsListAdapter;

import java.util.ArrayList;

public class BookingsFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    public BookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookings_recycler, container, false);
        recyclerView = view.findViewById(R.id.recycler3);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BookingsViewModel bookingsViewModel = ViewModelProviders.of(this).get(BookingsViewModel.class);
        observeViewModel(bookingsViewModel);

    }

    private void observeViewModel(BookingsViewModel bookingsViewModel) {
        bookingsViewModel.getBookingsInfoListObservable().observe(this, new Observer<ArrayList<BookingsInfo>>() {
            @Override
            public void onChanged(@Nullable ArrayList<BookingsInfo> bookingsInfos) {
                if (bookingsInfos != null){
                    BookingsListAdapter bookingsListAdapter = new BookingsListAdapter(bookingsInfos, getFragmentManager());
                    recyclerView.setAdapter(bookingsListAdapter);
                }
            }
        });
    }
}
