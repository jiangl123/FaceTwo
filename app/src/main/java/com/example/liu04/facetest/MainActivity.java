package com.example.liu04.facetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.example.liu04.facetest.activity.DetectActivity;
import com.example.liu04.facetest.activity.FaceAddActivity;
import com.example.liu04.facetest.activity.FaceManageActivity;
import com.example.liu04.facetest.activity.FaceMatchActivity;
import com.example.liu04.facetest.activity.IdentifyActivity;
import com.example.liu04.facetest.utils.AuthService;

/**
 * Created by liu04 on 2017/12/28.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FaceUtils.sToken = AuthService.getAuth();
//            }
//        }).start();
    }

    public void clickToActivity(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_faceMatch:
                intent.setClass(this,FaceMatchActivity.class);
                break;
            case R.id.btn_faceManage:
                intent.setClass(this, FaceManageActivity.class);
                break;
            case R.id.btn_identify:
                intent.setClass(this, IdentifyActivity.class);
                break;
            case R.id.btn_detect:
                intent.setClass(this, DetectActivity.class);
                break;
        }
        startActivity(intent);
    }
}
