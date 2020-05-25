package com.example.easyengapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyengapp.Activity.RegisterActivity;
import com.example.easyengapp.Activity.WelcomeActiviry;
import com.example.easyengapp.R;
import com.example.easyengapp.moldel.User;
import com.example.easyengapp.storage.SharePrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * fragment hồ sơ cá nhân
 */

public class ProfileFagment extends Fragment {
    TextView textName, tvEmail;
    ImageView avatar;
    Button btn_logout;
    Context mContext;
    GoogleSignInClient signInClient;

    public ProfileFagment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fagment, container, false);
        textName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        avatar = view.findViewById(R.id.avatar);
        btn_logout = view.findViewById(R.id.btn_logout);
        mContext = getContext();
        User user = SharePrefManager.getInstance(mContext).getUser();
        textName.setText("Wellcome: " + user.getFullname());
        tvEmail.setText(user.getEmail()+" "+user.getPassword());
        Picasso.with(mContext).load(Uri.parse(user.getAvatar())).into(avatar);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInClient = RegisterActivity.signInClient;
             if(signInClient !=null)   signInClient.signOut();
                SharePrefManager.getInstance(mContext).clear();
                Intent intent = new Intent(mContext, WelcomeActiviry.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
