package com.example.intel.dryoio;


import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.app.KeyguardManager;
import java.security.KeyStore;
import java.util.ArrayList;

import javax.crypto.Cipher;

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
    TextView tvRegisterNow,textView,tvUpdate;
    DatabaseReference reff;
    Student student;
    SharedPreferences sharedPreferences;
    ProgressBar progress;

    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        sharedPreferences = getSharedPreferences("Pref",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterNow = findViewById(R.id.tvRegisterNow);
        textView = findViewById(R.id.errorText);
        tvUpdate = findViewById(R.id.tvUpdate);

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        progress = findViewById(R.id.progress);

        progress.setVisibility(View.INVISIBLE);

        student = new Student();

        if(!fingerprintManager.isHardwareDetected()){
            /**
             * An error message will be displayed if the device does not contain the fingerprint hardware.
             * However if you plan to implement a default authentication method,
             * you can redirect the user to a default authentication activity from here.
             * Example:
             * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
             * startActivity(intent);
             */
            textView.setText("Your Device does not have a Fingerprint Sensor");
        }else {
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                textView.setText("Fingerprint authentication permission not enabled");
            }else{
                // Check whether at least one fingerprint is registered
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    textView.setText("Register at least one fingerprint in Settings");
                }else{
                    // Checks whether lock screen security is enabled or not
                    if (!keyguardManager.isKeyguardSecure()) {
                        textView.setText("Lock screen security not enabled in Settings");
                    }else{
                        generateKey();


                        if (cipherInit()) {
                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                            FingerprintHandlerStudent helper = new FingerprintHandlerStudent(this);
                            helper.startAuth(fingerprintManager, cryptoObject);
                        }
                    }
                }
            }
        }

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
                        Toast.makeText(StudentLogin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.INVISIBLE);
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
                                            progress.setVisibility(View.INVISIBLE);
                                            Intent intent = new Intent(StudentLogin.this, Student_pending_bills.class);
                                            //intent.putExtra("studentUsername",username);
                                            editor.putString("username",username);
                                            editor.commit();
                                            startActivity(intent);
                                            finish();
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

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean is_connected = isNetworkConnected();

                if(is_connected==false)
                {
                    Toast.makeText(StudentLogin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(etUsername.getText().toString().trim().equals(""))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(StudentLogin.this);
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Dryo Alert");
                        alert.setMessage("Please enter your username");
                        alert.show();
                    }

                    else
                    {
                        Intent intent = new Intent(StudentLogin.this, StudentUpdate.class);
                        intent.putExtra("username",etUsername.getText().toString().trim());
                        startActivity(intent);
                        finish();
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
                    finish();
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

        Intent intent = new Intent(StudentLogin.this, MainActivity.class);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
}
