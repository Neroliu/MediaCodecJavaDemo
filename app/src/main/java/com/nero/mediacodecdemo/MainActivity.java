package com.nero.mediacodecdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileDescriptor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    public static final int REQUEST_STORAGE = 200;
    public static final int REQUEST_VIDEO_FILE = 201;
    public static final String MIME_TYPE_VIDEO = "video/*";
    private Button bt_selectVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initData();
    }


    private void initView() {
        bt_selectVideo = findViewById(R.id.bt_select_video);
    }

    private void initEvent() {
        bt_selectVideo.setOnClickListener(this);
    }

    private void initData() {
        //todo no data need to initialize
    }


    @Override
    public void onClick(View v) {
        if (v == bt_selectVideo) {
            clickSelectVideo();
        }
    }

    private void clickSelectVideo() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
            return;
        }
        Uri fileUri = chooseFile();
        if (fileUri == null) {
            Toast.makeText(this, R.string.select_video_failed, Toast.LENGTH_SHORT).show();
            return;
        }
        try (ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(fileUri, "r")){

        } catch (Exception exception) {
            Log.w(TAG, "open fd failed", exception);
            Toast.makeText(this, R.string.open_video_failed, Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private Uri chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(MIME_TYPE_VIDEO);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_video)), REQUEST_VIDEO_FILE);
        return null;
    }


}
