package com.example.easyengapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyengapp.Activity.MainActivity;
import com.example.easyengapp.Activity.Practice2Activity;
import com.example.easyengapp.Activity.Practice3Activity;
import com.example.easyengapp.Activity.PracticeActivity;
import com.example.easyengapp.Database.MyDatabase;
import com.example.easyengapp.Model.GetResultResponse;
import com.example.easyengapp.Model.Result;
import com.example.easyengapp.Model.Topic;
import com.example.easyengapp.R;
import com.example.easyengapp.Retrofit.RetrofitClient;
import com.example.easyengapp.storage.SharePrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * fragment Luyện tập nơi chứa những bài tập
 */
public class PracticeFragment extends Fragment {
    Context mContext;
    private String TAG = getClass().getSimpleName();
    private MyDatabase database;
    int numberlevelPassed;
    View view;
    public PracticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_practice, container, false);
        mContext = getContext();
        database = new MyDatabase(mContext);
        Toast.makeText(mContext,"Practice Fragment",Toast.LENGTH_SHORT).show();
        getResult();
        return view;
    }
    /*
    public void show_practice(View view) {
        int id = -1;
        switch (view.getId()) {
            case R.id.tp1:
                id = 1;
                break;
            case R.id.tp2:
                id = 2;
                break;
            case R.id.tp3:
                id = 3;
                break;
            case R.id.tp4:
                id = 4;
                break;
            case R.id.tp5:
                id = 5;
                break;
            case R.id.tp6:
                id = 6;
                break;
            case R.id.tp7:
                id = 7;
                break;
            case R.id.tp8:
                id = 8;
                break;
            case R.id.tp9:
                id = 9;
                break;
            case R.id.tp10:
                id = 10;
                break;
            case R.id.tp11:
                id = 11;
                break;
            case R.id.tp12:
                id = 12;
                break;
            case R.id.tp13:
                id = 13;
                break;
            case R.id.tp14:
                id = 14;
                break;
            case R.id.tp15:
                id = 15;
                break;
            case R.id.tp16:
                id = 16;
                break;
            case R.id.tp17:
                id = 17;
                break;
            case R.id.tp18:
                id = 18;
                break;
        }
//
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_choose_practice, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        final TextView tv_topicName = dialogView.findViewById(R.id.tv_topicname);
        TextView tv_passRate = dialogView.findViewById(R.id.tv_pass_rate);
        Button btn_dang1 = dialogView.findViewById(R.id.btn_dang1);
        Button btn_dang2 = dialogView.findViewById(R.id.btn_dang2);
        Button btn_dang3 = dialogView.findViewById(R.id.btn_dang3);
        TextView txt_ketqua = dialogView.findViewById(R.id.tv_ketqua);
        Topic tp = database.getTopicById(id);
        tv_topicName.setText(tp.getName_topic());
        getLevelPassed(id,tv_passRate);
        final int topicId = id;
        btn_dang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PracticeActivity.class);
                intent.putExtra("IdTopic", topicId);
                startActivity(intent);
            }
        });
        btn_dang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Practice2Activity.class);
                intent.putExtra("IdTopic", topicId);
                startActivity(intent);
            }
        });
        btn_dang3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Practice3Activity.class);
                intent.putExtra("IdTopic", topicId);
                startActivity(intent);
            }
        });
        dialog.show();
    }
*/
    public void getResult() {
        String user_id = SharePrefManager.getInstance(mContext).getUser().getId();
        for (int id_topic = 1; id_topic <= 18; id_topic++) {
            final int[] levelPassed = {0};
//            Topic topic = database.getTopicById(id_topic);
//            Log.d(TAG, "getResult: topic "+id_topic+" = "+topic.toString());
            String topic_id = database.getTopicById(id_topic).get_id();
            Log.d(TAG, "getResult: topic_id = " + topic_id);
            Call<GetResultResponse> call = RetrofitClient.getInstance().getApi().getResultByUserTopic(user_id, topic_id);
            final int finalId_topic = id_topic;
            call.enqueue(new Callback<GetResultResponse>() {
                @Override
                public void onResponse(Call<GetResultResponse> call, Response<GetResultResponse> response) {
                    List<Result> results = response.body().getResults();// list result for 1 user and 1 topic
                    if(results!=null){
                        for (Result result : results) {
                            if (result.getPass()) levelPassed[0]++;
                        }
                        if (levelPassed[0] > 0){
                            Log.d(TAG, "onResponse: final Id topic"+finalId_topic);
                            chang_background(finalId_topic);
                        }
                        Log.d(TAG, "onResponse: result for  " + finalId_topic + "-" + results.size());
                    }

                }

                @Override
                public void onFailure(Call<GetResultResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }
    public void getLevelPassed(int id_topic, final TextView textView) {
        String user_id = SharePrefManager.getInstance(mContext).getUser().getId();
        numberlevelPassed = 0;
        String topic_id = database.getTopicById(id_topic).get_id();
        Log.d(TAG, "getResult: topic_id = " + topic_id);
        Call<GetResultResponse> call = RetrofitClient.getInstance().getApi().getResultByUserTopic(user_id, topic_id);
        call.enqueue(new Callback<GetResultResponse>() {
            @Override
            public void onResponse(Call<GetResultResponse> call, Response<GetResultResponse> response) {
                List<Result> results = response.body().getResults();// list result for 1 user and 1 topic
                for (Result result : results) {
                    if (result.getPass()) numberlevelPassed++;
                }
                if(numberlevelPassed>0) textView.setText(""+numberlevelPassed+"/3");
            }

            @Override
            public void onFailure(Call<GetResultResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
//            return numberlevelPassed;
    }
    private void chang_background(int finalId_topic) {
        switch (finalId_topic) {
            case 1:
                ImageView img = view.findViewById(R.id.img_tp1);
                img.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 2:
                ImageView img2 = view.findViewById(R.id.img_tp2);
                img2.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 3:
                ImageView img3 = view.findViewById(R.id.img_tp3);
                img3.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 4:
                ImageView img4 = view.findViewById(R.id.img_tp4);
                img4.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 5:
                ImageView img5 = view.findViewById(R.id.img_tp5);
                img5.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 6:
                ImageView img6 = view.findViewById(R.id.img_tp6);
                img6.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 7:
                ImageView img7 = view.findViewById(R.id.img_tp7);
                img7.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 8:
                ImageView img8 = view.findViewById(R.id.img_tp8);
                img8.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 9:
                ImageView img9 = view.findViewById(R.id.img_tp9);
                img9.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 10:
                ImageView img10 = view.findViewById(R.id.img_tp10);
                img10.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 11:
                ImageView img11 = view.findViewById(R.id.img_tp11);
                img11.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 12:
                ImageView img12 = view.findViewById(R.id.img_tp12);
                img12.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 13:
                ImageView img13 = view.findViewById(R.id.img_tp13);
                img13.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 14:
                ImageView img14 = view.findViewById(R.id.img_tp14);
                img14.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 15:
                ImageView img15 = view.findViewById(R.id.img_tp15);
                img15.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 16:
                ImageView img16 = view.findViewById(R.id.img_tp16);
                img16.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 17:
                ImageView img17 = view.findViewById(R.id.img_tp17);
                img17.setBackgroundResource(R.drawable.round_image_pass);
                break;
            case 18:
                ImageView img18 = view.findViewById(R.id.img_tp18);
                img18.setBackgroundResource(R.drawable.round_image_pass);
        }
    }

}
