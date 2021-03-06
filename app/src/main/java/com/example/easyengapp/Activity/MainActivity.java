package com.example.easyengapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyengapp.Database.MyDatabase;
import com.example.easyengapp.Database.TranferDatabase;
import com.example.easyengapp.Fragment.DictionaryFragment;
import com.example.easyengapp.Fragment.PracticeFragment;
import com.example.easyengapp.Fragment.ProfileFragment;
import com.example.easyengapp.Model.GetResultResponse;
import com.example.easyengapp.Model.Result;
import com.example.easyengapp.Model.Topic;
import com.example.easyengapp.Model.TopicByIdResponse;
import com.example.easyengapp.R;
import com.example.easyengapp.Retrofit.RetrofitClient;
import com.example.easyengapp.storage.SharePrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private boolean isFirstTime;
    private SharePrefManager prefManager;
    private TranferDatabase tranferDatabase;
    private MyDatabase database;
    private String TAG = getClass().getSimpleName();
    int numberlevelPassed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PracticeFragment()).commit();
        //
        prefManager = SharePrefManager.getInstance(this);
        isFirstTime = prefManager.getFistTime();
        database = new MyDatabase(this);
        Toast.makeText(this, "Connect internet: " + checkInternet(), Toast.LENGTH_SHORT).show();
        Log.d("Main", "onCreate: " + (database == null));
        initDatabase();

    }

    private void initDatabase() {
        if (isFirstTime) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage(getString(R.string.init_db));
            dialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    tranferDatabase = new TranferDatabase(getApplicationContext(), true);
                    dialog.dismiss();
                    prefManager.saveIsFirstTime(false);
                }
            }).start();
        } else {
            tranferDatabase = new TranferDatabase(this, false);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new PracticeFragment();
                            break;
                        case R.id.navigation_dictionary:
                            selectedFragment = new DictionaryFragment();
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
                    return true;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_search:
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            case R.id.App_info:
                Toast.makeText(MainActivity.this, "Thông tin ứng dụng", Toast.LENGTH_LONG).show();
                break;
            case R.id.App_setup:
                Toast.makeText(MainActivity.this, "Cài đặt ứng dụng", Toast.LENGTH_LONG).show();
                break;
            case R.id.App_feedback:
                Toast.makeText(MainActivity.this, "Báo cáo ứng dụng", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
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
        getLevelPassed(id, tv_passRate);
        final int topicId = id;
        btn_dang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PracticeActivity.class);
                intent.putExtra("IdTopic", topicId);
                startActivity(intent);
            }
        });
        btn_dang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Practice2Activity.class);
                intent.putExtra("IdTopic", topicId);
                startActivity(intent);
            }
        });
        btn_dang3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Practice3Activity.class);
                intent.putExtra("IdTopic", topicId);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    public void getLevelPassed(int id_topic, final TextView textView) {
        String user_id = SharePrefManager.getInstance(this).getUser().getId();
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
                if (numberlevelPassed > 0) textView.setText("" + numberlevelPassed + "/3");
            }

            @Override
            public void onFailure(Call<GetResultResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
    public boolean checkInternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else
            connected = false;
        return connected;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAffinity(MainActivity.this);
    }
}
