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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LaundryLogin extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvRegisterNow;
    ProgressBar progress;
    DatabaseReference reff;
    LaundryVendor laundryvendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterNow = findViewById(R.id.tvRegisterNow);
        progress = findViewById(R.id.progress);

        progress.setVisibility(View.INVISIBLE);

        laundryvendor=new LaundryVendor();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress.setVisibility(View.VISIBLE);

                if(etUsername.getText().toString().trim().equals(""))
                {
                    etUsername.setError("Please enter your username");
                    progress.setVisibility(View.INVISIBLE);
                }
                else if(etPassword.getText().toString().trim().equals(""))
                {
                    etPassword.setError("Please enter your password");
                    progress.setVisibility(View.INVISIBLE);
                }

                else
                {
                    boolean is_connected = isNetworkConnected();

                    if(is_connected==false)
                    {
                        Toast.makeText(LaundryLogin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.INVISIBLE);
                    }

                    else
                    {
                        //Toast.makeText(LaundryLogin.this, "Login done", Toast.LENGTH_SHORT).show();
                        reff = FirebaseDatabase.getInstance().getReference().child("laundryvendor");

                        final String username = etUsername.getText().toString().trim();
                        final String password = etPassword.getText().toString().trim();

                        reff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                ArrayList<LaundryVendor> laundryVendorsList = new ArrayList<>();

                                for (DataSnapshot shot : dataSnapshot.getChildren())
                                {
                                    laundryVendorsList.add(shot.getValue(LaundryVendor.class));
                                }

                                int flag=0;
                                for(int i=0;i<laundryVendorsList.size();i++)
                                {
                                    if(laundryVendorsList.get(i).getUsername().equals(username))
                                    {
                                        flag=1;
                                        if(laundryVendorsList.get(i).getPassword().equals(password))
                                        {
                                            progress.setVisibility(View.INVISIBLE);
                                            Toast.makeText(LaundryLogin.this, "Login Success", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LaundryLogin.this, LaundryBillGenerator.class);
                                            intent.putExtra("username",username);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            etPassword.setError("Incorrect Password");
                                            progress.setVisibility(View.INVISIBLE);
                                        }
                                        break;
                                    }
                                }

                                if(flag==0)
                                {
                                    etUsername.setError("Username doesn't exist");
                                    progress.setVisibility(View.INVISIBLE);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }


            }
        });

        tvRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean is_connected = isNetworkConnected();

                if(is_connected==false)
                {
                    Toast.makeText(LaundryLogin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Intent intent = new Intent(LaundryLogin.this,LaundryRegister.class);
                    startActivity(intent);
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
