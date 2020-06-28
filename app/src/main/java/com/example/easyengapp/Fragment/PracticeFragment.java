package com.example.easyengapp.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easyengapp.R;

/**
 * A simple {@link Fragment} subclass.
 * fragment Luyện tập nơi chứa những bài tập
 */
public class PracticeFragment extends Fragment {
    Context mContext;
    private String TAG = getClass().getSimpleName();

    public PracticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_practice, container, false);
        mContext = getContext();
        Toast.makeText(mContext,"Practice Fragment",Toast.LENGTH_SHORT).show();
        return view;
    }
    public void show_practice(View view){

    }
}
