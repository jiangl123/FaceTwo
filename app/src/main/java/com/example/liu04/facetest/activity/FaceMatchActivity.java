package com.example.liu04.facetest.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liu04.facetest.FaceUtils;
import com.example.liu04.facetest.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FaceMatchActivity extends AppCompatActivity {
    //调用系统相册-选择图片
    private static final int IMAGE = 1;
    private String path1;
    private String path2;
    private TextView mResult;
    private ImageView mImage1;
    private ImageView mImage2;
    private int who = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_match);
        mImage1 = findViewById(R.id.image1);
        mImage2 = findViewById(R.id.image2);
        mResult = findViewById(R.id.textResult);
    }

    private void methor() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(FaceUtils.match(path1, path2));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mResult.setText(s);
                    }
                });
    }

    public void clickTo(View view) {
        if(TextUtils.isEmpty(path1)||TextUtils.isEmpty(path2)){
            Toast.makeText(this, "请添加图片", Toast.LENGTH_SHORT).show();
            return;
        }
        methor();
    }

    //"access_token": "24.db387e14a11880a80d4ac9b3e75b2975.2592000.1517035981.282335-10597129"
    public void onClick(View v) {
        //调用相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE);
        switch (v.getId()) {
            case R.id.image1:
                who = 1;
                break;
            case R.id.image2:
                who = 2;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        if (who == 1) {
            path1 = imaePath;
            mImage1.setImageBitmap(bm);
        } else {
            path2 = imaePath;
            mImage2.setImageBitmap(bm);
        }
    }
}
