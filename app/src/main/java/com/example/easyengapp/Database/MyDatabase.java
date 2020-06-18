package com.example.easyengapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.easyengapp.Model.Sentence;
import com.example.easyengapp.Model.TopicSentence;
import com.example.easyengapp.Model.Topic;
import com.example.easyengapp.Model.Word;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    private static String DbName = "English.db";

    private  String TOPIC_TABLE = "topic_table";
    private String  TOPIC_ID = "topic_id";
    private String TOPIC_NAME = "topic_name";

    private String SENTENCE_TABLE = "sentence_table";
    private String SENTENCE_ID = "sentence_id";
    private String SENTENCE_CONTENT = "sentence_content";
    private String SENTENCE_MEAN = "sentence_mean";

    private String WORD_TABLE = "Dictionary";// bảng chứa các từ trong từ điển
    private String WORD_ID = "word_id";
    private String Word = "Word";// từ
    private String Phonetic = "Phonetic";//phát âm của từ
    private String SimpleMeaning = "SimpleMeaning";// nghĩa
    private String Value = "Value";// thể loại
    private String TopicId = "topic_id";// mã chủ đề
    private String TAG = "MyDatabase";

    Context context;

    public MyDatabase(Context context ) {
        super(context, DbName, null, 1);
        this.context = context;
        Log.d(TAG, "MyDatabase: createDB");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createWordTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " %s text, %s text, %s text,%s text)", WORD_TABLE ,WORD_ID, Word, Phonetic, SimpleMeaning, Value);
        String createTopicTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " %s text)",TOPIC_TABLE,TOPIC_ID,TOPIC_NAME);
        String createSentenceTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s text, %s text)",
                SENTENCE_TABLE,SENTENCE_ID,SENTENCE_CONTENT,SENTENCE_MEAN);
        db.execSQL(createTopicTable);
        db.execSQL(createWordTable);
        db.execSQL(createSentenceTable);
        Log.d(TAG, "onCreate: successfull");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_word_table = String.format("drop table if exist %s", WORD_TABLE);
        db.execSQL(drop_word_table);
        String drop_sen_table = String.format("drop table if exist %s", SENTENCE_TABLE);
        db.execSQL(drop_sen_table);
        String drop_topic_table = String.format("drop table if exist %s", TOPIC_TABLE);
        db.execSQL(drop_topic_table);
        onCreate(db);
    }
    // thêm chủ đề
    public void addTopic(Topic topic){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOPIC_ID,topic.getTopicId());
        contentValues.put(TOPIC_NAME,topic.getTopicName());
        db.insert(TOPIC_TABLE,null,contentValues);
        db.close();
    }
    // thêm chủ đề
//    public void addTopicSentence(TopicSentence topic){
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TOPIC_SEN_NAME,topic.getTopicName());
//        db.insert(TOPIC_SEN_TABLE,null,contentValues);
//        db.close();
//    }
    // thêm từ vào các chủ để
    public void addWord(Word word){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Word, word.getWord());
        values.put(Phonetic,word.getPhonetic());
        values.put(SimpleMeaning,word.getSinpleMeaning());
        values.put(Value,word.getValue());
//        values.put(TopicId,word.getTopicId());
        db.insert(WORD_TABLE,null,values);
        db.close();
    }// thêm các câu vào các chủ đề
    public void addSentence(Sentence sentence){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SENTENCE_CONTENT,sentence.getContent());
        values.put(SENTENCE_MEAN,sentence.getMean());
//        values.put(TopicId,sentence.getTopicId());
        db.insert(SENTENCE_TABLE,null,values);
        db.close();
    }
    // lấy danh sách các từ trong chủ đề ra
    public ArrayList<Word> getWordTopicByid(int topic_id){
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        /*
         * do something
         * */
        return words;
    }
    // lấy các câu trong chủ để ra
    public ArrayList<Sentence> getSentenceTopicByid(int topic_id){
        ArrayList<Sentence> sentences = new ArrayList<>();
        /*
        * do something
        * */
        return sentences;
    }

    public ArrayList<Word> getWord(String tableName, int number){
        ArrayList<Word> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from "+tableName;
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        cursor.moveToFirst();
        if(count > 0){
            if(number >= count || number == 0){
                for(int i = 0;i<count;i++){
                    Word w = new Word(cursor.getString(1),cursor.getString(2)
                            ,cursor.getString(3),cursor.getString(4));
                    arr.add(w);
                    cursor.moveToNext();
                }
            }else{
                for(int i = 0;i<number;i++){
                    Word w = new Word(cursor.getString(0),cursor.getString(1)
                            ,cursor.getString(2),cursor.getString(3));
                    arr.add(w);
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        db.close();
        return arr;
    }
//    public ArrayList<Sentence> getSentence(String tableName,int number){
//
//    }
}
