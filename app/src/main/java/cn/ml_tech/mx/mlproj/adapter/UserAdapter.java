package cn.ml_tech.mx.mlproj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.ml_tech.mx.mlproj.R;
import cn.ml_tech.mx.mlservice.DAO.UserType;

/**
 * 创建时间: 2017/6/29
 * 创建人: zhongwang
 * 功能描述:
 */

public class UserAdapter extends BaseAdapter {
    List<UserType> data;
    Context context;

    public UserAdapter(List<UserType> data, Context context) {
        this.data = data;
        this.context = context;
    }

    private UserAdapter() {
    }

    public void setDataToView(List<UserType> value) {
        data.clear();
        data.addAll(value);
        notifyDataSetChanged();
    }

    public UserAdapter(List<UserType> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, null);
            textView = (TextView) convertView.findViewById(R.id.tvFactoryName);
            convertView.setTag(textView);
        } else {
            textView = (TextView) convertView.getTag();
        }
        textView.setText(data.get(position).getTypeName());
        return convertView;
    }
}