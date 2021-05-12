package com.example.a3dmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    EditText email;
    Button   enviar;

    String strEmail = "";

    private FirebaseAuth   mAuth;
    private ProgressDialog mDialog;

    Dialog myDialog;
    ImageView showDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email      = (EditText) findViewById(R.id.resetPassEmail);
        enviar     = (Button)   findViewById(R.id.resetPassword);
        showDialog = (ImageView) findViewById(R.id.show_information);

        mDialog  = new ProgressDialog(this);
        myDialog = new Dialog(this);

        mAuth = FirebaseAuth.getInstance();
        
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = email.getText().toString();
                if (!strEmail.isEmpty()){
                    mDialog.setMessage("Por favor espere...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }else{
                    Toast.makeText(getApplicationContext(), "Por favor ingresa el email", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    public void showPopPup(View v){
        TextView txtClose;
        myDialog.setContentView(R.layout.custom_pop_up);
        txtClose = (TextView) myDialog.findViewById(R.id.button_cerrar);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    private void resetPassword() {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(strEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Revisa tu correo para reestablecer la contraseña", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "No se puede reestablecer la contraseña", Toast.LENGTH_LONG).show();
                }
                mDialog.dismiss();
            }
        });
    }
}