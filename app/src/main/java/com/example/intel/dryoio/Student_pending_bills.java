package com.example.intel.dryoio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Student_pending_bills extends AppCompatActivity {

    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    List<Bill> pendingBills = new ArrayList<>();
    List<Bill> pendingBillstemp = new ArrayList<>();
    ProgressBar progressBar;
    DatabaseReference reff;
    String studentUsername;
    SharedPreferences sharedPreferences;
    TextView tvName;
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pending_bills);

        sharedPreferences = getSharedPreferences("Pref",0);

        Intent intent = getIntent();
        studentUsername = sharedPreferences.getString("username",null);

        navigationView = findViewById(R.id.navigationView1);
        drawerLayout = findViewById(R.id.drawer1);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Log.i("navigation","listener");

                int id = item.getItemId();
                switch (id)
                {
                    case R.id.complete:
                        Log.i("navigation","complete");
                        Intent intent2 = new Intent(Student_pending_bills.this, Student_complete_bills.class);
                        startActivity(intent2);
                        break;
                    case R.id.logout:
                        Log.i("navigation","logout");
                        Intent intent = new Intent(Student_pending_bills.this, StudentLogin.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.wallet:
                        Intent intent1 = new Intent(Student_pending_bills.this,StudentWallet.class);
                        startActivity(intent1);
                    default:
                        Log.i("navigation","default");
                        return true;
                }

                return true;
            }
        });

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.pendingBillsRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        View headerView = navigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.tvName);
        tvName.setText(studentUsername);
        ivLogo = headerView.findViewById(R.id.ivLogo);
        TextDrawable drawable = TextDrawable.builder().buildRound(String.valueOf(Character.toUpperCase(studentUsername.charAt(0))), Color.RED);
        ivLogo.setImageDrawable(drawable);

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
                    if(pendingBills.get(i).isPaid()==false && pendingBills.get(i).getStudentUsername().equals(studentUsername) )
                    {
                        pendingBillstemp.add(pendingBills.get(i));
                    }
                }



//                for(int i=0;i<pendingBills.size();i++)
//                {
//                    Log.i("aayega",pendingBills.get(i).getStudentUsername());
//                    Log.i("aayega",studentUsername);
//                    if(pendingBills.get(i).getStudentUsername().equals(studentUsername))
//                    {
//                        pendingBillstemp.add(pendingBills.get(i));
//                    }
//                }


                if(pendingBillstemp.size()==0)
                {
                    Toast.makeText(Student_pending_bills.this, "No records to show", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    StudentPendingBillsAdapter adapter = new StudentPendingBillsAdapter(Student_pending_bills.this,pendingBillstemp,studentUsername);
                    recyclerView.setAdapter(adapter);
                }


                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Student_pending_bills.this, StudentLogin.class);
        startActivity(intent);
        finish();
    }
}
