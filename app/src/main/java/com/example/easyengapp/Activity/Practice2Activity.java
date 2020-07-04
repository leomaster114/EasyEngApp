package com.example.easyengapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyengapp.Database.MyDatabase;
import com.example.easyengapp.Model.Result;
import com.example.easyengapp.Model.SaveResultResponse;
import com.example.easyengapp.Model.Sentence;
import com.example.easyengapp.R;
import com.example.easyengapp.Retrofit.RetrofitClient;
import com.example.easyengapp.storage.SharePrefManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Practice2Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_micro, img_spell;
    ImageButton img_btn_cancel;
    ProgressBar progressBar;
    TextView tv_read_sen, tv_notify, tv_read_answer;
    Button btn_check, btn_continue;
    MyDatabase database;
    private Animation ani_add, ani_remove;
    private int answered = 0, correctanswer = 0;
    private ArrayList<Sentence> arrSentence;//mang cau trong bai test
    private int idTopic,index;
    TextToSpeech textToSpeech;
    boolean pass;
    MediaPlayer sound_correct,sound_incorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice2);
        initAll();
        btn_continue.setOnClickListener(this);
        btn_check.setOnClickListener(this);
        img_btn_cancel.setOnClickListener(this);
        img_btn_cancel.setOnClickListener(this);
        img_micro.setOnClickListener(this);
        img_spell.setOnClickListener(this);
        sound_correct = MediaPlayer.create(this, R.raw.correct);
        sound_incorrect = MediaPlayer.create(this, R.raw.incorrect);
        load(index);
        textToSpeech  = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setSpeechRate((float) 0.8);
                }
            }
        });
    }

    private void initAll() {
        img_spell = findViewById(R.id.imgv_spell);
        img_micro = findViewById(R.id.img_micro);
        img_btn_cancel = findViewById(R.id.btn_cancel);
        progressBar = findViewById(R.id.progress);
        tv_notify = findViewById(R.id.tv_notify);
        tv_read_answer = findViewById(R.id.tv_read_answer);
        tv_read_sen = findViewById(R.id.tv_read_sen);
        btn_check = findViewById(R.id.btn_check);
        btn_continue =findViewById(R.id.btn_continue);
        ani_add = AnimationUtils.loadAnimation(this, R.anim.view_zoom_out);
        ani_remove = AnimationUtils.loadAnimation(this, R.anim.view_zoom_in);
        pass = false;
        //
        Intent intent = getIntent();
        idTopic = intent.getIntExtra("IdTopic", -1);
        database = new MyDatabase(this);
        arrSentence = new ArrayList<>();
        if (idTopic != -1) {
            arrSentence = database.getSentenceTopicByid(idTopic);
            Collections.shuffle(arrSentence);
        }
    }
    private void load(int index){
        tv_read_sen.setText(arrSentence.get(index).getContent());
        tv_read_answer.setText("Câu trả lời của bạn:");
        btn_check.setVisibility(View.VISIBLE);
        btn_continue.setVisibility(View.GONE);
        tv_notify.setBackgroundColor(Color.RED);
        tv_notify.setVisibility(View.GONE);
        btn_continue.setBackgroundColor(Color.GRAY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                showDialogCancel();
                break;
            case R.id.btn_continue:
                index++;
                load(index);
                break;
            case R.id.btn_check:
                if(tv_read_answer.getText().toString().equals("Câu trả lời của bạn:"))
                    Toast.makeText(this,"Please speak anything!",Toast.LENGTH_SHORT).show();
                else{
                    answered+=1;
                    if (checkCorrect()) {
                        correctanswer+=1;
                        sound_correct.start();
                        tv_notify.setText("Đáp án đúng");
                        tv_notify.setBackgroundColor(Color.GREEN);
                        btn_continue.setBackgroundColor(Color.GREEN);
                        showButton();
                    } else {
                        sound_incorrect.start();

                        tv_notify.setText("Đáp án sai");
                        showButton();
                    }
                    if(answered == 10){
                        showDialogReport();
                    }
                    progressBar.setProgress(progressBar.getProgress()+10);
                }
                break;
            case R.id.img_micro:
                getInputSpeech();
                break;
            case R.id.imgv_spell:
                speak(arrSentence.get(index).getContent());
                break;

        }
    }
    public void speak(String sentences){
        textToSpeech.speak(sentences, TextToSpeech.QUEUE_FLUSH, null);
    }
    private void showButton() {
        tv_notify.setVisibility(View.VISIBLE);
        tv_notify.startAnimation(ani_add);
        btn_continue.setVisibility(View.VISIBLE);
        btn_continue.startAnimation(ani_add);
        btn_check.setVisibility(View.GONE);
    }

    private boolean checkCorrect() {
        return tv_read_sen.getText().toString().equals(tv_read_answer.getText().toString().toLowerCase());
    }

    private void showDialogReport() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Practice2Activity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_report, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        TextView txtTotal = dialogView.findViewById(R.id.txt_total);
        TextView txtTotal_correct = dialogView.findViewById(R.id.txt_total_correct);
        Button btn_ok = dialogView.findViewById(R.id.btn_ok);
        TextView txt_ketqua = dialogView.findViewById(R.id.tv_ketqua);
        txtTotal.setText("Tổng số câu: 10");
        txtTotal_correct.setText("Số câu đúng: "+correctanswer);
        if(correctanswer>=8){
            txt_ketqua.setText("Chúc mừng bạn đã vượt qua mức 2");
            pass = true;
        }
        else {
            txt_ketqua.setText("Rất tiếc bạn chưa vượt qua mức 2");
            pass = false;
        }
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result result = new Result();
                result.setLevel_id("5ef56717b0aeb41110c27930");
                result.setTopic_id(database.getTopicById(idTopic).get_id());
                result.setUser_id(SharePrefManager.getInstance(Practice2Activity.this).getUser().getId());
                result.setPoint(correctanswer);

                result.setPass(pass);
                Call<SaveResultResponse> call = RetrofitClient.getInstance().getApi().createResult(result);
                call.enqueue(new Callback<SaveResultResponse>() {
                    @Override
                    public void onResponse(Call<SaveResultResponse> call, Response<SaveResultResponse> response) {
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<SaveResultResponse> call, Throwable t) {
                        Toast.makeText(Practice2Activity.this,"Save result: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        dialog.show();
    }

    private void getInputSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,100);
        }else{
            Toast.makeText(this, "Không hỗ trợ ghi âm", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if (resultCode == RESULT_OK && data !=null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv_read_answer.setText(result.get(0));
                }
                break;
        }
    }
    @Override
    public void onBackPressed() {
        showDialogCancel();
    }
    private void showDialogCancel() {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Practice2Activity.this);

        dialogDelete.setTitle("Chú ý!!");
        dialogDelete.setMessage("Bạn có muốn huỷ bài làm này?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Practice2Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        dialogDelete.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }
}