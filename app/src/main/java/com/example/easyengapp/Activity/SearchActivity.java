package com.example.easyengapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyengapp.Adapter.OnLoadMoreListener;
import com.example.easyengapp.Adapter.RecycleViewSuggestAdapter;
import com.example.easyengapp.Adapter.TopicAdapter;
import com.example.easyengapp.Database.MyDatabase;
import com.example.easyengapp.Database.TranferDatabase;
import com.example.easyengapp.Fragment.DictionaryFragment;
import com.example.easyengapp.Model.KeyAndValue;
import com.example.easyengapp.Model.Word;
import com.example.easyengapp.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText edtSearch;
    RecyclerView recycleViewSuggest;
    TextView txtHead;
    private int MaxLoadSize = 10;
    ImageButton ibtn_cancel;
    ImageView img_search;
    TranferDatabase tranferDatabase;
    MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edtSearch = findViewById(R.id.edt_search);
        txtHead = findViewById(R.id.txt_head);
        recycleViewSuggest = findViewById(R.id.recycle_view_suggest);
        ibtn_cancel = findViewById(R.id.action_cancel);
        img_search = findViewById(R.id.img_search);
        tranferDatabase = new TranferDatabase(this, false);
        database = new MyDatabase(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);//false: theo thứ tự
        recycleViewSuggest.setLayoutManager(layoutManager);
        recycleViewSuggest.setHasFixedSize(true);
        ibtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCancle();
            }
        });
        initSearch();
    }

    public void initSearch() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = s.toString();
                if (key.length() > 1) {
                    ArrayList<Word> arrKV = database.getWordByKey(key);
                    txtHead.setText("Tìm thấy " + String.valueOf(arrKV.size()));
                    recycleViewSuggest.setAdapter(new RecycleViewSuggestAdapter(SearchActivity.this, arrKV, recycleViewSuggest));
                } else {
                    txtHead.setText("Tìm thấy" + " nhiều lắm");
                    recycleViewSuggest.setAdapter(new RecycleViewSuggestAdapter(SearchActivity.this, null, recycleViewSuggest));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onClickCancle() {
        if (edtSearch.getText().toString().equals("")) {
            Intent intent = new Intent(SearchActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else {
            edtSearch.setText("");
            recycleViewSuggest.setAdapter(new RecycleViewSuggestAdapter(this, null, recycleViewSuggest));
            txtHead.setText("Tìm thấy");
        }
    }
    @Override
    public void onBackPressed()
    {

        Intent intent = new Intent(SearchActivity.this,MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        startActivity(intent);
    }
}