package com.example.osama.dashbike.Views.Adapters;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.osama.dashbike.Utility.ApplicationContext;
import com.example.osama.dashbike.Models.BikesInfo;
import com.example.osama.dashbike.R;
import com.example.osama.dashbike.Views.ui.BikeDetailsFragment;
import com.example.osama.dashbike.Utility.VolleySingleton;

import java.util.ArrayList;


public class BikesListAdapter extends RecyclerView.Adapter<BikesListAdapter.BikesListHolder> {

    private ArrayList<BikesInfo> arrayList1;
    private ImageLoader imageLoader;
    private FragmentManager fragmentManager;
    private Context context;

    public BikesListAdapter(ArrayList<BikesInfo> arrayList, FragmentManager fragmentManager, Context context){
        this.arrayList1 = arrayList;
        this.fragmentManager = fragmentManager;
        this.context = context;


        //very costly method i.e. slow performance. Modify it.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BikesListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.fragment_bikes, viewGroup, false);
        BikesListHolder bikesListHolder = new BikesListHolder(view);
        return bikesListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BikesListHolder thugHolder, int i) {

        thugHolder.text.setText(arrayList1.get(i).getTxt1());
        thugHolder.text2.setText(arrayList1.get(i).getTxt2());
        imageLoader = VolleySingleton.getInstance(ApplicationContext.getAppContext()).getmImageLoader();
        thugHolder.image.setImageUrl(arrayList1.get(i).getImageUrl(),imageLoader);

    }

    @Override
    public int getItemCount() {
        return arrayList1.size();
    }

    public class BikesListHolder extends RecyclerView.ViewHolder{

        TextView text,text2;
        NetworkImageView image;
        public BikesListHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textView11);
            text2 = itemView.findViewById(R.id.textView_bikemodel_R);
            image = itemView.findViewById(R.id.imageView11);

            // GO TO BIKE'S DETAIL VIEW
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = arrayList1.get(getAdapterPosition()).getTxt1();
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putString("BIKEID", id).apply();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_home_container, new BikeDetailsFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

        }
    }
}
