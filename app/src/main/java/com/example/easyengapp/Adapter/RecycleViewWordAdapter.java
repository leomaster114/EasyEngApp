package com.example.easyengapp.Adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyengapp.DialogWord;
import com.example.easyengapp.Model.Word;
import com.example.easyengapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class RecycleViewWordAdapter extends RecyclerView.Adapter<RecycleViewWordAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Word> arrWord;
    private TextToSpeech textToSpeech;
    private String tableName;

    public RecycleViewWordAdapter(Context context, ArrayList<Word> arrWord, String tableName) {
        this.context = context;
        this.arrWord = arrWord;
        this.textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setSpeechRate((float) 0.8);
                }
            }
        });
        this.tableName = tableName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_word,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Word word = arrWord.get(position);
        holder.txtWord.setText(word.getWord());
        holder.txtWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogWord(context,tableName,arrWord).show(position);
            }
        });
        holder.ibtnSpell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(word.getWord(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrWord.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtWord;
        private ImageButton ibtnSpell;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWord = itemView.findViewById(R.id.txt_word);
            ibtnSpell = itemView.findViewById(R.id.action_spell);
        }
    }
}
