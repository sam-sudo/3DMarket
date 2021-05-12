package com.example.a3dmarket.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a3dmarket.Home;
import com.example.a3dmarket.PhoneLogin;
import com.example.a3dmarket.R;
import com.example.a3dmarket.ResetPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    EditText mEditTexEmail, mEditTexPassword;
    TextView reset;
    Button   mButtonLogin;

    FloatingActionButton fabGoogle;
    FloatingActionButton fabFacebook;
    FloatingActionButton fabPhone;


    private String email    = "";
    private String password = "";

    FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root   = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        mEditTexEmail        = (EditText)root.findViewById(R.id.email_login);
        mEditTexPassword     = (EditText)root.findViewById(R.id.password_login);
        mButtonLogin         = (Button)root.findViewById(R.id.login);

        reset = (TextView) root.findViewById(R.id.reset);

        fabGoogle   = (FloatingActionButton)root.findViewById(R.id.fab_google);
        fabFacebook = (FloatingActionButton)root.findViewById(R.id.fab_facebook);
        fabPhone    = (FloatingActionButton)root.findViewById(R.id.fab_number);

        mAuth = FirebaseAuth.getInstance();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email    = mEditTexEmail.getText().toString();
                password = mEditTexPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    loginUser();
                }else {
                    Toast.makeText(getContext(), "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fabGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fabFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fabPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PhoneLogin.class));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ResetPassword.class));
            }
        });



        return root;
    }

    private void loginUser(){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(getActivity(), Home.class));
                    getActivity().finish();
                }else{
                    Toast.makeText(getContext(), "No se puede iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
