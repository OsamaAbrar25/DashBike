package com.example.osama.dashbike.Views.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.osama.dashbike.Models.DealersInfo;
import com.example.osama.dashbike.R;
import com.example.osama.dashbike.ViewModels.BikesViewModel;
import com.example.osama.dashbike.ViewModels.DealersViewModel;
import com.example.osama.dashbike.Views.Adapters.BikesListAdapter;
import com.example.osama.dashbike.Views.Adapters.DealersListAdapter;

import java.util.ArrayList;

public class DealersFragment extends Fragment {

    RecyclerView recyclerView;
    DealersViewModel dealersViewModel;
    DealersListAdapter dealersListAdapter;
    LinearLayoutManager linearLayoutManager;


    public DealersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dealers_recycler, container, false);
        recyclerView = view.findViewById(R.id.recycler2);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dealersViewModel = ViewModelProviders.of(this).get(DealersViewModel.class);
        observeViewModel(dealersViewModel);
    }

    private void observeViewModel(DealersViewModel dealersViewModel){
        dealersViewModel.getdealersinfolistobservable().observe(this, new Observer<ArrayList<DealersInfo>>() {
            @Override
            public void onChanged(@Nullable ArrayList<DealersInfo> dealersInfo) {
                if (dealersInfo != null){
                    dealersListAdapter = new DealersListAdapter(dealersInfo, getFragmentManager(),getActivity());
                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(dealersListAdapter);
                }
            }
        });
    }
}
