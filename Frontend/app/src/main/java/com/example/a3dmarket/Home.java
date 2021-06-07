package com.example.a3dmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.a3dmarket.Fragments.HomeFragment;
import com.example.a3dmarket.Fragments.ProfileFragment;
import com.example.a3dmarket.Fragments.SearchFragment;
import com.example.a3dmarket.Fragments.SettingsFragment;
import com.example.a3dmarket.Fragments.UploadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Home extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        chipNavigationBar = (ChipNavigationBar)findViewById(R.id.chip_bottom_nav);

        sharedPref = new SharedPref(this);


        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.AppThemeDark);
            chipNavigationBar.setBackground(getDrawable(R.drawable.menu_bg_dark));
            chipNavigationBar.setMenuResource(R.menu.bottom_nav_menu_dark);
            chipNavigationBar.setItemSelected(R.id.home, true);


        }else{
            setTheme(R.style.AppThemeDay);
            chipNavigationBar.setBackground(getDrawable(R.drawable.menu_bg));
            chipNavigationBar.setMenuResource(R.menu.bottom_nav_menu);
            chipNavigationBar.setItemSelected(R.id.home, true);
        }


        chipNavigationBar.setItemEnabled(R.id.nav_home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
        if (chipNavigationBar.isExpanded()){
            chipNavigationBar.showBadge(R.id.home, 2);
        }
        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.upload:
                        fragment = new UploadFragment();
                        break;
                    case R.id.settings:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
            }
        });
    }
}