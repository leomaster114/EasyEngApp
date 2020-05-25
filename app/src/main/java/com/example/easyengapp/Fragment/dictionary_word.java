package com.example.easyengapp.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easyengapp.Adapter.SubjectAdapter;
import com.example.easyengapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class dictionary_word extends Fragment{

    public dictionary_word() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dictionary_word, container, false);
        return view;
    }


}
