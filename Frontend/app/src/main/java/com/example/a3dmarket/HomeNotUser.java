package com.example.a3dmarket;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.a3dmarket.Fragments.HomeFragment;
import com.example.a3dmarket.Fragments.HomeFragmentNotUser;
import com.example.a3dmarket.Fragments.ProfileFragment;
import com.example.a3dmarket.Fragments.SearchFragment;
import com.example.a3dmarket.Fragments.SearchFragmentNotUser;
import com.example.a3dmarket.Fragments.SettingsFragment;
import com.example.a3dmarket.Fragments.UploadFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeNotUser extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_not_user);
        chipNavigationBar = (ChipNavigationBar)findViewById(R.id.chip_bottom_nav_1);


        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_1, new HomeFragmentNotUser()).commit();
        if (chipNavigationBar.isExpanded()){
            chipNavigationBar.showBadge(R.id.home_1, 2);
        }

        chipNavigationBar.setBackground(getDrawable(R.drawable.menu_bg));
        chipNavigationBar.setMenuResource(R.menu.bottom_nav_menu_2);
        chipNavigationBar.setItemSelected(R.id.home_1, true);

        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.home_1:
                        fragment = new HomeFragmentNotUser();
                        break;
                    case R.id.search_1:
                        fragment = new SearchFragmentNotUser();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_1, fragment).commit();
            }
        });
    }
}