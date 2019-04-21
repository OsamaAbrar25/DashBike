package com.example.osama.dashbike.Views.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.osama.dashbike.Models.BikeDetailsInfo;
import com.example.osama.dashbike.R;
import com.example.osama.dashbike.Repository.BookBikeRepository;
import com.example.osama.dashbike.Utility.SpinnerTimePickerDialog;
import com.example.osama.dashbike.ViewModels.BikeDetailsViewModel;
import com.example.osama.dashbike.Utility.VolleySingleton;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BikeDetailsFragment extends Fragment {
    TextView textView_bikemodel, textView_bikemodel_R, textView3, textView_perhr, textView_perhd, textView_perfd,
            textView_duration, textView_choosetime, textView7;
    NumberPicker numberPicker;
    Spinner spinner;
    Button button_booknow;
    BikeDetailsViewModel bikeDetailsViewModel;
    NetworkImageView imageView_bike;
    int duration;
    String rate ;
    String duration_unit;
    String dob;
    String bike_model;
    float trxn_amt;

    ArrayList<BikeDetailsInfo> bikeDetailsInfo = null;

    //enter off hour in 24 hrs format
    private int offHour = 10;
    private int onHour = 7;

    public BikeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bike_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView_bikemodel =  view.findViewById(R.id.textView_bikemodel);
        textView_bikemodel_R = view.findViewById(R.id.textView_bikemodel_R);
        textView3 = view.findViewById(R.id.textView_rate);
        //textView_perhr = view.findViewById(R.id.textView_perhr);
        //textView_perhd = view.findViewById(R.id.textView_perhd);
        //textView_perfd = view.findViewById(R.id.textView_perfd);
        textView_duration = view.findViewById(R.id.textView_duration);
        textView_choosetime = view.findViewById(R.id.textView_choosetime);
        textView7 = view.findViewById(R.id.textView7);
        numberPicker = view.findViewById(R.id.numberPicker);
        button_booknow = view.findViewById(R.id.button_booknow);
        imageView_bike = view.findViewById(R.id.imageView_bike);
        //radioGroup = view.findViewById(R.id.radioGroup);
        spinner = view.findViewById(R.id.spinner);


        //DURATION PICKER
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(30);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                duration = newVal;
            }
        });


        //TIME PICKER
        textView_choosetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                /*TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                }, hour, minute, false);
                timePickerDialog.show();*/
                SpinnerTimePickerDialog spinnerTimePickerDialog = new SpinnerTimePickerDialog(getActivity(), new SpinnerTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dob = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"T"+hourOfDay+":"+minute+"Z";

                    }
                }, hour, minute, false, 2, offHour, onHour);
                spinnerTimePickerDialog.show();
            }
        });

        //GET DIRECTIONS
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=28.632430,77.218790");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(mapIntent);
                }
            }
        });


        button_booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();

            }
        });


        //OBSERVE DATA FROM VIEWMODEL
        bikeDetailsViewModel = ViewModelProviders.of(this).get(BikeDetailsViewModel.class);
        observeViewModel(bikeDetailsViewModel);

        //RATE SPINNER
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rate = parent.getSelectedItem().toString().replaceAll("[^0-9]", "");
                duration_unit = parent.getSelectedItem().toString().substring(parent.getSelectedItem().toString().indexOf("/ ")+1).trim();
                Toast.makeText(getActivity(), duration_unit, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void observeViewModel(BikeDetailsViewModel bikeDetailsViewModel) {
        bikeDetailsViewModel.getBikeDetailsInfolistObservable().observe(this, new Observer<ArrayList<BikeDetailsInfo>>() {
            @Override
            public void onChanged(@Nullable ArrayList<BikeDetailsInfo> bikeDetailsInfos) {
                if (bikeDetailsInfos != null) {
                    bikeDetailsInfo  = bikeDetailsInfos;
                    ImageLoader imageLoader = VolleySingleton.getInstance(getContext()).getmImageLoader();
                    imageView_bike.setImageUrl(bikeDetailsInfos.get(0).getBike_img(), imageLoader);
                    textView_bikemodel_R.setText(bikeDetailsInfos.get(0).getBike_model());
                    ArrayList<String> spinner_items = new ArrayList<>();
                    spinner_items.add("Rs " + bikeDetailsInfos.get(0).getBike_rate_hr().concat(" / hr"));
                    spinner_items.add("Rs " + bikeDetailsInfos.get(0).getBike_rate_h().concat(" / half_day"));
                    spinner_items.add("Rs " + bikeDetailsInfos.get(0).getBike_rate_f().concat(" / full_day"));
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinner_items);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    //textView_perhr.setText(bikeDetailsInfos.get(0).getBike_rate_hr().concat(" / hr"));
                    //textView_perhd.setText(bikeDetailsInfos.get(0).getBike_rate_h().concat(" / half day"));
                    //textView_perfd.setText(bikeDetailsInfos.get(0).getBike_rate_f().concat(" / full day"));
                    bike_model = bikeDetailsInfos.get(0).getBike_model();
                }
            }
        });
    }


    //PAYMENT CHECKOUT
    public void startPayment() {
        //BOOKING
        BookBikeRepository bookBikeRepository = new BookBikeRepository(getActivity());
        trxn_amt = Float.parseFloat(rate)*duration;
        bookBikeRepository.setBookingDetails(bike_model, dob, dob, Integer.toString(duration), Float.toString(trxn_amt), "87987","3", duration_unit);
        bookBikeRepository.bookBike();

        //CHECKOUT
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.logo);

        final Activity activity = getActivity();

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Dashbike");
            options.put("description", "Order #123456");
            options.put("currency", "INR");
            options.put("amount", "500");

            //PASS EMAIL AND PHONE NO. AS PREFILL ARGUMENT TOO .....................................................
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("error", "Error in starting Razorpay Checkout", e);
        }
    }

}
