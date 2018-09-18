package com.example.liu04.facetest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.liu04.facetest.FaceUtils;
import com.example.liu04.facetest.QueryAdapter;
import com.example.liu04.facetest.R;
import com.example.liu04.facetest.UserBean;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FaceQueryActivity extends AppCompatActivity {

    private ListView mListView;
    private QueryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_query);
        mListView = findViewById(R.id.listView);
        methor();
    }

    private void methor() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(FaceUtils.getUsers());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Gson gson = new Gson();
                        UserBean userBean = gson.fromJson(s, UserBean.class);
                        mAdapter = new QueryAdapter(userBean.getResult(), FaceQueryActivity.this);
                        mListView.setAdapter(mAdapter);
                    }
                });
    }
}
