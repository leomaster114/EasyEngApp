package com.example.easyengapp.Fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.easyengapp.Activity.RegisterActivity;
import com.example.easyengapp.Activity.WelcomeActiviry;
import com.example.easyengapp.Notification.AlertReceiver;
import com.example.easyengapp.R;
import com.example.easyengapp.Retrofit.RetrofitClient;
import com.example.easyengapp.Retrofit.RetrofitClient2;
import com.example.easyengapp.moldel.UpdateAvatarResponse;
import com.example.easyengapp.moldel.UpdateUserResponse;
import com.example.easyengapp.moldel.User;
import com.example.easyengapp.storage.SharePrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * fragment hồ sơ cá nhân
 */

public class ProfileFragment extends Fragment {

    Button btnChangeAvatar, btnUpdateInfor, btnRemind, btnLogout;
    ImageButton btnCamera, btnFolder;
    ImageView imgViewAvatar;
    Switch remindSwitch;
    EditText edtName, edtEmail, edtPass;
    Context mContext;
    GoogleSignInClient googleSignInClient;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        mContext = getContext();
        btnChangeAvatar = view.findViewById(R.id.btnChangeAvatar);
        btnUpdateInfor = view.findViewById(R.id.btnUpdateInfor);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnFolder = view.findViewById(R.id.btnFolder);
        btnRemind = view.findViewById(R.id.btnRemind);
        btnRemind.setText(SharePrefManager.getInstance(mContext).getRemindTime());
        remindSwitch = view.findViewById(R.id.remindSwitch);
        imgViewAvatar = view.findViewById(R.id.imgViewAvatar);
        btnLogout = view.findViewById(R.id.btnLogout);
        edtName = view.findViewById(R.id.edtName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPass = view.findViewById(R.id.edtPassword);
        //set information to editText
        User user = SharePrefManager.getInstance(mContext).getUser();
        edtName.setText(user.getFullname());
        edtEmail.setText(user.getEmail());
        edtPass.setText(user.getPassword());
        Picasso.with(mContext).load(Uri.parse(user.getAvatar())).into(imgViewAvatar);
        //logout
        googleSignInClient = RegisterActivity.signInClient;
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (googleSignInClient != null) {
                    googleSignInClient.signOut();
                }
                SharePrefManager.getInstance(mContext).clear();
                Intent intent = new Intent(mContext, WelcomeActiviry.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        btnChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAvatar();
            }
        });

        btnUpdateInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update user infor
                updateUserInfor();
            }
        });

        btnRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRemind();
            }
        });

        remindSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    btnRemind.setEnabled(true);
                    Toast.makeText(mContext, "Đã bật thông báo luyện tập.", Toast.LENGTH_SHORT).show();
                }
                else{
                    cancelAlarm();
                    Toast.makeText(mContext, "Đã tắt thông báo luyện tập.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void setRemind() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //i: hour , i1: minute
                        // Time format: HH:mm:ss
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        calendar.set(Calendar.HOUR_OF_DAY, i);
                        calendar.set(Calendar.MINUTE, i1);
                        calendar.set(Calendar.SECOND, 0);
                        btnRemind.setText(simpleDateFormat.format(calendar.getTime()));
                        SharePrefManager.getInstance(mContext).saveRemindTime(btnRemind.getText().toString());
                        startAlarm(calendar);
                        Toast.makeText(mContext, "Thông báo luyện tập đã được đặt.", Toast.LENGTH_SHORT).show();
                    }
                },
                hour,
                minute,
                true
        );
        timePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK && data != null) {
            Toast.makeText(getActivity(), "Returned image from camera", Toast.LENGTH_SHORT).show();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgViewAvatar.setImageBitmap(bitmap);
        }

        if (requestCode == REQUEST_CODE_FOLDER && resultCode == Activity.RESULT_OK && data != null) {
            Toast.makeText(getActivity(), "Returned image from storage", Toast.LENGTH_SHORT).show();
            Uri uri = data.getData(); // get data uri
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgViewAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUserInfor() {
        String userId = SharePrefManager.getInstance(mContext).getUser().getId();
        final String fullname = edtName.getText().toString().trim();
        final String password = edtPass.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        if (fullname.isEmpty()) {
            edtName.setError("Fullname is required!");
            edtName.setTextColor(Color.RED);
            edtName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Username is required!");
            edtEmail.setTextColor(Color.RED);
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPass.setError("Password is required");
            edtPass.setTextColor(Color.RED);
            edtPass.requestFocus();
            return;
        }
        if (password.length() < 8) {
            edtPass.setError("Password should be atleast 6 character");
            edtPass.setTextColor(Color.RED);
            edtPass.requestFocus();
            return;
        }

        Call<UpdateUserResponse> call = RetrofitClient.getInstance().getApi().updateUser(userId, fullname, password, email);
        call.enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                UpdateUserResponse res = response.body();
                if (res.isStatus()) {
                    Toast.makeText(mContext, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                    User updatedUser = res.getUser();
                    SharePrefManager.getInstance(mContext).saveUser(updatedUser);
                } else {
                    Toast.makeText(mContext, "Đã xảy ra lỗi!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateAvatar() {
        String userId = SharePrefManager.getInstance(mContext).getUser().getId();

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgViewAvatar.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos);
        byte[] bitmapData = bos.toByteArray();

        File filesDir = mContext.getFilesDir();
        File file = new File(filesDir, "image" + System.currentTimeMillis() + ".JPEG");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            Log.d("MSG", "--> File name: " + file.getName());
            Log.d("MSG", "--> File path: " + file.getAbsolutePath());
            Log.d("MSG", "--> File extension: " + file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")));
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.d("MSG", e.getMessage());
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("single_image", file.getName(), requestBody);

        Call<UpdateAvatarResponse> call = RetrofitClient2.getInstance().getApi().updateAvatar(userId, body);
        call.enqueue(new Callback<UpdateAvatarResponse>() {
            @Override
            public void onResponse(Call<UpdateAvatarResponse> call, Response<UpdateAvatarResponse> response) {
                UpdateAvatarResponse res = response.body();
                if (res.getError() == null) {
                    User user = SharePrefManager.getInstance(mContext).getUser();
                    Log.d("MSG", res.getLocation());
                    user.setAvatar(res.getLocation());
                    SharePrefManager.getInstance(mContext).saveUser(user);
                    // Picasso.with(mContext).load(Uri.parse(user.getAvatar())).into(imgViewAvatar);
                    Toast.makeText(getActivity(), "Cập nhật ảnh đại diện thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("MSG", res.getError());
                    Toast.makeText(getActivity(), res.getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateAvatarResponse> call, Throwable t) {
                Log.d("MSG", t.getMessage());
                Toast.makeText(mContext, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Notification/ Alarm Handler

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        btnRemind.setText("Disabled");
        btnRemind.setEnabled(false);
    }
}
