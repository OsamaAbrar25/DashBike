package com.example.osama.dashbike.Views.Adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osama.dashbike.Models.BookingsInfo;
import com.example.osama.dashbike.R;

import java.util.ArrayList;

public class BookingsListAdapter extends RecyclerView.Adapter<BookingsListAdapter.BookingsListHolder> {
    private ArrayList<BookingsInfo> arrayList;
    private FragmentManager fragmentManager;

    public BookingsListAdapter(ArrayList<BookingsInfo> arrayList, FragmentManager fragmentManager) {
        this.arrayList = arrayList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public BookingsListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_bookings, viewGroup, false);
        return new BookingsListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsListHolder bookingsListHolder, int i) {
        bookingsListHolder.textView_bikemodel.setText(arrayList.get(i).getBike_model());
        bookingsListHolder.textView_durationR.setText(arrayList.get(i).getDuration());
        bookingsListHolder.textView_bookingdateR.setText(arrayList.get(i).getDob());
        bookingsListHolder.textView_trxnamtR.setText(arrayList.get(i).getTransaction_amt());
        bookingsListHolder.textView_dealerR.setText(arrayList.get(i).getDealer());
        bookingsListHolder.textView_orderidR.setText(arrayList.get(i).getOrd_id());
        bookingsListHolder.textView_statusbooked.setText(arrayList.get(i).getIs_accepted());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class BookingsListHolder extends RecyclerView.ViewHolder {

        TextView textView_dealer, textView_bikemodel, textView_duration, textView_durationR, textView_bookingdate,
                textView_bookingdateR, textView_trxnamt, textView_trxnamtR, textView_dealerR, textView_orderidR, textView_orderid,
                textView_statusbooked, textView_statuscancelled;

        public BookingsListHolder(@NonNull View itemView) {
            super(itemView);
            textView_dealer = itemView.findViewById(R.id.textView_dealer);
            textView_bikemodel = itemView.findViewById(R.id.textView_bikemodel);
            textView_duration = itemView.findViewById(R.id.textView_duration);
            textView_durationR = itemView.findViewById(R.id.textView_durationR);
            textView_bookingdate = itemView.findViewById(R.id.textView_bookingdate);
            textView_bookingdateR = itemView.findViewById(R.id.textView_bookingdateR);
            textView_trxnamt = itemView.findViewById(R.id.textView_trxnamt);
            textView_trxnamtR = itemView.findViewById(R.id.textView_trxnamtR);
            textView_dealer = itemView.findViewById(R.id.textView_dealer);
            textView_dealerR = itemView.findViewById(R.id.textView_dealerR);
            textView_orderid = itemView.findViewById(R.id.textView_orderid);
            textView_orderidR = itemView.findViewById(R.id.textView_orderidR);
            textView_statusbooked = itemView.findViewById(R.id.textView_statusbooked);
            textView_statuscancelled = itemView.findViewById(R.id.textView_statuscancelled);
        }
    }
}
