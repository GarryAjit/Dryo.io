package com.example.intel.dryoio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Laundry_CompleteBills extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Bill> pendingBills = new ArrayList<>();
    List<Bill> pendingBillstemp = new ArrayList<>();
    ProgressBar progressBar;
    DatabaseReference reff;
    String laundryUsername;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry__complete_bills);

        sharedPreferences = getSharedPreferences("Pref",0);

        Intent intent = getIntent();
        laundryUsername = sharedPreferences.getString("username",null);


        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.pendingBillsRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.VISIBLE);

        reff = FirebaseDatabase.getInstance().getReference().child("bill");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot shot : dataSnapshot.getChildren())
                {
                    pendingBills.add(shot.getValue(Bill.class));
                }

                for(int i=0;i<pendingBills.size();i++)
                {
                    if(pendingBills.get(i).isPaid()==true && pendingBills.get(i).getLaundryVendorUsername().equals(laundryUsername))
                    {
                        pendingBillstemp.add(pendingBills.get(i));
                    }
                }

                if(pendingBillstemp.size()==0)
                {
                    Toast.makeText(Laundry_CompleteBills.this, "No records to show", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    CompleteBillsAdapter adapter = new CompleteBillsAdapter(Laundry_CompleteBills.this,pendingBillstemp,laundryUsername);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
