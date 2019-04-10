package com.example.intel.dryoio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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

public class StudentWallet extends AppCompatActivity {

    TextView tvBalance;
    EditText etCardNumber, etCvv, etDueDate, etLaundryUsername, etAddAmount, etPayAmount;
    Button btnAdd, btnPay;
    SharedPreferences sharedPreferences;
    String studentUsername;
    ProgressBar progress;
    DatabaseReference reff;
    Student student;
    Double balance;
    LaundryVendor laundryVendor;
    Double lbalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_wallet);

        tvBalance = findViewById(R.id.tvBalance);
        etCardNumber = findViewById(R.id.etCardNumber);
        etCvv = findViewById(R.id.etCvv);
        etDueDate = findViewById(R.id.etExpiryDate);
        etLaundryUsername = findViewById(R.id.etLaundryUsername);
        etAddAmount = findViewById(R.id.etAddAmount);
        etPayAmount = findViewById(R.id.etPayAmount);
        btnAdd = findViewById(R.id.btnAdd);
        btnPay = findViewById(R.id.btnPay);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        sharedPreferences = getSharedPreferences("Pref",0);
        studentUsername = sharedPreferences.getString("username",null);

        reff = FirebaseDatabase.getInstance().getReference().child("student").child(studentUsername);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student = dataSnapshot.getValue(Student.class);
                balance = student.getBalance();
                tvBalance.setText(String.valueOf(balance));
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);

                if(etCardNumber.getText().toString().trim().length()!=16)
                {
                    etCardNumber.setError("Ener a valid card number");
                    progress.setVisibility(View.INVISIBLE);
                }

                else if(etCvv.getText().toString().trim().length()!=3)
                {
                    etCvv.setError("Enter a valid cvv");
                    progress.setVisibility(View.INVISIBLE);
                }

                else if(etDueDate.getText().toString().trim().length()!=7)
                {
                    etDueDate.setError("Enter valid date");
                    progress.setVisibility(View.INVISIBLE);
                }

                else
                {

                    DatabaseReference reff1 = FirebaseDatabase.getInstance().getReference().child("student").child(studentUsername);
                    balance += Double.parseDouble(etAddAmount.getText().toString().trim());
                    reff1.child("balance").setValue(balance);
                    progress.setVisibility(View.INVISIBLE);
                    Toast.makeText(StudentWallet.this, "Balance Added Successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etLaundryUsername.getText().toString().trim().equals(""))
                {
                    etLaundryUsername.setError("Enter valid username");
                }

                else if(etPayAmount.getText().toString().trim().equals(""))
                {
                    etPayAmount.setError("Enter valid amount");
                }

                else
                {
                    if(Double.parseDouble(etPayAmount.getText().toString().trim()) > balance)
                    {
                       // Toast.makeText(StudentWallet.this, "Insufficient balance", Toast.LENGTH_SHORT).show();
                        ShowMessage("DRYO.IO balance error","Insufficient balance");
                    }

                    else
                    {
                        final DatabaseReference reff2 = FirebaseDatabase.getInstance().getReference().child("laundryvendor").child(etLaundryUsername.getText().toString().trim());
                        reff2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                laundryVendor = dataSnapshot.getValue(LaundryVendor.class);
                                lbalance = laundryVendor.getBalance();

                                lbalance += Double.parseDouble(etPayAmount.getText().toString().trim());
                                balance -= Double.parseDouble(etPayAmount.getText().toString().trim());

                                reff.child("balance").setValue(balance);
                                reff2.child("balance").setValue(lbalance);

                                Toast.makeText(StudentWallet.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }


                }
            }
        });


    }

    public void ShowMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StudentWallet.this, Student_pending_bills.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
