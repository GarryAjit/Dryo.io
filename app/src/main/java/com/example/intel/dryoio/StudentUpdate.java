package com.example.intel.dryoio;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.VisibleForTesting;
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

public class StudentUpdate extends AppCompatActivity {

    Button btnUpdate;
    EditText etName, etMobileNumber, etBlockNo, etRoomNo, etUsername, etPassword, etRePassword;
    DatabaseReference reff;
    Student student;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        etName = findViewById(R.id.etName);
        etMobileNumber = findViewById(R.id.etMobileNo);
        etBlockNo = findViewById(R.id.etBlockNo);
        etRoomNo = findViewById(R.id.etRoomNo);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        btnUpdate = findViewById(R.id.btnUpdate);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        reff = FirebaseDatabase.getInstance().getReference().child("student").child(username);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student = dataSnapshot.getValue(Student.class);
                etName.setText(student.getName());
                etMobileNumber.setText(student.getMobileNo());
                etBlockNo.setText(String.valueOf(student.getBlockNo()));
                etRoomNo.setText(String.valueOf(student.getRoomNo()));
                etUsername.setText(String.valueOf(student.getUsername()));
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

                else if(Integer.parseInt(etBlockNo.getText().toString().trim())<1 || Integer.parseInt(etBlockNo.getText().toString().trim())>22)
                {
                    etBlockNo.setError("Please enter a valid block number");
                }

                else if(etRoomNo.getText().toString().trim().equals(""))
                {
                    etRoomNo.setError("Please enter your room number");
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
                        Toast.makeText(StudentUpdate.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        reff = FirebaseDatabase.getInstance().getReference().child("student");
                        student.setName(etName.getText().toString().trim());
                        student.setMobileNo(etMobileNumber.getText().toString().trim());
                        student.setBlockNo(Integer.parseInt(etBlockNo.getText().toString().trim()));
                        student.setRoomNo(Integer.parseInt(etRoomNo.getText().toString().trim()));
                        student.setUsername(etUsername.getText().toString().trim());
                        student.setPassword(etPassword.getText().toString().trim());

                        //reff.push().setValue(student);
                        reff.child(etUsername.getText().toString().trim()).setValue(student);

                        Toast.makeText(StudentUpdate.this, "Update Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(StudentUpdate.this, StudentLogin.class);
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
