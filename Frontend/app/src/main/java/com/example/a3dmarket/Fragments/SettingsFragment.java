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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.example.a3dmarket.R;
import com.example.a3dmarket.SharedPref;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPref = new SharedPref(getContext());

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

        return root;
    }

    public void restartApp(){
        Toast.makeText(getContext(), "Reiniciando aplicación por favor espere", Toast.LENGTH_SHORT).show();

        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 1000, getActivity().getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pendingIntent);

        getActivity().finish();
    }
}