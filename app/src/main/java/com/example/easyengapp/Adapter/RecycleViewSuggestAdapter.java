package com.example.easyengapp.Adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyengapp.Database.MyDatabase;
import com.example.easyengapp.Database.Reformat_word;
import com.example.easyengapp.Model.KeyAndValue;
import com.example.easyengapp.Model.Word;
import com.example.easyengapp.R;

import java.util.ArrayList;
import java.util.Locale;


public class RecycleViewSuggestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Word> arrKV = new ArrayList<>();
    private Context context;
    private SparseBooleanArray sparseBooleanArrayExpand;
    private TextToSpeech textToSpeech;
    private MyDatabase myDataBase;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener onLoadMoreListener;

    public RecycleViewSuggestAdapter(Context context, ArrayList<Word> arr, RecyclerView recyclerView) {
        this.sparseBooleanArrayExpand = new SparseBooleanArray();
        this.arrKV = arr;
        this.context = context;
        this.myDataBase = new MyDatabase(context);
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setSpeechRate((float) 0.8);
                }
            }
        });
//        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                totalItemCount = linearLayoutManager.getItemCount();
//                Log.e("Loi", String.valueOf(totalItemCount));
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                    if (onLoadMoreListener != null) {
//                        onLoadMoreListener.onLoadMore();
//                    }
//                    isLoading = true;
//                }
//            }
//        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_word_suggest, viewGroup, false);
            return new ViewHolderItem(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_progressbar, viewGroup, false);
            return new ViewHolderLoading(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ViewHolderItem) {
            final ViewHolderItem holderItem = (ViewHolderItem) viewHolder;
            final Word word = arrKV.get(i);
            final String key = word.getWord();
            final String phonetic = word.getPhonetic();
            final String simpleMeaning = word.getMean();
            if (!phonetic.isEmpty()) {
                holderItem.txtPhonetic.setText(phonetic);
            } else {
                holderItem.txtPhonetic.setVisibility(View.GONE);
            }

            holderItem.txtWord.setText(key);
            holderItem.txtMeaning.setText(simpleMeaning);
            holderItem.imgeSpell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textToSpeech.speak(key,TextToSpeech.QUEUE_FLUSH, null);
                }
            });
            holderItem.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!sparseBooleanArrayExpand.get(holderItem.getAdapterPosition(), false)) {
                        holderItem.imageExpand.startAnimation(AnimationUtils.loadAnimation(context, R.anim.view_rotate_open));
                        holderItem.txtMeaning.setMaxLines(100);
                        holderItem.txtMeaning.setText(Html.fromHtml(word.getValue()));
                        sparseBooleanArrayExpand.put(holderItem.getAdapterPosition(), true);
                    } else {
                        holderItem.imageExpand.startAnimation(AnimationUtils.loadAnimation(context, R.anim.view_rotate_close));
                        holderItem.txtMeaning.setMaxLines(1);
                        holderItem.txtMeaning.setText(simpleMeaning);
                        sparseBooleanArrayExpand.put(holderItem.getAdapterPosition(), false);
                    }
                }
            });

        } else if (viewHolder instanceof ViewHolderLoading){
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) viewHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return arrKV == null ? 0 : arrKV.size();
    }

    @Override
    public int getItemViewType(int position) {
        return arrKV.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
    public void setLoaded() {
        isLoading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        private TextView txtWord;
        private TextView txtMeaning;
        private TextView txtPhonetic;
        private ImageView imageExpand,imgeSpell;
        private LinearLayout itemLayout;

        ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.item_layout);
            txtWord = itemView.findViewById(R.id.txt_word);
            txtMeaning = itemView.findViewById(R.id.txt_meaning);
            txtPhonetic = itemView.findViewById(R.id.txt_phonetic);
            imageExpand = itemView.findViewById(R.id.img_expand);
            imgeSpell = itemView.findViewById(R.id.img_spell);
        }
    }

    private class ViewHolderLoading extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ViewHolderLoading(View view) {
            super(view);
            progressBar = view.findViewById(R.id.itemProgressbar);
        }
    }
}
