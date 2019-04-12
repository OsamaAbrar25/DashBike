package com.example.osama.dashbike.Views.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.osama.dashbike.R;
import com.example.osama.dashbike.Repository.RegisterListener;
import com.example.osama.dashbike.Repository.RegistrationRepository;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    Button button;
    EditText editText, editText2, editText3, editText4, editText_contactno;
    TextView textView, textView2, textView3, textView4, textView5, textView_contactno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText_contactno = findViewById(R.id.editText_contactno);
        textView = findViewById(R.id.textView_bikemodel);
        textView2 = findViewById(R.id.textView_bikemodel_R);
        textView3 = findViewById(R.id.textView_rate);
        textView4 = findViewById(R.id.textView_perhr);
        textView5 = findViewById(R.id.textView_duration);
        textView_contactno = findViewById(R.id.textView_contactno);

        //ON REGISTER BUTTON CLICK
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationRepository registrationRepository = new RegistrationRepository(getApplicationContext(), new RegisterListener() {
                    @Override
                    public void onDataReceived(JSONObject response) {
                        if (response.has("detail")) {
                            //Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            //startActivity(intent);
                            finish();
                        }
                        try {
                            String res = response.getString("detail");
                            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
                registrationRepository.getRegistration(editText.getText().toString(), editText2.getText().toString(), editText_contactno.getText().toString(),
                        editText3.getText().toString(), editText4.getText().toString());
                try {
                    registrationRepository.Register();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
