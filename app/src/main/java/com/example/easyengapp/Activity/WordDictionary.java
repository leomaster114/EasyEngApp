package com.example.easyengapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.easyengapp.Adapter.RecycleViewWordAdapter;
import com.example.easyengapp.Database.MyDatabase;
import com.example.easyengapp.Model.Topic;
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
//        Log.d("TAG", "onCreate: id topic = "+idTopic);
//        Topic topic = database.getTopicById(idTopic);
//        Log.d("TAG", "onCreate: topic = "+topic.getName_topic());
        arrWords = new ArrayList<>();
        if(idTopic!=-1){
            showToeic();
        }
    }
    public void showToeic() {
        arrWords.clear();
        arrWords = database.getWordTopicByid(idTopic);
        Log.d("WordDictionary", "showToeic: size of arrWords"+arrWords.size());
        Topic topic  = database.getTopicById(idTopic);
        Collections.reverse(arrWords);
        getSupportActionBar().setSubtitle(topic.getName_topic());
        recyclerViewListWord.setAdapter(new RecycleViewWordAdapter(this, arrWords,"word_table"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_search:
                Intent intent = new Intent(WordDictionary.this,SearchActivity.class);
                startActivity(intent);
            case R.id.App_info:
                Toast.makeText(WordDictionary.this, "Thông tin ứng dụng", Toast.LENGTH_LONG).show();
                break;
            case R.id.App_setup:
                Toast.makeText(WordDictionary.this, "Cài đặt ứng dụng", Toast.LENGTH_LONG).show();
                break;
            case R.id.App_feedback:
                Toast.makeText(WordDictionary.this, "Báo cáo ứng dụng", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
