package com.example.liu04.facetest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by liu04 on 2018/1/13.
 */

public class QueryAdapter extends BaseAdapter {
    List<UserBean.ResultBean> mList;
    Context mContext;

    public QueryAdapter(List<UserBean.ResultBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_user,
                parent, false);
        viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);
        UserBean.ResultBean resultBean = mList.get(position);
        viewHolder.text_uid.setText(resultBean.getUid());
        viewHolder.text_userInfo.setText(resultBean.getUser_info());
        return convertView;
    }

    class ViewHolder {
        TextView text_uid;
        TextView text_userInfo;

        public ViewHolder(View view) {
            text_uid = view.findViewById(R.id.text_uid);
            text_userInfo = view.findViewById(R.id.text_userInfo);
        }
    }
}
