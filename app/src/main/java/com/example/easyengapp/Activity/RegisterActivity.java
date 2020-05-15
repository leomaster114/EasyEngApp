package com.example.easyengapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyengapp.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_email,edt_name,edt_pass;
    Button btn_register, btn_login_fb, btn_login_gg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_email = findViewById(R.id.edt_mail);
        edt_name = findViewById(R.id.edt_name);
        edt_pass = findViewById(R.id.edt_pass);
        btn_register = findViewById(R.id.btn_register);
        btn_login_fb = findViewById(R.id.btn_login_fb);
        btn_login_gg = findViewById(R.id.btn_login_gg);
        // click event
        btn_register.setOnClickListener(this);
        btn_login_fb.setOnClickListener(this);
        btn_login_gg.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:{// click Dang ky
                //validate thông tin
                //save in database
                Toast.makeText(this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                //chuyen sang trang dang nhap
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_login_fb:{
                // xử lý login bằng facebook
                Toast.makeText(this,"Login with Facebook account",Toast.LENGTH_LONG).show();
                //chuyển sang MainActivity
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_login_gg:{
                // xử lý login bằng google
                Toast.makeText(this,"Login with Google account",Toast.LENGTH_LONG).show();
                //chuyển sang MainActivity
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
