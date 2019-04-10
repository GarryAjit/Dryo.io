package com.example.intel.dryoio;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class laundry_update extends AppCompatActivity {

    Button btnUpdate;
    EditText etName, etMobileNumber, etUsername, etPassword, etRePassword;
    DatabaseReference reff,reff1;
    LaundryVendor laundryVendor;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_update);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        etName = findViewById(R.id.etName);
        etMobileNumber = findViewById(R.id.etMobileNo);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        btnUpdate = findViewById(R.id.btnUpdate);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        reff = FirebaseDatabase.getInstance().getReference().child("laundryvendor").child(username);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LaundryVendor laundryVendor = dataSnapshot.getValue(LaundryVendor.class);
                etName.setText(laundryVendor.getName());
                etUsername.setText(laundryVendor.getUsername());
                etMobileNumber.setText(laundryVendor.getPhoneno());
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(laundry_update.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        laundryVendor = new LaundryVendor();
                        reff = FirebaseDatabase.getInstance().getReference().child("laundryvendor");
                        laundryVendor.setName(etName.getText().toString().trim());
                        laundryVendor.setPhoneno(etMobileNumber.getText().toString().trim());
                        laundryVendor.setUsername(etUsername.getText().toString().trim());
                        laundryVendor.setPassword(etPassword.getText().toString().trim());
                        laundryVendor.setBalance(0.00);

                        //reff.push().setValue(laundryVendor);
                        reff.child(etUsername.getText().toString().trim()).setValue(laundryVendor);

                        Toast.makeText(laundry_update.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(laundry_update.this, LaundryLogin.class);
                        startActivity(intent);
                        finish();

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
