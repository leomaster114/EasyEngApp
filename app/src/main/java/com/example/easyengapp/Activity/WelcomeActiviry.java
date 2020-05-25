package com.example.easyengapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.easyengapp.R;
import com.example.easyengapp.moldel.User;
import com.example.easyengapp.storage.SharePrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    public void onStart() {
        super.onStart();
//        SharePrefManager.getInstance(this).clear();
        if(SharePrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            updateUI(currentUser);
        }
    }
    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account!=null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            String personPhoto = account.getPhotoUrl().toString();
//            Toast.makeText(RegisterActivity.this,personName+"\n "+personGivenName+"\n"+personFamilyName+"\n"+personEmail+"\n"+personPhoto,Toast.LENGTH_SHORT).show();
            // them vao thanh mot tai khoan moi
//            User new_user = new User(personEmail,personGivenName+"eng","12345678",personName);
//            new_user.setAvatar(personPhoto);
//            AddUser(new_user);
            //luu thong tin
//            SharePrefManager.getInstance(this).saveUser(new_user);
            Intent intent = new Intent(WelcomeActiviry.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
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
