package com.example.easyengapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.easyengapp.Database.MyDatabase;
import com.example.easyengapp.Database.TranferDatabase;
import com.example.easyengapp.Fragment.DictionaryFragment;
import com.example.easyengapp.Fragment.PracticeFragment;
import com.example.easyengapp.Fragment.ProfileFagment;
import com.example.easyengapp.R;
import com.example.easyengapp.storage.SharePrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private boolean isFirstTime;
    private SharePrefManager prefManager;
    private TranferDatabase tranferDatabase;
    private MyDatabase database;
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
        Log.d("Main", "onCreate: "+(database==null));
        initDatabase();

    }

    private void initDatabase() {
        if(isFirstTime){
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage(getString(R.string.init_db));
            dialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    tranferDatabase = new TranferDatabase(getApplicationContext(),true);
                    dialog.dismiss();
                    prefManager.saveIsFirstTime(false);
                }
            }).start();
        }else{
            tranferDatabase = new TranferDatabase(this,false);
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
                        case R.id.navigation_favorite:
                            selectedFragment = new DictionaryFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new ProfileFagment();
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
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
