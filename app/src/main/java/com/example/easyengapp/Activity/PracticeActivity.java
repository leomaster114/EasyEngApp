package com.example.easyengapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyengapp.Database.MyDatabase;
import com.example.easyengapp.DialogWord;
import com.example.easyengapp.FullScreenDialog;
import com.example.easyengapp.Model.Sentence;
import com.example.easyengapp.Model.Word;
import com.example.easyengapp.R;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PracticeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PracticeFrag";
    private FlowLayout layout_fill, layout_choose;
    private LinearLayout layout_result;
    private Button btn_next, btn_check;
    private Animation ani_add, ani_remove;
    private int width, height, elevation, textSize, margin;
    private LinearLayout.LayoutParams params;
    private int idTop, length, index = 0;// chieu dai cau, vi tri cau, tong so cau
    private boolean pass;
    private String content;//noi dung tieng anh cua cau
    private ArrayList<Sentence> arrSentence;
    private String[] arrContent;// tach content ra thanh cac tu rieng
    private int idTopic;
    private Sentence sentence;
    private String mean;// nghia tieng viet cua cau
    private TextView tv_mean, tv_result, tv_notify;
    private ImageButton btn_cancel;
    ProgressBar progressBar;
    private MyDatabase database;
    private int answered = 0, correctanswer = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        layout_choose = findViewById(R.id.layout_choose);
        layout_fill = findViewById(R.id.layout_fill_answer);
        layout_result = findViewById(R.id.layout_result);
        btn_next = findViewById(R.id.btn_continue);
        btn_check = findViewById(R.id.btn_check);
        tv_notify = findViewById(R.id.tv_notify);
        tv_mean = findViewById(R.id.txt_meaning);
        tv_result = findViewById(R.id.tv_result);
        btn_cancel = findViewById(R.id.btn_cancel);
        progressBar = findViewById(R.id.progress);
        //
        btn_next.setOnClickListener(this);
        btn_check.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        //
        Intent intent = getIntent();
        idTopic = intent.getIntExtra("IdTopic", -1);
        database = new MyDatabase(this);
        arrSentence = new ArrayList<>();
        arrContent = new String[]{};//mang cac chu trong cau bi dao lon
        if (idTopic != -1) {
            arrSentence = database.getSentenceTopicByid(idTopic);
            Collections.shuffle(arrSentence);
        }
        initDimens();
        load(index);
    }

    private void load(int index) {
        pass = false;
        sentence = arrSentence.get(index);
        content = sentence.getContent();
        arrContent = content.split(" ");
        mean = sentence.getMean();
        tv_mean.setText(mean);
        init1(shufferContent(arrContent));
        init2(shufferContent(arrContent));
        idTop = 1000;
        length = 1000 + arrContent.length - 1;
        btn_check.setVisibility(View.VISIBLE);
        btn_next.setVisibility(View.GONE);
        tv_notify.setBackgroundColor(Color.RED);
        tv_notify.setVisibility(View.GONE);
        btn_next.setBackgroundColor(Color.GRAY);
        layout_result.setVisibility(View.GONE);
        layout_fill.setVisibility(View.VISIBLE);
    }

    // dao cac tu trong cau
    private List<String> shufferContent(String[] content) {
        List<String> contents = Arrays.asList(content);
        Collections.shuffle(contents);
        return contents;
    }

    public void initDimens() {
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
        elevation = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        params = new LinearLayout.LayoutParams(width, height);
        params.setMargins(margin, margin, margin, margin);
        ani_add = AnimationUtils.loadAnimation(this, R.anim.view_zoom_out);
        ani_remove = AnimationUtils.loadAnimation(this, R.anim.view_zoom_in);
    }

    // init where show box to fill answer
    public void init1(List<String> string) {
        layout_fill.removeAllViews();
        for (int i = 0; i < string.size(); i++) {
            TextView textView = getTextView();
            textView.setText("");
            textView.setBackground(getDrawable(R.drawable.background_char_null));
            textView.setId(1000 + i);
            layout_fill.addView(textView);
        }

    }

    // init where show box to choose answer
    public void init2(List<String> string) {
        layout_choose.removeAllViews();
        for (int i = 0; i < string.size(); i++) {
            TextView textView = getTextView();
            textView.setText(string.get(i));
            textView.setBackground(getDrawable(R.drawable.background_char));
            textView.setId(2000 + i);
            layout_choose.addView(textView);
        }

    }

    public TextView getTextView() {
        TextView textView = new TextView(this);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(textSize);
        textView.setElevation(elevation);
        textView.setLayoutParams(params);
        textView.setClickable(true);
        textView.setOnClickListener(this);
        return textView;
    }

    @Override
    public void onClick(View v) {
         if (v.getId() == R.id.btn_continue) {
            index++;
            load(index);
        }else if(v.getId() == R.id.btn_cancel){
                showDialogCancel();
         }else if (v.getId() == R.id.btn_check) {
                answered+=1;
            if (checkCorrect()) {
                pass = true;
                correctanswer+=1;
                tv_notify.setText("Đáp án đúng");
                tv_notify.setBackgroundColor(Color.GREEN);
                btn_next.setBackgroundColor(Color.GREEN);
                showAnswer();
            } else {
                tv_notify.setText("Đáp án sai");
                showAnswer();
            }
             progressBar.setProgress(progressBar.getProgress()+10);
             if(answered == 10){
                showDialogReport();
            }
        } else {// click vao cac tv trong cau tra loi
            try {
                int id = v.getId();
                if (id >= 2000) {// click vao tu goi y de xep thanh cau tra loi
                    TextView txt2 = findViewById(id);
                    String str = txt2.getText().toString();
                    TextView txt1 = findViewById(idTop);
                    txt1.setText(str);
                    txt1.setBackground(getDrawable(R.drawable.background_char));
                    txt1.startAnimation(ani_add);
                    idTop++;
                    txt2.startAnimation(ani_remove);
                    txt2.setVisibility(View.INVISIBLE);

                } else {// click vao cau tra loi de tra loi lai
                    if (!pass) {
                        TextView txt1 = findViewById(id);
                        String str = txt1.getText().toString();
                        if (!str.equals("")) {
                            txt1.setText("");
                            txt1.setBackground(getDrawable(R.drawable.background_char_null));
                            idTop = id;
                            while (true) {
                                if (id == length) break;
                                else {
                                    TextView txt = findViewById(idTop + 1);
                                    if (!txt.getText().toString().equals("")) {
                                        txt1.setText(txt.getText().toString());
                                        txt1.setBackground(getDrawable(R.drawable.background_char));
                                        txt.setText("");
                                        txt.setBackground(getDrawable(R.drawable.background_char_null));
                                        idTop += 1;
                                        txt1.findViewById(idTop);
                                    } else break;
                                }
                            }
                            for (int i = 2000; i <= (1000 + length); i++) {
                                TextView txt2 = layout_choose.findViewById(i);
                                if (txt2.getText().equals(str) && txt2.getVisibility() == View.INVISIBLE) {
                                    txt2.setVisibility(View.VISIBLE);
                                    txt2.startAnimation(ani_add);
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Toast.makeText(this, "Chọn chậm thôi bạn :))", Toast.LENGTH_LONG).show();
                load(index);
            }

        }
    }

    private void showDialogReport() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PracticeActivity.this);
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
        if(correctanswer>=8) txt_ketqua.setText("Chúc mừng bạn đã vượt qua mức 2");
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
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(PracticeActivity.this);

        dialogDelete.setTitle("Chú ý!!");
        dialogDelete.setMessage("Bạn có muốn huỷ bài làm này?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PracticeActivity.this,MainActivity.class);
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
        String strCheck = "";
        for (int i = 1000; i <= length; i++) {
            TextView txt = findViewById(i);
            strCheck = strCheck + txt.getText().toString() + " ";
        }
        Log.d(TAG, "checkCorrect: content = " + content);
        Log.d(TAG, "checkCorrect: strcheck = " + strCheck);
        return content.equals(strCheck.trim());
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
        layout_choose.removeAllViews();
        layout_fill.setVisibility(View.GONE);
        layout_result.setVisibility(View.VISIBLE);
        tv_result.setText(content);
        tv_result.setVisibility(View.VISIBLE);
        tv_result.startAnimation(ani_add);
    }
}