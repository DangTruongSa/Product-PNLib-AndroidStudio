package com.example.mob2041_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mob2041_v2.fragment.FragmentLoaisach;
import com.example.mob2041_v2.fragment.FragmentSach;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.drawerlayuot);
        toolbar=findViewById(R.id.toolbar1);
        navigationView=findViewById(R.id.nvgtv);

        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        //set fragment mac dinh
        getSupportFragmentManager()
                .beginTransaction()
                        .replace(R.id.lnlo1,new FragmentLoaisach())
                                .commit();
        getSupportActionBar().setTitle("LOẠI SÁCH");

        SharedPreferences sharedPreferences = getSharedPreferences("dataUser",MODE_PRIVATE);
        int role =sharedPreferences.getInt("role",-1);
        /*
        role:
        * 1-nguoi dung
        * 2-thu thu
        * 3-admin
        */
            Menu nav_Menu = navigationView.getMenu();
        switch (role){
            case 1:
                nav_Menu.findItem(R.id.mSach).setVisible(false);
                nav_Menu.findItem(R.id.mLoaisach).setVisible(false);
                nav_Menu.findItem(R.id.mLspm).setVisible(true);
                nav_Menu.findItem(R.id.mTaopm).setVisible(false);
                nav_Menu.findItem(R.id.mThongke).setVisible(false);
                break;
            case 2:
                nav_Menu.findItem(R.id.mSach).setVisible(false);
                nav_Menu.findItem(R.id.mLoaisach).setVisible(false);
                nav_Menu.findItem(R.id.mLspm).setVisible(false);
                nav_Menu.findItem(R.id.mTaopm).setVisible(true);
                nav_Menu.findItem(R.id.mThongke).setVisible(false);
                break;
            case 3:
                nav_Menu.findItem(R.id.mSach).setVisible(true);
                nav_Menu.findItem(R.id.mLoaisach).setVisible(true);
                nav_Menu.findItem(R.id.mLspm).setVisible(false);
                nav_Menu.findItem(R.id.mTaopm).setVisible(true);
                nav_Menu.findItem(R.id.mThongke).setVisible(true);
                break;
            default:
                break;
        }

        //xuly item navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment=null;

                if (item.getItemId()==R.id.mDangxuat){
                    startActivity(new Intent(MainActivity.this,WellcomeAc.class));
                    finish();
                }
                if (item.getItemId()==R.id.mSach){
                    fragment = new FragmentSach();
                }else if (item.getItemId() == R.id.mLoaisach){
                    fragment = new FragmentLoaisach();
                }else {
                    fragment = new FragmentLoaisach();
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.lnlo1,fragment)
                        .commit();

                getSupportActionBar().setTitle(item.getTitle());

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });



    }

    private void hideItem()
    {
        navigationView=findViewById(R.id.nvgtv);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}