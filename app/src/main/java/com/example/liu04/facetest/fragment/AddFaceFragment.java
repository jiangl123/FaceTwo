package com.example.liu04.facetest.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liu04.facetest.FaceUtils;
import com.example.liu04.facetest.MainActivity;
import com.example.liu04.facetest.R;
import com.example.liu04.facetest.utils.BitmapUtil;
import com.example.liu04.facetest.utils.Config;
import com.example.liu04.facetest.youtu.Youtu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jiangling on 2018/5/17.
 */

public class AddFaceFragment extends Fragment implements View.OnClickListener {

    //调用系统相册-选择图片
    private static final int IMAGE = 1;
    private TextView tv_title;
    private ImageView iv_face;
    private Button btn_add;
    private Bitmap bmp_face;
    private ProgressDialog dialog;
    private EditText et_userId;
    private EditText et_userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add, null);
        et_userId = view.findViewById(R.id.et_frag_userId);
        et_userInfo = view.findViewById(R.id.et_frag_userInfo);
        tv_title = view.findViewById(R.id.tv_frag_title);
        iv_face = view.findViewById(R.id.iv_fragment_face);
        btn_add = view.findViewById(R.id.btn_fragment_addface);
        iv_face.setOnClickListener(this);
        btn_add.setOnClickListener(this);
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
            case R.id.iv_fragment_face:
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                break;
            case R.id.btn_fragment_addface:
                if (bmp_face!=null){
                    if (checkEt()){
                        if (et_userId.getText().equals("qtdsswk")){
                            Intent intent1 = new Intent(getContext(), MainActivity.class);
                            startActivity(intent1);
                        }else{
                            dialog.show();
                            methor(et_userId.getText().toString(),et_userInfo.getText().toString());
                        }
                    }
                }else{
                    Toast.makeText(getContext(),"点击图片区域选择图片",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public boolean checkEt(){
        if (TextUtils.isEmpty(et_userId.getText())||TextUtils.isEmpty(et_userInfo.getText())){
            Toast.makeText(getContext(),"UserId和UserInfo不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //百度人脸注册
    private void methor(final String userId, final String user_info) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(FaceUtils.rzlAddFace(bmp_face,userId, user_info));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("FaceAddActivity","main Thread name :"+Thread.currentThread().getName().toString()+"\n baidu addface response :"+s);
                        regist(bmp_face,userId);
                    }
                });
    }

    //优图人脸注册
    private void regist(final Bitmap bmp,final String userid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        Youtu faceYoutu = new Youtu(Config.AppID, Config.SecretID, Config.SecretKey, Youtu.API_YOUTU_END_POINT);
                        List<String> list = new ArrayList<>();
                        list.add(Config.GROUP_ID);
                        final JSONObject respose = faceYoutu.NewPerson(bmp,userid,list);
                        Log.e("DetectResult", respose.toString());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (respose!=null){
                                    try {
                                        dialog.dismiss();
                                        String errormsg = respose.getString("errormsg");
                                        if (!TextUtils.isEmpty(errormsg)){
                                            if (errormsg.toUpperCase().equals("OK")){
                                                Toast.makeText(getContext(),"人脸录入成功",Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(getContext(), errormsg+" 02", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(getContext(), "人脸录入失败", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
            String []strs = imagePath.split("/");
            String imageName = "";
            if (strs.length>0){
               imageName = strs[strs.length-1];
            }
            if (imageName.endsWith(".jpg")||imageName.endsWith(".jpeg")||imageName.endsWith(".png")||imageName.endsWith(".JPG")||imageName.endsWith(".JPEG")||imageName.endsWith(".PNG")){
                int index = imageName.indexOf(".");
                if (index>0){
                    String name = imageName.substring(0,index);
                    String []parames = name.split("_");
                    if (parames.length>1){
                        if (!TextUtils.isEmpty(parames[0])){
                            et_userId.setText(parames[0]);
                        }
                        if (!TextUtils.isEmpty(parames[1])){
                            et_userInfo.setText(parames[1]);
                        }
                    }
                }
            }
        }
    }

    //加载图片
    private void showImage(String imagePath) {
        bmp_face = BitmapUtil.getBitmap(imagePath,1000,1000);
        iv_face.setImageBitmap(bmp_face);
    }
}
