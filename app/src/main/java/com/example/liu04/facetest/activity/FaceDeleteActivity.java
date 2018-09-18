package com.example.liu04.facetest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liu04.facetest.FaceUtils;
import com.example.liu04.facetest.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FaceDeleteActivity extends AppCompatActivity {

    private EditText mDelete_uid;
    private TextView mText_result;
    private String mUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_delete);
        mDelete_uid = findViewById(R.id.delete_uid);
        mText_result = findViewById(R.id.text_result);
    }

    public void clickDelete(View view) {
        mUid = mDelete_uid.getText().toString();
        methor();
    }

    private void methor() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(FaceUtils.delete(mUid));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mText_result.setText(s);
                    }
                });
    }
}
