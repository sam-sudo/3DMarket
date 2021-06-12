package com.example.a3dmarket.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.example.a3dmarket.Credits;
import com.example.a3dmarket.Home;
import com.example.a3dmarket.Login;
import com.example.a3dmarket.R;
import com.example.a3dmarket.ResetPassword;
import com.example.a3dmarket.SharedPref;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private CheckBox mySwitch;
    SharedPref sharedPref;
    Button cerrarSesion, resetPass, creditos;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPref = new SharedPref(getContext());
        cerrarSesion = (Button)root.findViewById(R.id.singout);
        resetPass = (Button)root.findViewById(R.id.reseet);
        creditos = (Button)root.findViewById(R.id.credits);

        mAuth = FirebaseAuth.getInstance();

        if (sharedPref.loadNightModeState() == true){
            getActivity().setTheme(R.style.AppThemeDark);
        }else{
            getActivity().setTheme(R.style.AppThemeDay);
        }

        mySwitch = (CheckBox) root.findViewById(R.id.mySwitch);
        if (sharedPref.loadNightModeState() == true){
            mySwitch.setChecked(true);
        }

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sharedPref.setNightModeState(true);
                    restartApp();
                }else{
                    sharedPref.setNightModeState(false);
                    restartApp();
                }
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Toas", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), ResetPassword.class));
            }
        });

        creditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Credits.class));
            }
        });

        return root;
    }

    public void restartApp(){
        Toast.makeText(getContext(), "Reiniciando aplicación por favor espere", Toast.LENGTH_SHORT).show();

        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
                1000,
                getActivity().getIntent(),
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC,
                System.currentTimeMillis() + 2000, pendingIntent);

        getActivity().finish();
    }
}