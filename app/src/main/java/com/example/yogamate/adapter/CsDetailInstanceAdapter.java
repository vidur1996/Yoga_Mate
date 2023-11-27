package com.example.yogamate.adapter;


import com.example.yogamate.R;
import com.example.yogamate.model.Instance;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CsDetailInstanceAdapter extends BaseAdapter {

    private final List<Instance> dataList;
    private final Context context;

    public CsDetailInstanceAdapter(Context context, List<Instance> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_cd_instances, parent, false);
        }
        Instance data = dataList.get(position);
        TextView dateTextView = convertView.findViewById(R.id.tv_cdin_date);
        TextView descTextView = convertView.findViewById(R.id.tv_cdin_desc);
        TextView teacherTextView = convertView.findViewById(R.id.tv_cdin_teacher);
        dateTextView.setText(data.getDate());
        teacherTextView.setText(data.getTeacher());
        descTextView.setText(data.getDescription());

        return convertView;
    }
}
