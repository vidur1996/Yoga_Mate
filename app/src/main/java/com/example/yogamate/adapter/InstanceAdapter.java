package com.example.yogamate.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yogamate.R;
import com.example.yogamate.model.Course;
import com.example.yogamate.model.Instance;

import java.util.ArrayList;

public class InstanceAdapter extends RecyclerView.Adapter<InstanceAdapter.ViewHolder> {

    private final ArrayList<Instance> inset;
    onClickInstanceAdapter callback;

    public InstanceAdapter(ArrayList<Instance> arrayList) {
        //this.userSet = userSet;
        inset = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_instances, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Instance myListData = inset.get(position);
        holder.tv_teacher.setText(myListData.getTeacher());
        holder.tv_days.setText(myListData.getDate());
        holder.tv_desc.setText(myListData.getDescription());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onAcceptClick(myListData, position);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDeclineClick(myListData, position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return inset.size();
    }

    public void onClickInstanceAdapter(onClickInstanceAdapter callback) {
        this.callback = callback;
    }

    public interface onClickInstanceAdapter {
        void onAcceptClick(Instance editIn, int index);

        void onDeclineClick(Instance deleteIn, int index);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_teacher;
        TextView tv_days;
        TextView tv_desc;
        ImageView delete;
        ImageView edit;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_teacher = itemView.findViewById(R.id.tv_in_teacher);
            tv_days = itemView.findViewById(R.id.tv_in_date);
            tv_desc = itemView.findViewById(R.id.tv_in_desc);
            delete = itemView.findViewById(R.id.img_in_delete);
            edit = itemView.findViewById(R.id.img_in_edit);

        }
    }
}