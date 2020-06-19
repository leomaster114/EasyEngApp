package com.example.easyengapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.easyengapp.Model.Sentence;
import com.example.easyengapp.Model.Topic;
import com.example.easyengapp.Model.Word;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    private static String DbName = "English.db";

    private String TOPIC_TABLE = "topic_table";
    private String TOPIC_ID = "topicId";
    private String TOPIC_NAME = "topic_name";

    private String SENTENCE_TABLE = "sentence_table";
    private String SENTENCE_ID = "sentence_id";
    private String SENTENCE_CONTENT = "sentence_content";
    private String SENTENCE_MEAN = "sentence_mean";

    private String WORD_TABLE = "word_table";// bảng chứa các từ trong từ điển
    private String WORD_ID = "word_id";
    private String Word = "word";// từ
    private String Phonetic = "phonetic";//phát âm của từ
    private String SimpleMeaning = "mean";// nghĩa
    private String Value = "value";// thể loại, vi du,...
    private String TopicId = "topicId";// mã chủ đề
    private String TAG = "MyDatabase";

    Context context;

    public MyDatabase(Context context) {
        super(context, DbName, null, 1);
        this.context = context;
        Log.d(TAG, "MyDatabase: createDB");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createWordTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + " %s text, %s text, %s text,%s text,%s INTEGER, FOREIGN KEY (%s) REFERENCES %s(%s))"
                , WORD_TABLE, WORD_ID, Word, Phonetic, SimpleMeaning, Value, TopicId, TopicId, TOPIC_TABLE, TopicId);
        String createTopicTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " %s text)", TOPIC_TABLE, TOPIC_ID, TOPIC_NAME);
        String createSentenceTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s text, %s text,%s INTEGER, FOREIGN KEY (%s) REFERENCES %s(%s))",
                SENTENCE_TABLE, SENTENCE_ID, SENTENCE_CONTENT, SENTENCE_MEAN, TopicId, TopicId, TOPIC_TABLE, TopicId);
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

    // thêm từ vào các chủ để
    public void addWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Word, word.getWord());
        values.put(Phonetic, word.getPhonetic());
        values.put(SimpleMeaning, word.getMean());
        values.put(Value, word.getValue());
        values.put(TopicId, word.getTopic().getTopicId());
        db.insert(WORD_TABLE, null, values);
        db.close();
    }

    // lấy danh sách các từ trong chủ đề ra
    public ArrayList<Word> getWordTopicByName(String topicName) {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select w.word_id,w.word, w.phonetic, w.mean, w.value, tp.topicId, tp.topic_name from word_table as w, topic_table as tp " +
                "where w.topicId = tp.topicId and tp.topic_name = ?";
        String[] Args = new String[]{topicName};
        Cursor cursor = db.rawQuery(query, Args);
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                Topic topic = new Topic();
                word.setWord_id(cursor.getInt(0));
                word.setWord(cursor.getString(1));
                word.setPhonetic(cursor.getString(2));
                word.setMean(cursor.getString(3));
                word.setValue(cursor.getString(4));
                //
                topic.setTopicId(cursor.getInt(5));
                topic.setTopicName(cursor.getString(6));
                //
                word.setTopic(topic);
                words.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return words;
    }
    // lấy danh sách các từ trong chủ đề ra
    public ArrayList<Word> getWordTopicByid(int idTopic) {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select w.word_id,w.word, w.phonetic, w.mean, w.value, tp.topicId, tp.topic_name from word_table as w, topic_table as tp " +
                "where w.topicId = tp.topicId and tp.topicId = ?";
        String[] Args = new String[]{String.valueOf(idTopic)};
        Cursor cursor = db.rawQuery(query, Args);
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                Topic topic = new Topic();
                word.setWord_id(cursor.getInt(0));
                word.setWord(cursor.getString(1));
                word.setPhonetic(cursor.getString(2));
                word.setMean(cursor.getString(3));
                word.setValue(cursor.getString(4));
                //
                topic.setTopicId(cursor.getInt(5));
                topic.setTopicName(cursor.getString(6));
                //
                word.setTopic(topic);
                words.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return words;
    }
    // get all word
    public ArrayList<Word> getWord(String tableName) {
        ArrayList<Word> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select w.word_id,w.word, w.phonetic, w.mean, w.value, tp.topicId, tp.topic_name from word_table as w, topic_table as tp " +
                "where w.topicId = tp.topicId";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                Topic topic = new Topic();
                word.setWord_id(cursor.getInt(0));
                word.setWord(cursor.getString(1));
                word.setPhonetic(cursor.getString(2));
                word.setMean(cursor.getString(3));
                word.setValue(cursor.getString(4));
                //
                topic.setTopicId(cursor.getInt(5));
                topic.setTopicName(cursor.getString(6));
                //
                word.setTopic(topic);
                arr.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arr;
    }

    // thêm chủ đề
    public void addTopic(Topic topic) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(TOPIC_ID, topic.getTopicId());
        contentValues.put(TOPIC_NAME, topic.getTopicName());
        db.insert(TOPIC_TABLE, null, contentValues);
        db.close();
    }
    //get All topic
    public ArrayList<Topic> getAllTopic(){
        ArrayList<Topic> arrTopic = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select tp.topicId, tp.topic_name from topic_table as tp ";
        Cursor cursor = db.rawQuery(query, null);
        Log.d(TAG, "getAllTopic: cursor count "+cursor.getCount());
        Log.d(TAG, "getAllTopic: movetofirst"+(cursor.moveToFirst() == true));

        if (cursor.moveToFirst()) {
            do {
                Topic topic = new Topic();
                topic.setTopicId(cursor.getInt(0));
                topic.setTopicName(cursor.getString(1));
                arrTopic.add(topic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrTopic;
    }
    //get All topic
    public Topic getTopicById(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = "select tp.topicId, tp.topic_name from topic_table as tp where tp.topicId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        Log.d(TAG, "getTopicById: cursor count "+cursor.getCount());
        Log.d(TAG, "getTopicById: movetofirst"+(cursor.moveToFirst() == true));

        if (cursor.moveToFirst()) {
            do {
                Topic topic = new Topic();
                topic.setTopicId(cursor.getInt(0));
                topic.setTopicName(cursor.getString(1));
                return topic;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return null;
    }
    public Topic getTopicByName(String topicName){
        SQLiteDatabase db = getReadableDatabase();
        String query = "select tp.topicId, tp.topic_name from topic_table as tp where tp.topicName = ?";
        String[] args = new String[]{topicName};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            do {
                Topic topic = new Topic();
                topic.setTopicId(cursor.getInt(0));
                topic.setTopicName(cursor.getString(1));
                return topic;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return null;
    }

    // thêm các câu vào các chủ đề
    public void addSentence(Sentence sentence) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SENTENCE_CONTENT, sentence.getContent());
        values.put(SENTENCE_MEAN, sentence.getMean());
        values.put(TopicId, sentence.getTopic().getTopicId());
        db.insert(SENTENCE_TABLE, null, values);
        db.close();
    }

    // lấy các câu trong chủ để ra
    public ArrayList<Sentence> getSentenceByTopic(String topicName) {
        ArrayList<Sentence> arrsentences = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select s.sentences_id,s.sentences_content,s.sentences_mean, tp.topicId, tp.topic_name from sentences_table as s, topic_table as tp " +
                "where s.topicId = tp.topicId and tp.topic_name = ?";
        String[] Args = new String[]{topicName};
        Cursor cursor = db.rawQuery(query, Args);
        if (cursor.moveToFirst()) {
            do {
               Sentence sentence = new Sentence();
                Topic topic = new Topic();
               sentence.setSentence_id(cursor.getInt(0));
                sentence.setContent(cursor.getString(1));
                sentence.setMean(cursor.getString(2));
                //
                topic.setTopicId(cursor.getInt(3));
                topic.setTopicName(cursor.getString(4));
                //
                sentence.setTopic(topic);
                arrsentences.add(sentence);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrsentences;
    }
}
