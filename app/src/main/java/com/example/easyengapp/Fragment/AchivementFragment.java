package com.example.easyengapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyengapp.R;

/**
 * A simple {@link Fragment} subclass.
 * fragment thành tích
 */
public class AchivementFragment extends Fragment {

    public AchivementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_achivement, container, false);
    }
}
