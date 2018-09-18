package com.example.liu04.facetest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.liu04.facetest.R;

public class FaceManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_manage);
    }

    public void clickManage(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn_register:
                intent.setClass(this,FaceAddActivity.class);
                break;
            case R.id.btn_update:
                intent.setClass(this,FaceUpdateActivity.class);
                break;
            case R.id.btn_delete:
                intent.setClass(this,FaceDeleteActivity.class);
                break;
            case R.id.btn_query:
                intent.setClass(this,FaceQueryActivity.class);
                break;
        }
        startActivity(intent);
    }
}
