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

import com.example.osama.dashbike.Models.DealersInfo;
import com.example.osama.dashbike.R;
import com.example.osama.dashbike.Utility.RoundedTransformation;
import com.example.osama.dashbike.Views.ui.BikesFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DealersListAdapter extends RecyclerView.Adapter<DealersListAdapter.DealerListHolder>{

    private ArrayList<DealersInfo> arrayList;
    private FragmentManager fragmentManager;
    private Context context;

    public DealersListAdapter(ArrayList<DealersInfo> arrayList, FragmentManager fragmentManager, Context context) {
        this.arrayList = arrayList;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @NonNull
    @Override
    public DealerListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.fragment_dealers, viewGroup, false);
        DealerListHolder dealerListHolder = new DealerListHolder(view);
        return dealerListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DealerListHolder dealerListHolder, int i) {
        dealerListHolder.text.setText(arrayList.get(i).getTxt1());
        dealerListHolder.text2.setText(arrayList.get(i).getTxt2());
        Picasso.get()
                .load(arrayList.get(i).getImageurl())
                .centerCrop()
                .resize(100, 100)
                .transform(new RoundedTransformation(200,0))
                .into(dealerListHolder.image);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DealerListHolder extends RecyclerView.ViewHolder{
        TextView text,text2;
        ImageView image;

        public DealerListHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textView_info);
            text2 = itemView.findViewById(R.id.textView_dealername);
            image = itemView.findViewById(R.id.imageView_dealerspic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = arrayList.get(getAdapterPosition()).getTxt1();
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putString("DEALERID", id).apply();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_home_container, new BikesFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

        }
    }
}
