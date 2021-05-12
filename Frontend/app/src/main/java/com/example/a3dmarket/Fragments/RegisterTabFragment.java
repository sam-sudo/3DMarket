package com.example.a3dmarket.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a3dmarket.Home;
import com.example.a3dmarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterTabFragment extends Fragment {

    EditText mEditTextName;
    EditText mEditTextRepeatPassword;
    EditText mEditTexEmail;
    EditText mEditTexPassword;
    Button mButtonRegister;

    private String name           = "";
    private String repeatPassword = "";
    private String email          = "";
    private String password       = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);

        mEditTextName           = (EditText)root.findViewById(R.id.name_register);
        mEditTextRepeatPassword = (EditText)root.findViewById(R.id.repeat_password);
        mEditTexEmail           = (EditText)root.findViewById(R.id.email_register);
        mEditTexPassword        = (EditText)root.findViewById(R.id.password_register);
        mButtonRegister         = (Button)root.findViewById(R.id.register);

        mAuth     = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name           = mEditTextName.getText().toString();
                email          = mEditTexEmail.getText().toString();
                password       = mEditTexPassword.getText().toString();
                repeatPassword = mEditTextRepeatPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !repeatPassword.isEmpty()){
                    if (password.length() >= 6){
                        registerUser();
                    }else{
                        Toast.makeText(getContext(), "Contraseña demasiado corta", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getContext(), "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);


                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(getActivity(), Home.class));
                                getActivity().finish();
                            }else{
                                Toast.makeText(getContext(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
