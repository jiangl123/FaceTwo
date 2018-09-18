package com.example.liu04.facetest.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liu04.facetest.FaceUtils;
import com.example.liu04.facetest.R;
import com.example.liu04.facetest.utils.Config;
import com.example.liu04.facetest.youtu.Youtu;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jiangling on 2018/5/17.
 */

public class DelFaceFragment extends Fragment implements View.OnClickListener {
    private TextView tv_title;
    private EditText et_userid;
    private Button btn_del;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_del, null);
        tv_title = view.findViewById(R.id.tv_frag_title);
        et_userid = view.findViewById(R.id.et_frag_uerid_del);
        btn_del = view.findViewById(R.id.btn_frag_userid_del);
        btn_del.setOnClickListener(this);
        dialog = new ProgressDialog(getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            tv_title.setText(title);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_frag_userid_del:
                if (!TextUtils.isEmpty(et_userid.getText())) {
                    dialog.show();
                    methor(et_userid.getText().toString());
                } else {
                    Toast.makeText(getContext(), "UserId不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void methor(final String userid) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(FaceUtils.rzlDelete(userid));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //百度删除返回有问题，先忽略
                        Log.e("DelFragment", "BaiDu del face response :" + s);
                        delete(userid);
                    }
                });
    }

    public void delete(final String userid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Youtu faceYoutu = new Youtu(Config.AppID, Config.SecretID, Config.SecretKey, Youtu.API_YOUTU_END_POINT);
                    final JSONObject respose = faceYoutu.DelPerson(userid);
                    Log.e("DeleteResult", respose.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (respose != null) {
                                try {
                                    dialog.dismiss();
                                    int deleted = respose.getInt("deleted");
                                    if (deleted > 0) {
                                        Toast.makeText(getContext(), "人脸删除成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "此人没在人脸库中", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
