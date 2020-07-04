package com.example.easyengapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyengapp.Activity.MainActivity;
import com.example.easyengapp.Activity.RegisterActivity;
import com.example.easyengapp.Model.UserReminderResponse;
import com.example.easyengapp.R;
import com.example.easyengapp.Retrofit.RetrofitClient;
import com.example.easyengapp.Model.LoginResponse;
import com.example.easyengapp.Model.User;
import com.example.easyengapp.storage.SharePrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    Button btn_login;
    TextView tv_forgetPass, tv_dont_have_acc;
    EditText edt_username, edt_pass;
    Context context;
    private ProgressBar progressBar;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        context = getContext();
        btn_login = view.findViewById(R.id.btn_login);
        tv_forgetPass = view.findViewById(R.id.tv_forget_acc);
        tv_dont_have_acc = view.findViewById(R.id.tv_donthave_acc);
        edt_username = view.findViewById(R.id.edt_username);
        edt_pass = view.findViewById(R.id.edt_password);
        progressBar = view.findViewById(R.id.progressBar_login);
        btn_login.setOnClickListener(this);
        tv_forgetPass.setOnClickListener(this);
        tv_dont_have_acc.setOnClickListener(this);
        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if(SharePrefManager.getInstance(context).isLoggedIn()){
//            Intent intent = new Intent(context,MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login: {// click vào đăng nhập
                progressBar.setVisibility(View.VISIBLE);
                UserLogin();
                break;
            }
            case R.id.tv_forget_acc: {//click vào quên mật khẩu
                Toast.makeText(context, "Quên mật khẩu", Toast.LENGTH_LONG).show();
                //chuyển sang fragment quên mật khẩu
                ForgetAccountFragment forgetAccountFragment = new ForgetAccountFragment();
                FrameLayout frameLayout = getActivity().findViewById(R.id.replaceFragment1);
                frameLayout.setVisibility(View.GONE);
                frameLayout = getActivity().findViewById(R.id.replaceFragment2);
                frameLayout.setVisibility(View.VISIBLE);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.replaceFragment2, forgetAccountFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case R.id.tv_donthave_acc:{
                Intent intent = new Intent(context,RegisterActivity.class);
                startActivity(intent);
            }

        }
    }

    private void UserLogin() {
        final String username = edt_username.getText().toString().trim();
        final String pass = edt_pass.getText().toString().trim();
        if (username.isEmpty()) {
            edt_username.setError("Username is required!");
            edt_username.setTextColor(Color.RED);
            edt_username.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            edt_pass.setError("Password is required");
            edt_pass.setTextColor(Color.RED);
            edt_pass.requestFocus();
            return;
        }
        if (pass.length() < 6) {
            edt_pass.setError("Password should be atleast 6 character");
            edt_pass.setTextColor(Color.RED);
            edt_pass.requestFocus();
            return;
        }
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().LoginUser(username,pass);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                if(result.isStatus()){
                    Toast.makeText(context,"Đã đăng nhập thành công",Toast.LENGTH_LONG).show();
                    User user = result.getUser();
                    SharePrefManager.getInstance(context)
                            .saveUser(new User(user.getId(),user.getFullname(),username,pass,user.getEmail(),user.getAvatar()));//result.getUser()
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    progressBar.setVisibility(View.GONE);
                    edt_username.setError("Incorrect username or password!");
                    edt_username.setTextColor(Color.RED);
                    edt_pass.setError("Incorrect username or password!");
                    edt_pass.setTextColor(Color.RED);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                edt_username.setError("Incorrect username or password!");
                edt_username.setTextColor(Color.RED);
                edt_pass.setError("Incorrect username or password!");
                edt_pass.setTextColor(Color.RED);
            }
        });
    }

}

