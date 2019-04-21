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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.osama.dashbike.Models.BikesInfo;
import com.example.osama.dashbike.R;
import com.example.osama.dashbike.Views.ui.BikeDetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BikesListAdapter extends RecyclerView.Adapter<BikesListAdapter.BikesListHolder> {

    private ArrayList<BikesInfo> arrayList;
    private FragmentManager fragmentManager;
    private Context context;

    public BikesListAdapter(ArrayList<BikesInfo> arrayList, FragmentManager fragmentManager, Context context){
        this.arrayList = arrayList;
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

        thugHolder.textView_bikemodel_1.setText(arrayList.get(i).getBike_model());
        thugHolder.textView_rate_1.setText(arrayList.get(i).getTxt2());
        Picasso.get()
                .load(arrayList.get(i).getImageUrl())
                .centerCrop()
                //.transform(new RoundedTransformation(5,0))
                .resize(150,150)
                .into(thugHolder.imageView_bikepic);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class BikesListHolder extends RecyclerView.ViewHolder{

        TextView textView_bikemodel_1,textView_rate_1;
        ImageView imageView_bikepic;
        public BikesListHolder(@NonNull View itemView) {
            super(itemView);
            textView_bikemodel_1 = itemView.findViewById(R.id.textView_bikemodel_1);
            textView_rate_1 = itemView.findViewById(R.id.textView_rate_1);
            imageView_bikepic = itemView.findViewById(R.id.imageView_bikepic);

            // GO TO BIKE'S DETAIL VIEW
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = arrayList.get(getAdapterPosition()).getId();
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
