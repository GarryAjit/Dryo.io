package com.example.intel.dryoio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class Student_laundry_view_bill extends AppCompatActivity {

    TextView tvLaundryUsername, tvStudentUsername, tvCurrentDate, tvDueDate, tvTshirt, tvTowel, tvShirt,
            tvPant, tvHanky, tvBlanket, tvSocks, tvUnderwear,tvTotal;
    Button btnPayBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_laundry_view_bill);

        tvLaundryUsername = findViewById(R.id.tvLaundryUsername);
        tvStudentUsername = findViewById(R.id.tvStudentUsername);
        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        tvDueDate = findViewById(R.id.tvDueDate);
        tvTshirt = findViewById(R.id.tvTshirt);
        tvTowel = findViewById(R.id.tvTowel);
        tvShirt = findViewById(R.id.tvShirt);
        tvPant = findViewById(R.id.tvPant);
        tvHanky = findViewById(R.id.tvHanky);
        tvBlanket = findViewById(R.id.tvBlanket);
        tvSocks = findViewById(R.id.tvSocks);
        tvUnderwear = findViewById(R.id.tvUnderwear);
        tvTotal = findViewById(R.id.tvTotal);
        btnPayBill = findViewById(R.id.btnPayBill);

        final Intent intent = getIntent();
        final String laundryUsername = intent.getStringExtra("username");
        final String studentUsername = intent.getStringExtra("studentUsername");
        final String currentDate = intent.getStringExtra("currentDate");
        String dueDate = intent.getStringExtra("dueDate");
        int tshirt = intent.getIntExtra("tshirt",0);
        int shirt = intent.getIntExtra("shirt",0);
        int towel = intent.getIntExtra("towel",0);
        int pant = intent.getIntExtra("pant",0);
        int hanky = intent.getIntExtra("hanky",0);
        int blanket = intent.getIntExtra("blanket",0);
        int socks = intent.getIntExtra("socks",0);
        int underwear = intent.getIntExtra("underwear",0);
        int total = intent.getIntExtra("total",0);

        tvLaundryUsername.setText(laundryUsername);
        tvStudentUsername.setText(studentUsername);
        tvCurrentDate.setText(currentDate);
        tvDueDate.setText(dueDate);
        tvTshirt.setText(String.valueOf(tshirt));
        tvTowel.setText(String.valueOf(towel));
        tvShirt.setText(String.valueOf(shirt));
        tvPant.setText(String.valueOf(pant));
        tvHanky.setText(String.valueOf(hanky));
        tvBlanket.setText(String.valueOf(blanket));
        tvSocks.setText(String.valueOf(socks));
        tvUnderwear.setText(String.valueOf(underwear));
        tvTotal.setText(String.valueOf(total));

        btnPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Student_laundry_view_bill.this, StudentWallet.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(Student_laundry_view_bill.this, Student_pending_bills.class));
        finish();
    }
}
