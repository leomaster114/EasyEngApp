package com.example.easyengapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.example.easyengapp.Model.KeyAndValue;
import com.example.easyengapp.Model.Sentence;
import com.example.easyengapp.Model.Topic;
import com.example.easyengapp.Model.TopicByIdResponse;
import com.example.easyengapp.Model.TopicResponse;
import com.example.easyengapp.Model.Word;
import com.example.easyengapp.Retrofit.RetrofitClient;
import com.google.gson.internal.$Gson$Preconditions;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranferDatabase {
    private String dbName = "TransferDatabase";
    private DB tranDb;
    private Context mContext;
    private MyDatabase myDatabase;
    private String DB_Name = "English.db";
    private String DB_PATH = "/data/data/com.example.easyengapp/database/";

    public TranferDatabase(Context context, boolean isFirsttime) {
        try {
            this.tranDb = DBFactory.open(context, dbName);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        this.mContext = context;
        this.myDatabase = new MyDatabase(context);
        if (isFirsttime) {
            putJsonFile("vietanh.json");
            putJsonFile("anhviet.json");
            putJsonFile("sentences.json");
            createDBTopic();
            creatDBDictionary();
            createDBSentences();
            copyDataBase();
        }
    }

    private void copyDataBase() {
        try {
            //mo database trong asset
            InputStream inputStream = mContext.getAssets().open(DB_Name);

            //duong dan de tao db rong
            String outFileName = DB_PATH + DB_Name;

            // mo db rong de ghi vao
            OutputStream outputStream = new FileOutputStream(outFileName);
            //ghi du lieu tu db asset vao db moi
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            //dong streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e("Loi_copyDB", e.getMessage());
        }
    }

    private void putJsonFile(String filePath) {
        String line;
        String[] reString;
        String key;
        String value;
        try {
            AssetManager asset = mContext.getAssets();
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(asset.open(filePath)));
            while ((line = bf.readLine()) != null) {
                if (line.length() == 1) continue;
                reString = line.split(":", 2);
                if (reString.length > 1) {
                    key = reString[0].substring(1, reString[0].length() - 1).toLowerCase();
                    if (reString[1].length() > 2) {
                        if (reString[1].charAt(reString[1].length() - 1) == ',') {
                            value = reString[1].substring(2, reString[1].length() - 2).toLowerCase();
                        } else {
                            value = reString[1].substring(2, reString[1].length() - 1).toLowerCase();
                        }
                    } else {
                        value = "";
                    }
                    set(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void set(String key, String value) {
        try {
            this.tranDb.put(key, value);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    private String get(String key) {
        try {
            return this.tranDb.get(key);
        } catch (SnappydbException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<KeyAndValue> findKeyAndValue(String key) {
        try {
            ArrayList<KeyAndValue> arr = new ArrayList<>();
            String[] list = this.tranDb.findKeys(key);
            for (String str : list) {
                arr.add(new KeyAndValue(str, this.tranDb.get(str)));
            }
            return arr;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void creatDBDictionary() {
        myDatabase.deleteAllWord();
        String line, key, word, phonetic, simpleMeaning, value, content;
        try {
            AssetManager assetManager = mContext.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("word_topic.txt")));
            while ((line = bf.readLine()) != null) {
                line = line.toLowerCase().trim();
                int tp = Integer.parseInt(line.split("-")[0].trim());
//                Log.d("TAG", "creatDBDictionary2: tp = "+tp);
                word = line.split("-")[1].trim();
                if (findKeyAndValue(word).size() > 0) {
                    KeyAndValue keyAndValue = findKeyAndValue(word).get(0);
                    key = keyAndValue.getKey();
                    value = keyAndValue.getValue();
                    ArrayList<String> strings = new Reformat_word().spilit(value);
                    phonetic = new Reformat_word().getPhonetic(strings);
                    simpleMeaning = new Reformat_word().getSimpleMeaning(strings);
                    content = new Reformat_word().toViewFormat(key, strings);
                    Call<TopicByIdResponse> call = RetrofitClient.getInstance().getApi().getTopicById(tp);
                    final String finalKey = key;
                    final String finalPhonetic = phonetic;
                    final String finalSimpleMeaning = simpleMeaning;
                    final String finalContent = content;
                    call.enqueue(new Callback<TopicByIdResponse>() {
                        @Override
                        public void onResponse(Call<TopicByIdResponse> call, Response<TopicByIdResponse> response) {
                            final Topic topic = response.body().getTopic();
                            myDatabase.addWord(new Word(finalKey, finalPhonetic, finalSimpleMeaning, finalContent, topic));
                        }

                        @Override
                        public void onFailure(Call<TopicByIdResponse> call, Throwable t) {
                            Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Log.e("Loi", line);
                }
            }
        } catch (Exception e) {
            Log.e("Loi_createDB", e.getMessage());
        }
    }

    public void createDBSentences() {
        myDatabase.deleteAllWord();
        String line, key, value, content;
        try {
            AssetManager assetManager = mContext.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("sentences.txt")));
            while ((line = bf.readLine()) != null) {
                line = line.toLowerCase().trim();
                int tp = Integer.parseInt(line.split("-")[0].trim());
                content = line.split("-")[1].trim();
                if (findKeyAndValue(content).size() > 0) {
                    KeyAndValue keyAndValue = findKeyAndValue(content).get(0);
                    key = keyAndValue.getKey();
                    value = keyAndValue.getValue();

                    Call<TopicByIdResponse> call = RetrofitClient.getInstance().getApi().getTopicById(tp);
                    final String finalKey = key;
                    final String finalValue = value;
                    call.enqueue(new Callback<TopicByIdResponse>() {
                        @Override
                        public void onResponse(Call<TopicByIdResponse> call, Response<TopicByIdResponse> response) {
                            final Topic topic = response.body().getTopic();
                            myDatabase.addSentence(new Sentence(finalKey, finalValue, topic));
                        }

                        @Override
                        public void onFailure(Call<TopicByIdResponse> call, Throwable t) {
                            Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.e("Loi", line);
                }
            }
        } catch (Exception e) {
            Log.e("Loi_createDB", e.getMessage());
        }
    }

    public void createDBTopic() {

        Call<TopicResponse> call = RetrofitClient.getInstance().getApi().getAllTopic();
        call.enqueue(new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                ArrayList<Topic> topics = (ArrayList<Topic>) response.body().getTopics();
                for (Topic topic : topics) {
                    myDatabase.addTopic(topic);
                }
            }

            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}