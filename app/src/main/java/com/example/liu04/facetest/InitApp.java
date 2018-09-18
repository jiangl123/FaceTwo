package com.example.liu04.facetest;

import android.app.Application;
import android.util.Log;
import com.example.liu04.facetest.utils.AuthService;

/**
 * Created by jiangling on 2018/5/22.
 */

public class InitApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                FaceUtils.sToken = AuthService.getServerToken(FaceUtils.SERVER_TOKEN_URL);
                Log.e("initapp",FaceUtils.sToken);
            }
        }).start();
    }
}
