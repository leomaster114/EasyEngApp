package com.example.easyengapp.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easyengapp.Adapter.SubjectAdapter;
import com.example.easyengapp.Database.ImageForSubject;
import com.example.easyengapp.Database.MyDatabase;
import com.example.easyengapp.Model.Topic;
import com.example.easyengapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * fragment thành tích
 */
public class DictionaryFragment extends Fragment {
    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Topic> listSubject;
    ArrayList<Integer> image_subs;
    MyDatabase database;
    public DictionaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dictionary, container, false);
        context = getContext();
        context =getContext();
        recyclerView = view.findViewById(R.id.list_subject);
        database = new MyDatabase(context);
        listSubject = new ArrayList<>();
        image_subs = ImageForSubject.imageId();
        // get subject from topic table
        listSubject = database.getAllTopic();
        layoutManager =new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);//false: theo thứ tự
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new SubjectAdapter(context,listSubject,image_subs);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
