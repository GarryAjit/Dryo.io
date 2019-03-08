package com.example.intel.dryoio;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class LaundryBillGenerator extends AppCompatActivity {

    EditText etStudentUsername;
    Button btnDueDatePicker,btnTshirtMinus, btnTshirtPlus, btnShirtMinus, btnShirtPlus, btnPantMinus, btnPantPlus, btnHankyMinus, btnHankyPlus, btnBlanketMinus, btnBlanketPlus, btnTowelMinus, btnTowelPlus, btnSocksMinus, btnSocksPlus, btnUnderwearMinus, btnUnderwearPlus, btnGenerate;
    TextView tvTshirt,tvShirt,tvPant,tvHanky,tvBlanket,tvTowel,tvSocks,tvUnderwear;
    TextView tvFinalAmount,tvDueDate;
    int amount;
    DatabaseReference reff;
    Bill bill;
    AlertDialog.Builder builder;
    private int mYear, mMonth, mDay;
    String currentDate, dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_bill_generator);

        Intent intent = getIntent();
        final String laundryUsername = intent.getStringExtra("username");

        etStudentUsername = findViewById(R.id.etStudentUsername);
        btnTshirtMinus = findViewById(R.id.btntshirt_minus);
        btnTshirtPlus = findViewById(R.id.btntshirt_plus);
        btnShirtMinus = findViewById(R.id.btnshirt_minus);
        btnShirtPlus = findViewById(R.id.btnshirt_plus);
        btnPantMinus = findViewById(R.id.btnpant_minus);
        btnPantPlus = findViewById(R.id.btnpant_plus);
        btnHankyMinus = findViewById(R.id.btnhanky_minus);
        btnHankyPlus = findViewById(R.id.btnhanky_plus);
        btnBlanketMinus = findViewById(R.id.btnblanket_minus);
        btnBlanketPlus = findViewById(R.id.btnblanket_plus);
        btnTowelMinus = findViewById(R.id.btntowel_minus);
        btnTowelPlus = findViewById(R.id.btntowel_plus);
        btnSocksMinus = findViewById(R.id.btnsocks_minus);
        btnSocksPlus = findViewById(R.id.btnsocks_plus);
        btnUnderwearMinus = findViewById(R.id.btnunderwear_minus);
        btnUnderwearPlus = findViewById(R.id.btnunderwear_plus);
        tvTshirt = findViewById(R.id.tvTshirtQuantity);
        tvShirt = findViewById(R.id.tvShirtQuantity);
        tvPant = findViewById(R.id.tvPantQuantity);
        tvHanky = findViewById(R.id.tvHankyQuantity);
        tvBlanket = findViewById(R.id.tvBlanketQuantity);
        tvTowel = findViewById(R.id.tvTowelQuantity);
        tvSocks = findViewById(R.id.tvSocksQuantity);
        tvUnderwear = findViewById(R.id.tvUnderwearQuantity);
        tvFinalAmount = findViewById(R.id.tvFinalAmt);
        btnGenerate = findViewById(R.id.btngenerate);
        tvDueDate = findViewById(R.id.tvDueDate);
        btnDueDatePicker = findViewById(R.id.btnDueDate);


        builder = new AlertDialog.Builder(this);

        bill =new Bill();

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        currentDate = mDay + "/" + mMonth + "/" + mYear;

        btnDueDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(LaundryBillGenerator.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int dyear, int dmonth, int dday) {

                                dmonth++;
                                dueDate = dday + "/" + dmonth+ "/" + dyear;
                                tvDueDate.setText(dueDate);

                            }
                        },mYear,mMonth,mDay);

                datePickerDialog.show();

            }
        });

        btnTshirtMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(tvTshirt.getText().toString().trim());
                if(qty>0)
                {
                    qty--;
                }

                tvTshirt.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnTshirtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvTshirt.getText().toString().trim());
                if(qty>=0)
                {
                    qty++;
                }

                tvTshirt.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));

            }
        });

        btnShirtMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvShirt.getText().toString().trim());
                if(qty>0)
                {
                    qty--;
                }

                tvShirt.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnShirtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvShirt.getText().toString().trim());
                if(qty>=0)
                {
                    qty++;
                }

                tvShirt.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnPantMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvPant.getText().toString().trim());
                if(qty>0)
                {
                    qty--;
                }

                tvPant.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnPantPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvPant.getText().toString().trim());
                if(qty>=0)
                {
                    qty++;
                }

                tvPant.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnHankyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvHanky.getText().toString().trim());
                if(qty>0)
                {
                    qty--;
                }

                tvHanky.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnHankyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvHanky.getText().toString().trim());
                if(qty>=0)
                {
                    qty++;
                }

                tvHanky.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnBlanketMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvBlanket.getText().toString().trim());
                if(qty>0)
                {
                    qty--;
                }

                tvBlanket.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnBlanketPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvBlanket.getText().toString().trim());
                if(qty>=0)
                {
                    qty++;
                }

                tvBlanket.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnTowelMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvTowel.getText().toString().trim());
                if(qty>0)
                {
                    qty--;
                }

                tvTowel.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnTowelPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvTowel.getText().toString().trim());
                if(qty>=0)
                {
                    qty++;
                }

                tvTowel.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnSocksMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvSocks.getText().toString().trim());
                if(qty>0)
                {
                    qty--;
                }

                tvSocks.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnSocksPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvSocks.getText().toString().trim());
                if(qty>=0)
                {
                    qty++;
                }

                tvSocks.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnUnderwearMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvUnderwear.getText().toString().trim());
                if(qty>0)
                {
                    qty--;
                }

                tvUnderwear.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnUnderwearPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(tvUnderwear.getText().toString().trim());
                if(qty>=0)
                {
                    qty++;
                }

                tvUnderwear.setText(String.valueOf(qty));

                amount = (Integer.parseInt(tvTshirt.getText().toString().trim())*8) + (Integer.parseInt(tvShirt.getText().toString().trim())*10) + (Integer.parseInt(tvPant.getText().toString().trim())*12) + (Integer.parseInt(tvHanky.getText().toString().trim())*5) + (Integer.parseInt(tvBlanket.getText().toString().trim())*30) +
                        (Integer.parseInt(tvTowel.getText().toString().trim())*15) + (Integer.parseInt(tvSocks.getText().toString().trim())*8) + (Integer.parseInt(tvUnderwear.getText().toString().trim())*10);

                tvFinalAmount.setText(String.valueOf(amount));
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to Generate the Bill ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               // finish();

                                reff = FirebaseDatabase.getInstance().getReference().child("bill");
                                bill.setLaundryVendorUsername(laundryUsername);
                                bill.setStudentUsername(etStudentUsername.getText().toString().trim());

                                bill.setBlanketQuantity(Integer.parseInt(tvBlanket.getText().toString().trim()));
                                bill.setShirtQuantity(Integer.parseInt(tvShirt.getText().toString().trim()));
                                bill.setTowelQuantity(Integer.parseInt(tvTowel.getText().toString().trim()));
                                bill.setTshirtQuantity(Integer.parseInt(tvTshirt.getText().toString().trim()));
                                bill.setHankyQuantity(Integer.parseInt(tvHanky.getText().toString().trim()));
                                bill.setPantQuantity(Integer.parseInt(tvPant.getText().toString().trim()));
                                bill.setSocksQuantity(Integer.parseInt(tvSocks.getText().toString().trim()));
                                bill.setUnderwearQuantity(Integer.parseInt(tvUnderwear.getText().toString().trim()));
                                //Log.i("amount",tvFinalAmount.toString());
                                bill.setTotalAmount(Integer.parseInt(tvFinalAmount.getText().toString().trim()));
                                bill.setCurrentDate(currentDate);
                                bill.setDueDate(dueDate);
                                bill.setPaid(false);

                                reff.push().setValue(bill);

                                Toast.makeText(LaundryBillGenerator.this, "Bill Added Sucessfully", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                //Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        //Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("DRYO Bill Generation Alert");
                alert.show();
            }
        });

    }
}
