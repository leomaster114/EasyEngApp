package com.example.easyengapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyengapp.Activity.MainActivity;
import com.example.easyengapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    Button btn_login;
    TextView tv_forgetPass;
    EditText edt_username, edt_pass;
    Context context;
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
        edt_username = view.findViewById(R.id.edt_username);
        edt_pass = view.findViewById(R.id.edt_password);

        btn_login.setOnClickListener(this);
        tv_forgetPass.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login: {// click vào đăng nhập
                String email = edt_username.getText().toString();
                String pass = edt_pass.getText().toString();
                if(email.equals("")||pass.equals("")) Toast.makeText(context,"Nhập đầy đủ email và password",Toast.LENGTH_LONG).show();
                else{
                    // validate email, password
                    Toast.makeText(context, "email: " + edt_username.getText() + "\npassword: " + edt_pass.getText(), Toast.LENGTH_LONG).show();
                    if(email.equals("cloneforfun.ptit@gmail.com")){
                        if(pass.equals("12345")){
                            // dang nhap thanh cong
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(context, "Mật khẩu sai", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(context, "email sai", Toast.LENGTH_LONG).show();
                    }
                }


                break;
            }
            case R.id.tv_forget_acc: {//click vào quên mật khẩu
                Toast.makeText(context,"Quên mật khẩu",Toast.LENGTH_LONG).show();
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
            }
        }
    }
}
