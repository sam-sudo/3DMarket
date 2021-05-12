package com.example.a3dmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chipNavigationBar = (ChipNavigationBar)findViewById(R.id.chip_bottom_nav);
        chipNavigationBar.setItemEnabled(R.id.nav_home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
        chipNavigationBar.showBadge(R.id.home);
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