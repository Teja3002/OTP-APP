package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        final EditText inputmobile=findViewById(R.id.inputmobile);
        Button buttonGetOTP=findViewById(R.id.GetOTP);


        final ProgressBar progressBar=findViewById(R.id.ProgessbarSend);


        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputmobile.getText().toString().trim().isEmpty()){
                    if(inputmobile.getText().toString().trim().length()==10)
                        Toast.makeText(SendOTP.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }


                progressBar.setVisibility(View.VISIBLE);
                buttonGetOTP.setVisibility(View.INVISIBLE);


                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+inputmobile.getText().toString(),
                        60, TimeUnit.SECONDS,
                        SendOTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                        {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                buttonGetOTP.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                buttonGetOTP.setVisibility(View.VISIBLE);
                                Toast.makeText(SendOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                buttonGetOTP.setVisibility(View.VISIBLE);


                                Intent intent = new Intent(getApplicationContext(), Verification.class);
                                intent.putExtra("mobile ",inputmobile.getText().toString());
                                intent.putExtra("backendotp",backendotp);
                                startActivity(intent);

                            }
                        });

            }
        });

    }
}