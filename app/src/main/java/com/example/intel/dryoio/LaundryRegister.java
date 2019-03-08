package com.example.intel.dryoio;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LaundryRegister extends AppCompatActivity {

    Button btnRegister;
    EditText etName, etMobileNumber, etUsername, etPassword, etRePassword;
    DatabaseReference reff;
    LaundryVendor laundryVendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_register);

        etName = findViewById(R.id.etName);
        etMobileNumber = findViewById(R.id.etMobileNo);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        btnRegister = findViewById(R.id.btnRegister);

        laundryVendor=new LaundryVendor();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().trim().equals(""))
                {
                    etName.setError("Please enter your name");
                }

                else if(etMobileNumber.getText().toString().trim().length()!=10)
                {
                    etMobileNumber.setError("Please Enter a valid phone number");
                }

                else if(etUsername.getText().toString().trim().length()<5)
                {
                    etUsername.setError("Please enter a valid username");
                }

                else if(etPassword.getText().toString().trim().length()<5)
                {
                    etPassword.setError("Please enter a valid password");
                }

                else if(!(etRePassword.getText().toString().trim().equals(etPassword.getText().toString().trim())))
                {
                    etRePassword.setError("Passwords not matching");
                }

                else
                {
                    boolean is_connected = isNetworkConnected();

                    if(is_connected==false)
                    {
                        Toast.makeText(LaundryRegister.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        reff = FirebaseDatabase.getInstance().getReference().child("laundryvendor");
                        laundryVendor.setName(etName.getText().toString().trim());
                        laundryVendor.setPhoneno(etMobileNumber.getText().toString().trim());
                        laundryVendor.setUsername(etUsername.getText().toString().trim());
                        laundryVendor.setPassword(etPassword.getText().toString().trim());

                        reff.push().setValue(laundryVendor);

                        Toast.makeText(LaundryRegister.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager)   getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }
}
