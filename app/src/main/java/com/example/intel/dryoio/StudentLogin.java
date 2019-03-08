package com.example.intel.dryoio;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentLogin extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvRegisterNow;
    DatabaseReference reff;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterNow = findViewById(R.id.tvRegisterNow);

        student = new Student();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUsername.getText().toString().trim().equals(""))
                {
                    etUsername.setError("Please enter your username");
                }
                else if(etPassword.getText().toString().trim().equals(""))
                {
                    etPassword.setError("Please enter your password");
                }

                else
                {
                    boolean is_connected = isNetworkConnected();

                    if(is_connected==false)
                    {
                        Toast.makeText(StudentLogin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        reff = FirebaseDatabase.getInstance().getReference().child("student");

                        final String username = etUsername.getText().toString().trim();
                        final String password = etPassword.getText().toString().trim();

                        reff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                ArrayList<Student> studentList = new ArrayList<>();

                                for (DataSnapshot shot : dataSnapshot.getChildren())
                                {
                                    studentList.add(shot.getValue(Student.class));
                                }

                                int flag=0;
                                for(int i=0;i<studentList.size();i++)
                                {
                                    if(studentList.get(i).getUsername().equals(username))
                                    {
                                        flag=1;
                                        if(studentList.get(i).getPassword().equals(password))
                                        {
                                            Toast.makeText(StudentLogin.this, "Login Success", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            etPassword.setError("Incorrect Password");
                                        }
                                        break;
                                    }
                                }

                                if(flag==0)
                                {
                                    etUsername.setError("Username doesn't exist");
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
                    Toast.makeText(StudentLogin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Intent intent = new Intent(StudentLogin.this,StudentRegister.class);
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
