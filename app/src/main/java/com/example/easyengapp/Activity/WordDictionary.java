package com.example.easyengapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.easyengapp.Adapter.RecycleViewWordAdapter;
import com.example.easyengapp.Database.MyDatabase;
import com.example.easyengapp.Model.Word;
import com.example.easyengapp.R;

import java.util.ArrayList;
import java.util.Collections;

public class WordDictionary extends AppCompatActivity {
    MyDatabase database;
    private RecyclerView recyclerViewListWord;
    private ArrayList<Word> arrWords;
    int idTopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_dictionary);
        recyclerViewListWord = findViewById(R.id.list_word);
        recyclerViewListWord.setLayoutManager(new LinearLayoutManager(this));
        database = new MyDatabase(this);
        Intent intent = getIntent();
        idTopic = intent.getIntExtra("IdTopic",-1);
        if(idTopic!=-1){
            showToeic();
        }
    }
    public void showToeic() {
        arrWords = database.getWordTopicByid(idTopic);
//        arrWords = database.getWord("word_table");
        Collections.reverse(arrWords);
        getSupportActionBar().setSubtitle(String.valueOf(arrWords.size()));
        recyclerViewListWord.setAdapter(new RecycleViewWordAdapter(this, arrWords,"word_table"));
    }
}
