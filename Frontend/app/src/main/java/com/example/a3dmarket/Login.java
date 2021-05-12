package com.example.a3dmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.a3dmarket.Adapters.LoginAdapter;
import com.google.android.material.tabs.TabLayout;

public class Login extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        linearLayout = (LinearLayout) findViewById(R.id.principal_linear);

        tabLayout.setTabTextColors(Color.parseColor("#D3D3D3"),Color.parseColor("#F3B200"));
        tabLayout.addTab(tabLayout.newTab().setText("LOGIN"));
        tabLayout.addTab(tabLayout.newTab().setText("REGISTER"));

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Animaciones
        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(0);
        tabLayout.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(100).start();

        viewPager.setTranslationY(300);
        viewPager.setAlpha(0);
        viewPager.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(500).start();

        linearLayout.setTranslationY(300);
        linearLayout.setAlpha(0);
        linearLayout.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(500).start();
    }
}