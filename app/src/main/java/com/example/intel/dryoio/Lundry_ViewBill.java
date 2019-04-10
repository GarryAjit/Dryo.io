package com.example.intel.dryoio;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Lundry_ViewBill extends AppCompatActivity {

    TextView tvLaundryUsername, tvStudentUsername, tvCurrentDate, tvDueDate, tvTshirt, tvTowel, tvShirt,
            tvPant, tvHanky, tvBlanket, tvSocks, tvUnderwear,tvTotal;
    Button btnMark, btnSMS;
    DatabaseReference reff;
    String studentMobNo;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lundry__view_bill);

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
        btnMark = findViewById(R.id.btnMarkComplete);
        btnSMS = findViewById(R.id.btnGenerateSms);
        progress = findViewById(R.id.progress);

        progress.setVisibility(View.VISIBLE);

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

        reff = FirebaseDatabase.getInstance().getReference().child("student").child(studentUsername).child("mobileNo");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentMobNo = (String)dataSnapshot.getValue();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(Lundry_ViewBill.this, "Network error!", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);

            }
        });


        btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] dates = currentDate.split("/");


                DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("bill").child(laundryUsername + studentUsername + dates[0]+ dates[1]+ dates[2]).child("paid");
                r.setValue(true);
                Toast.makeText(Lundry_ViewBill.this, "Marked Sucessfully", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Lundry_ViewBill.this, Laundry_PendingBills.class);
                startActivity(intent1);
                finish();

            }
        });

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String no="7976467851";
                String msg="This is a reminder to pay your pending laundry bills";

//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("sms"+no));
//                intent.putExtra("sms_body",msg);
//                startActivity(intent);

                // The number on which you want to send SMS
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", no, null)));

                Uri uri = Uri.parse("smsto:" + studentMobNo);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", msg);
                startActivity(it);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(Lundry_ViewBill.this, Laundry_PendingBills.class));
        finish();
    }
}
