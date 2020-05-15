package com.example.easyengapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.easyengapp.R;

public class WelcomeActiviry extends AppCompatActivity implements View.OnClickListener{
    Button btn_have_acc, btn_no_acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // bỏ tiêu đề và action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome);

        btn_have_acc = findViewById(R.id.btn_have_acc);
        btn_no_acc  = findViewById(R.id.btn_no_acc);

        btn_have_acc.setOnClickListener(this);
        btn_no_acc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_have_acc:{
                Intent intent = new Intent(WelcomeActiviry.this,LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_no_acc:{
                Intent intent = new Intent(WelcomeActiviry.this,RegisterActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
