package com.example.a3dmarket.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.a3dmarket.HomeNotUser;
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
    Button   mButtonLogin, mButtonLoginNotRegister;


    private String email    = "";
    private String password = "";

    FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root   = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        mEditTexEmail           = (EditText)root.findViewById(R.id.email_login);
        mEditTexPassword        = (EditText)root.findViewById(R.id.password_login);
        mButtonLogin            = (Button)root.findViewById(R.id.login);
        mButtonLoginNotRegister = (Button)root.findViewById(R.id.noUser);

        reset = (TextView) root.findViewById(R.id.reset);

        mAuth = FirebaseAuth.getInstance();

        cargarPreferencias();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email    = mEditTexEmail.getText().toString();
                password = mEditTexPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    guardarPreferencias();
                    loginUser();
                }else {
                    Toast.makeText(getContext(), "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ResetPassword.class));
            }
        });

        mButtonLoginNotRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeNotUser.class));
                getActivity().finish();
            }
        });



        return root;
    }

    private void guardarPreferencias(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("email", email);
        editor.putString("password", password);

        editor.commit();
    }

    private void cargarPreferencias(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("email", "");
        String contraseña = preferences.getString("password", "");

        mEditTexEmail.setText(user);
        mEditTexPassword.setText(contraseña);

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
