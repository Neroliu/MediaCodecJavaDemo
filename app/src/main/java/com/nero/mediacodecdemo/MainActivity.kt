package com.nero.mediacodecdemo

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

import java.io.FileDescriptor

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var bt_selectVideo: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initEvent()
        initData()
    }


    private fun initView() {
        bt_selectVideo = findViewById(R.id.bt_select_video)
    }

    private fun initEvent() {
        bt_selectVideo!!.setOnClickListener(this)
    }

    private fun initData() {
        //todo no data need to initialize
    }


    override fun onClick(v: View) {
        if (v === bt_selectVideo) {
            clickSelectVideo()
        }
    }

    private fun clickSelectVideo() {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_STORAGE)
            return
        }
        val fileUri = chooseFile()
        if (fileUri == null) {
            Toast.makeText(this, R.string.select_video_failed, Toast.LENGTH_SHORT).show()
            return
        }
        try {
            contentResolver.openFileDescriptor(fileUri, "r")!!.use {

            }
        } catch (exception: Exception) {
            Log.w(TAG, "open fd failed", exception)
            Toast.makeText(this, R.string.open_video_failed, Toast.LENGTH_SHORT).show()
            return
        }

    }

    private fun chooseFile(): Uri? {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = MIME_TYPE_VIDEO
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_video)), REQUEST_VIDEO_FILE)
        return null
    }

    companion object {


        private val TAG = "MainActivity"
        val REQUEST_STORAGE = 200
        val REQUEST_VIDEO_FILE = 201
        val MIME_TYPE_VIDEO = "video/*"
    }


}
