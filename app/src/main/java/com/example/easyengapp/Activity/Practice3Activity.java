package com.example.easyengapp.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.easyengapp.Model.Sentence;
import com.example.easyengapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Practice3Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PracticeFrag";
    private Button btn_next, btn_check;
    private Animation ani_add, ani_remove;
    private int index = 0;// chieu dai cau, vi tri cau, tong so cau
    private String content;//noi dung tieng anh cua cau
    private ArrayList<Sentence> arrSentence;
    private int idTopic;
    private Sentence sentence;
    private TextView edt_answer, tv_result, tv_notify;
    private ImageButton btn_cancel;
    private ImageView img_spell;
    ProgressBar progressBar;
    private MyDatabase database;
    private int answered = 0, correctanswer = 0;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice3);

        btn_next = findViewById(R.id.btn_continue);
        btn_check = findViewById(R.id.btn_check);
        tv_notify = findViewById(R.id.tv_notify);
        edt_answer = findViewById(R.id.edt_your_answer);
        tv_result = findViewById(R.id.tv_result);
        btn_cancel = findViewById(R.id.btn_cancel);
        progressBar = findViewById(R.id.progress);
        img_spell = findViewById(R.id.img_spell);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setSpeechRate((float) 0.8);
                }
            }
        });
        ani_add = AnimationUtils.loadAnimation(this, R.anim.view_zoom_out);
        ani_remove = AnimationUtils.loadAnimation(this, R.anim.view_zoom_in);
        //
        img_spell.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_check.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        //
        Intent intent = getIntent();
        idTopic = intent.getIntExtra("IdTopic", -1);
        database = new MyDatabase(this);
        arrSentence = new ArrayList<>();
        if (idTopic != -1) {
            arrSentence = database.getSentenceTopicByid(idTopic);
            Collections.shuffle(arrSentence);
        }
        load(index);
    }

    private void load(int index) {
        sentence = arrSentence.get(index);
        content = sentence.getContent();
        speak(content);
        edt_answer.setText("");
        tv_result.setVisibility(View.GONE);
        btn_check.setVisibility(View.VISIBLE);
        btn_next.setVisibility(View.GONE);
        tv_notify.setBackgroundColor(Color.RED);
        tv_notify.setVisibility(View.GONE);
        btn_next.setBackgroundColor(Color.GRAY);

    }

    private void speak(String content) {
        textToSpeech.speak(content, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue: {
                index++;
                load(index);
                break;
            }
            case R.id.btn_cancel: {
                showDialogCancel();
                break;
            }
            case R.id.btn_check: {
                if (edt_answer.getText().toString().equals(""))
                    Toast.makeText(this, "Điền câu trả lời", Toast.LENGTH_SHORT).show();
                else {
                    answered += 1;
                    if (checkCorrect()) {
                        correctanswer += 1;
                        tv_notify.setText("Đáp án đúng");
                        tv_notify.setBackgroundColor(Color.GREEN);
                        btn_next.setBackgroundColor(Color.GREEN);
                        showAnswer();
                        // tang progessbar
                    } else {
                        tv_notify.setText("Đáp án sai");
                        showAnswer();
                    }
                    if (answered == 10) {
                        showDialogReport();
                    }
                    progressBar.setProgress(progressBar.getProgress() + 10);
                    break;
                }
            }

            case R.id.img_spell: {
                speak(sentence.getContent());
                break;
            }
        }
    }

    private void showDialogReport() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Practice3Activity.this);
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
        txtTotal_correct.setText("Số câu đúng: " + correctanswer);
        if (correctanswer >= 8) txt_ketqua.setText("Chúc mừng bạn đã vượt qua mức 2");
        else txt_ketqua.setText("Rất tiếc bạn chưa vượt qua mức 2");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    private void showDialogCancel() {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Practice3Activity.this);

        dialogDelete.setTitle("Chú ý!!");
        dialogDelete.setMessage("Bạn có muốn huỷ bài làm này?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Practice3Activity.this, MainActivity.class);
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

    // kiem tr dap an
    public boolean checkCorrect() {
        return sentence.getContent().toLowerCase().equals(edt_answer.getText().toString().toLowerCase());
    }

    @Override
    public void onBackPressed() {
        showDialogCancel();
    }

    // hien thi dap an dung
    public void showAnswer() {
        tv_notify.setVisibility(View.VISIBLE);
        tv_notify.startAnimation(ani_add);
        btn_next.setVisibility(View.VISIBLE);
        btn_next.startAnimation(ani_add);
        btn_check.setVisibility(View.GONE);
        tv_result.setText(content);
        tv_result.setVisibility(View.VISIBLE);
        tv_result.startAnimation(ani_add);
    }
}