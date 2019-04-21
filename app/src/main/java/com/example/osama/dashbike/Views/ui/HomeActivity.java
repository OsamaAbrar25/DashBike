package com.example.osama.dashbike.Views.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.osama.dashbike.R;
import com.example.osama.dashbike.Repository.BookBikeRepository;
import com.example.osama.dashbike.Repository.LogoutListener;
import com.example.osama.dashbike.Repository.LogoutRepository;
import com.example.osama.dashbike.Repository.NotificationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;


public class HomeActivity extends AppCompatActivity implements PaymentResultListener {
    private DrawerLayout drawerLayout;
    private LogoutRepository logoutRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_home);//bikes_recycler);

        // SMS READ PERMISSION
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        //GENERATE NEW FCM TOKEN
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("ERROR", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        NotificationRepository notificationRepository = new NotificationRepository(getApplicationContext());
                        notificationRepository.sendToken(token);
                        Log.d("token", token);

                    }
                });

        //ACTIONBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
        }

        //NAVIGATION DRAWER
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(true);
                // close drawer when item is tapped
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.home_list:
                        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.activity_home_container, new DealersFragment());
                        fragmentTransaction1.addToBackStack(null);
                        fragmentTransaction1.commit();
                        return true;
                    case R.id.my_bookings:
                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.activity_home_container, new BookingsFragment());
                        fragmentTransaction2.addToBackStack(null);
                        fragmentTransaction2.commit();
                        return true;
                    case R.id.log_out:
                        logoutRepository = new LogoutRepository(getApplicationContext(), new LogoutListener() {
                            @Override
                            public void onLogout(JSONObject response) {
                                try {
                                    if (response.getString("detail").equals("Successfully logged out.")) {
                                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("KEY", null).apply();
                                        Toast.makeText(getApplicationContext(), response.getString("detail"), Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        logoutRepository.Logout();
                        return true;
                }

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here

                return true;

            }
        });

        //DEALERS LIST FRAGMENT
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home_container, new DealersFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        /*
         * Preload payment resources
         */
        Checkout.preload(getApplicationContext());

    }

    //TO OPEN THE DRAWER
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //RAZORPAY'S METHODS
    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
        //BookBikeRepository bookBikeRepository = new BookBikeRepository(getApplicationContext());
        //bookBikeRepository.getIdAndSetTransactionId(s);
        NotificationRepository notificationRepository = new NotificationRepository(getApplicationContext());
        notificationRepository.sendNotification();

    }

    @Override
    public void onPaymentError(int i, String s) {

        Log.e("error",s);
    }
}


