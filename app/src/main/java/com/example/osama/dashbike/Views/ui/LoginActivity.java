package com.example.osama.dashbike.Views.ui;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.osama.dashbike.R;
import com.example.osama.dashbike.Repository.LoginKeyListener;
import com.example.osama.dashbike.Repository.LoginRepository;

public class LoginActivity extends AppCompatActivity {
    Button button;
    EditText editText, editText2;
    TextView textView, textView2, textView3, textView4;
    LoginRepository loginRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText3);
        textView = findViewById(R.id.textView_bikemodel);
        textView2 = findViewById(R.id.textView_bikemodel_R);
        textView3 = findViewById(R.id.textView_rate);
        textView4 = findViewById(R.id.textView_perhr);

        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("KEY",null)!=null){
            //GO TO HOME ACTIVITY
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        //GO TO REGISTRATION ACTIVITY
         textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });


        //ON SIGN-IN BUTTON CLICK
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRepository = new LoginRepository(getApplicationContext(), new LoginKeyListener() {
                    @Override
                    public void onLoginKeyReceived(String key) {
                        if (key != null) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                });
                loginRepository.getLogin(editText.getText().toString(), editText2.getText().toString());
                loginRepository.Login();
            }
        });


    }
}

