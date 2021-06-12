package com.example.a3dmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.a3dmarket.Fragments.AdriFragment;
import com.example.a3dmarket.Fragments.HomeFragment;
import com.example.a3dmarket.Fragments.ProfileFragment;
import com.example.a3dmarket.Fragments.SamuFragment;
import com.example.a3dmarket.Fragments.SearchFragment;
import com.example.a3dmarket.Fragments.SettingsFragment;
import com.example.a3dmarket.Fragments.UploadFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Credits extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        sharedPref = new SharedPref(this);

        chipNavigationBar = (ChipNavigationBar)findViewById(R.id.chip_bottom_nav_2);

        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.AppThemeDark);
            chipNavigationBar.setBackground(getDrawable(R.drawable.menu_bg_dark));
            chipNavigationBar.setMenuResource(R.menu.profile_menu_dark);
            chipNavigationBar.setItemSelected(R.id.adrian, true);


        }else{
            setTheme(R.style.AppThemeDay);
            chipNavigationBar.setBackground(getDrawable(R.drawable.menu_bg));
            chipNavigationBar.setMenuResource(R.menu.profile_menu);
            chipNavigationBar.setItemSelected(R.id.adrian, true);
        }

        chipNavigationBar.setItemEnabled(R.id.adrian, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout2, new AdriFragment()).commit();

        if (chipNavigationBar.isExpanded()){
            chipNavigationBar.showBadge(R.id.adrian, 2);
        }

        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.adrian:
                        fragment = new AdriFragment();
                        break;
                    case R.id.samuel:
                        fragment = new SamuFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout2, fragment).commit();
            }
        });
    }
}