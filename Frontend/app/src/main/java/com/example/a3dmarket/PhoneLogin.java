package com.example.a3dmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.Callback;

public class PhoneLogin extends AppCompatActivity {

    EditText phone, country;
    Button verificar;

    FirebaseAuth mAuth;

    String countryCode  = "";
    String numbrer  = "";
    String telefono = "";
    String verificationCodeBySystem = "";

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        mAuth = FirebaseAuth.getInstance();

        phone = (EditText) findViewById(R.id.number);
        country = (EditText) findViewById(R.id.country);

        verificar = (Button) findViewById(R.id.enviar);

        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countryCode = country.getText().toString();
                System.out.println(countryCode);
                numbrer = phone.getText().toString();
                System.out.println(numbrer);
                telefono = countryCode + numbrer;

                System.out.println("-------------------------------------------------------------");

                System.out.println(telefono);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        telefono,
                        60,
                        TimeUnit.SECONDS,
                        PhoneLogin.this,
                        mCallbacks

                );
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                if (code != null) {
                    verifyCode(code);
                }else{
                    Toast.makeText(getApplicationContext(), "codigo null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), "No se pudo completar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCodeBySystem = s;
                Toast.makeText(getApplicationContext(), verificationCodeBySystem, Toast.LENGTH_SHORT).show();
            }
        };



    }

    private void verifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(PhoneLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(PhoneLogin.this, "Your Account has been created successfully!", Toast.LENGTH_SHORT).show();

                            //Perform Your required action here to either let the user sign In or do something required
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Toast.makeText(PhoneLogin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}