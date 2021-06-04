package com.example.a3dmarket;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    SharedPreferences mySharedPrefer;

    public SharedPref(Context context){
        mySharedPrefer = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor = mySharedPrefer.edit();
        editor.putBoolean("NightMode", state);
        editor.commit();
    }

    public Boolean loadNightModeState(){
        Boolean state = mySharedPrefer.getBoolean("NightMode", false);
        return state;
    }
}
