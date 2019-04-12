package com.example.osama.dashbike.Views.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.osama.dashbike.Models.BikesInfo;
import com.example.osama.dashbike.R;
import com.example.osama.dashbike.ViewModels.BikesViewModel;
import com.example.osama.dashbike.Views.Adapters.BikesListAdapter;

import java.util.ArrayList;

public class BikesFragment extends Fragment {

    RecyclerView recyclerView;
    BikesViewModel bikesViewModel;
    BikesListAdapter bikesListAdapter;
    LinearLayoutManager linearLayoutManager;

    public BikesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bikes_recycler, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bikesViewModel = ViewModelProviders.of(this).get(BikesViewModel.class);
        observeViewModel(bikesViewModel);
        
    }

    private void observeViewModel (BikesViewModel bikesViewModel){
        bikesViewModel.getbikesinfolistobservable().observe(this, new Observer<ArrayList<BikesInfo>>() {
            @Override
            public void onChanged(@Nullable ArrayList<BikesInfo> bikesInfo) {
                if (bikesInfo != null) {
                    bikesListAdapter = new BikesListAdapter(bikesInfo, getFragmentManager(), getActivity());
                    recyclerView.setAdapter(bikesListAdapter);
                }
            }
        });
    }
}
