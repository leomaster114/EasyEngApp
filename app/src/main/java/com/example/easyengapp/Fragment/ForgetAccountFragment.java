package com.example.easyengapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyengapp.Activity.MainActivity;
import com.example.easyengapp.Model.ResetPasswordResponse;
import com.example.easyengapp.Model.UpdateUserResponse;
import com.example.easyengapp.R;
import com.example.easyengapp.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetAccountFragment extends Fragment implements View.OnClickListener{
    EditText edit_email;
    TextView tv_sendAgain;
    Button btn_receive_new_pass;
    Context context;
    public ForgetAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_account, container, false);
        context = getContext();
        edit_email = view.findViewById(R.id.edt_email);
        tv_sendAgain = view.findViewById(R.id.tv_send_again);
        btn_receive_new_pass = view.findViewById(R.id.btn_receive_new_pass);
        tv_sendAgain.setOnClickListener(this);
        btn_receive_new_pass.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_receive_new_pass:{// chuyển về trang login để đăng nhập với mật khẩu mới
                String email = edit_email.getText().toString().trim();
                if (email.isEmpty()) {
                    edit_email.setError("email is required!");
                    edit_email.setTextColor(Color.RED);
                    edit_email.requestFocus();
                    return;
                }
                Call<ResetPasswordResponse> call = RetrofitClient.getInstance().getApi().resetPassword(email);
                call.enqueue(new Callback<ResetPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                        ResetPasswordResponse rs = response.body();
                        if(rs.getStatus()==200) {
                            Toast.makeText(context,response.body().getData(),Toast.LENGTH_LONG).show();
                            LoginFragment loginFragment = new LoginFragment();
                            FrameLayout frameLayout = getActivity().findViewById(R.id.replaceFragment2);
                            frameLayout.setVisibility(View.GONE);
                            frameLayout = getActivity().findViewById(R.id.replaceFragment1);
                            frameLayout.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(context,response.body().getMsg(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                        Toast.makeText(context,"Network error !",Toast.LENGTH_LONG).show();
                    }
                });

            }
        }
    }

}
