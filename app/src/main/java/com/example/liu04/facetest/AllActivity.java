package com.example.liu04.facetest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.liu04.facetest.fragment.AddFaceFragment;
import com.example.liu04.facetest.fragment.DelFaceFragment;

public class AllActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_addface;
    private TextView tv_delface;
    int tab = 0;
    private Fragment f_add = null;
    private Fragment f_del = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        initView();
        initFragmentAddFace();
    }

    private void initView() {
        tv_addface = findViewById(R.id.tv_all_addface);
        tv_delface = findViewById(R.id.tv_all_delface);
        tv_addface.setOnClickListener(this);
        tv_delface.setOnClickListener(this);
    }

    private void initFragmentAddFace(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (f_add==null){
            f_add = new AddFaceFragment();
            Bundle bundle = new Bundle();
            bundle.putCharSequence("title","录入人脸");
            f_add.setArguments(bundle);
            transaction.add(R.id.layout_container,f_add);
        }
        hideFragment(transaction);
        transaction.show(f_add);
        transaction.commit();
    }


    private void initFragmentDelFace(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (f_del==null){
            f_del = new DelFaceFragment();
            Bundle bundle = new Bundle();
            bundle.putCharSequence("title","删除人脸");
            f_del.setArguments(bundle);
            transaction.add(R.id.layout_container,f_del);
        }
        hideFragment(transaction);
        transaction.show(f_del);
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction){
        if (f_add!=null){
            transaction.hide(f_add);
        }
        if (f_del!=null){
            transaction.hide(f_del);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all_addface:
                tab = 0;
                tabChange(tab);
                initFragmentAddFace();
                break;
            case R.id.tv_all_delface:
                tab = 1;
                tabChange(tab);
                initFragmentDelFace();
                break;
            default:
                break;
        }
    }

    private void tabChange(int tab){
        if (tab==1){
            tv_addface.setTextColor(getResources().getColor(R.color.black));
            tv_addface.setBackgroundColor(getResources().getColor(R.color.white));
            tv_delface.setTextColor(getResources().getColor(R.color.white));
            tv_delface.setBackgroundColor(getResources().getColor(R.color.black));
        }else{
            tv_delface.setTextColor(getResources().getColor(R.color.black));
            tv_delface.setBackgroundColor(getResources().getColor(R.color.white));
            tv_addface.setTextColor(getResources().getColor(R.color.white));
            tv_addface.setBackgroundColor(getResources().getColor(R.color.black));
        }
    }
}
