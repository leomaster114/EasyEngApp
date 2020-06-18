package com.example.easyengapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.easyengapp.Model.Word;

import java.util.ArrayList;
import java.util.Locale;

public class DialogWord {
    private String tableName;
    private Context context;
    private Dialog dialog;
    private ViewPager viewPager;
    private ArrayList<Word> arrListWord;
    private TextToSpeech textToSpeech;
    private Word word;

    public DialogWord(Context context, String tableName, ArrayList<Word> arrListWord) {
        this.tableName = tableName;
        this.context = context;
        this.arrListWord = arrListWord;
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setSpeechRate((float) 1.0);
                }
            }
        });
        dialog = new FullScreenDialog(this.context).getIntanse();
        dialog.setContentView(R.layout.dialog_detail_word);
        viewPager = dialog.findViewById(R.id.view_pager);
//        viewPager.setOnPageChangeListener(new PagerChangeListener());
    }

    public void show(int position) {
        viewPager.setAdapter(new ViewPagerAdapterDetailWord());
        viewPager.setCurrentItem(position);
        word = arrListWord.get(position);
        dialog.show();
    }

    private class ViewPagerAdapterDetailWord extends PagerAdapter{
        private TextView tvWord,tvPhonetic,tvMeaning;
        private LinearLayout baseLayout;
        private Button  btnSpell;

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final Word word = arrListWord.get(position);
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.detail_word,container,false);
            baseLayout = viewGroup.findViewById(R.id.base_layout);
            tvWord = viewGroup.findViewById(R.id.txt_word);
            tvPhonetic = viewGroup.findViewById(R.id.txt_phonetic);
            tvMeaning = viewGroup.findViewById(R.id.txt_meaning);
            btnSpell = viewGroup.findViewById(R.id.btn_spell);

            btnSpell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textToSpeech.speak(word.getWord(), TextToSpeech.QUEUE_FLUSH, null);
                }
            });
             tvWord.setText(word.getWord());
             String phonetic = word.getPhonetic();
             if(phonetic.equals("")) tvPhonetic.setVisibility(View.GONE);
             tvPhonetic.setText(word.getPhonetic());
             tvMeaning.setText(Html.fromHtml(word.getValue()));
             container.addView(viewGroup);
             return viewGroup;
        }

        @Override
        public int getCount() {
            return arrListWord.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
