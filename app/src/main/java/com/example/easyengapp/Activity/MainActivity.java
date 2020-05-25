package com.example.easyengapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.easyengapp.Adapter.ViewPagerAdapter;
import com.example.easyengapp.Fragment.DictionaryFragment;
import com.example.easyengapp.Fragment.PracticeFragment;
import com.example.easyengapp.Fragment.ProfileFagment;
import com.example.easyengapp.R;
import com.example.easyengapp.storage.SharePrefManager;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    public PracticeFragment practiceFragment;
    public DictionaryFragment dictionaryFragment;
    public ProfileFagment profileFagment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }
/*
    @Override
    public void onStart() {
        super.onStart();
        if(!SharePrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this,WelcomeActiviry.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    */
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.practice);
        tabLayout.getTabAt(1).setIcon(R.drawable.achive);
        tabLayout.getTabAt(2).setIcon(R.drawable.profile);
    }

    private void setupViewPager(ViewPager viewPager) {
        practiceFragment = new PracticeFragment();
        dictionaryFragment = new DictionaryFragment();
        profileFagment = new ProfileFagment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(practiceFragment,"");
        adapter.addFragment(dictionaryFragment,"");
        adapter.addFragment(profileFagment,"");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.App_info:
                Toast.makeText(MainActivity.this,"Thông tin ứng dụng",Toast.LENGTH_LONG).show();
                break;
            case R.id.App_setup:
                Toast.makeText(MainActivity.this,"Cài đặt ứng dụng",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
