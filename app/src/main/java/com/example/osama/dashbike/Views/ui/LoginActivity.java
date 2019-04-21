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
    Button button_add;
    EditText editText_email, editText_password;
    TextView textView_email, textView_password, textView_1, textView_2;
    LoginRepository loginRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button_add = findViewById(R.id.button_add);
        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);
        textView_email = findViewById(R.id.textView_email);
        textView_password = findViewById(R.id.textView_password);
        textView_1 = findViewById(R.id.textView_1);
        textView_2 = findViewById(R.id.textView_2);

        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("KEY",null)!=null){
            //GO TO HOME ACTIVITY
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        //GO TO REGISTRATION ACTIVITY
        textView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });


        //ON SIGN-IN BUTTON CLICK
        button_add.setOnClickListener(new View.OnClickListener() {
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
                loginRepository.getLogin(editText_email.getText().toString(), editText_password.getText().toString());
                loginRepository.Login();
            }
        });


    }
}

