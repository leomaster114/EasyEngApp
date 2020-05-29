package com.example.easyengapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import retrofit2.Call;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyengapp.R;
import com.example.easyengapp.Retrofit.RetrofitClient;
import com.example.easyengapp.moldel.RegisterResponse;
import com.example.easyengapp.moldel.User;
import com.example.easyengapp.storage.SharePrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_email,edt_name,edt_pass,edt_username;
    Button btn_register, btn_login_fb, btn_login_gg;
    TextView tv_have_acc;
    private ProgressBar progressBar;
    public static GoogleSignInClient signInClient;
    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_email = findViewById(R.id.edt_mail);
        edt_name = findViewById(R.id.edt_name);
        edt_pass = findViewById(R.id.edt_pass);
        edt_username = findViewById(R.id.edt_username);
        btn_register = findViewById(R.id.btn_register);
        btn_login_fb = findViewById(R.id.btn_login_fb);
        btn_login_gg = findViewById(R.id.btn_login_gg);
        tv_have_acc = findViewById(R.id.tv_have_acc);
        progressBar = findViewById(R.id.progressBar);
        // click event
        btn_register.setOnClickListener(this);
        btn_login_fb.setOnClickListener(this);
        btn_login_gg.setOnClickListener(this);
        tv_have_acc.setOnClickListener(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(this, gso);

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
                //save in database
                progressBar.setVisibility(View.VISIBLE);
                UserRegister();
                break;
            }
            case R.id.btn_login_fb:{
                // xử lý login bằng facebook
                Toast.makeText(this,"Login with Facebook account",Toast.LENGTH_LONG).show();

               LoginFaceBook();
                break;
            }
            case R.id.btn_login_gg:{
                // xử lý login bằng google
                progressBar.setVisibility(View.VISIBLE);
               LoginGoogle();
                break;
            }
            case R.id.tv_have_acc:{
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    private void LoginGoogle() {
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount acc = task.getResult(ApiException.class);
//            Toast.makeText(this,"Login with Google account Successfully",Toast.LENGTH_LONG).show();
            FirebaseGoogleAuth(acc);
        } catch (ApiException e) {
            e.printStackTrace();
//            Toast.makeText(this,"Login with Google account Unsuccessfully",Toast.LENGTH_LONG).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Successfully",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();

                    updateUI(user);

                }else{
                    Toast.makeText(RegisterActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            User new_user = new User(personEmail,personGivenName+"eng","12345678",personName);
            new_user.setAvatar(personPhoto);
//            AddUser(new_user);
            //luu thong tin
            SharePrefManager.getInstance(this).saveUser(new_user);
            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void LoginFaceBook() {
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void UserRegister() {
        String fullname = edt_name.getText().toString().trim();
        String username = edt_username.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String pass = edt_pass.getText().toString().trim();
        if(fullname.isEmpty()){
            edt_name.setError("Fullname is required!");
            edt_name.setTextColor(Color.RED);
            edt_name.requestFocus();
            return;
        }
        if(username.isEmpty()){
            edt_name.setError("Username is required!");
            edt_name.setTextColor(Color.RED);
            edt_name.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edt_email.setError("Enter valid email");
            edt_email.setTextColor(Color.RED);
            edt_email.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            edt_pass.setError("Password is required");
            edt_email.setTextColor(Color.RED);
            edt_email.requestFocus();
            return;
        }
        if(pass.length()<8){
            edt_pass.setError("Password should be atleast 6 character");
            edt_email.setTextColor(Color.RED);
            edt_email.requestFocus();
            return;
        }
        // user registration using the api call
        User user = new User(email,username,pass,fullname);
        AddUser(user);

    }
    // them mot tai khoan vao database
    public void AddUser(User user){
        Call<RegisterResponse> call = RetrofitClient.getInstance()
                .getApi().createUser(user);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse result = response.body();
                if(result.isStatus()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this,result.getMsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
