package com.example.easyengapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyengapp.Fragment.ForgetAccountFragment;
import com.example.easyengapp.Fragment.LoginFragment;
import com.example.easyengapp.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // bỏ tiêu đề và action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_login);
        loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_login,loginFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_loginactivity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.App_info:
                Toast.makeText(this, "Thông tin ứng dụng", Toast.LENGTH_LONG).show();
                break;
            case R.id.App_setup:
                Toast.makeText(this, "Cài đặt ứng dụng", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FrameLayout frameLayout = findViewById(R.id.replaceFragment1);
        frameLayout.setVisibility(View.VISIBLE);
        frameLayout = findViewById(R.id.replaceFragment2);
        frameLayout.setVisibility(View.GONE);
    }
}
