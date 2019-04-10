package com.example.intel.dryoio;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnStudent;
    Button btnLaundryVendor;
    boolean is_connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStudent = findViewById(R.id.btnStudent);
        btnLaundryVendor = findViewById(R.id.btnLaundryVendor);

        btnStudent.setEnabled(false);
        btnLaundryVendor.setEnabled(false);

        
            btnStudent.setEnabled(true);
            btnLaundryVendor.setEnabled(true);

            btnStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    is_connected = isNetworkConnected();

                    if(is_connected==false)
                    {
                        Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        Intent intent = new Intent(MainActivity.this, StudentLogin.class);
                        startActivity(intent);
                    }
                }
            });

            btnLaundryVendor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    is_connected = isNetworkConnected();

                    if(is_connected==false)
                    {
                        Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        Intent intent = new Intent(MainActivity.this, LaundryLogin.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
