package com.example.yogamate.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogamate.R;
import com.example.yogamate.model.Course;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final ArrayList<Course> courseset;
    onClickCourseAdapter callback;

    public CourseAdapter(ArrayList<Course> arrayList) {
        //this.userSet = userSet;
        courseset = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_courses, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Course myListData = courseset.get(position);
        holder.tv_name.setText(myListData.getClassName());
        holder.tv_days.setText(myListData.getClassDay());
        holder.tv_type.setText(myListData.getClassType());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDelClick(myListData, position);
            }
        });
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDetailsClick(myListData, position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return courseset.size();
    }

    public void onClickCourseAdapter(onClickCourseAdapter callback) {
        this.callback = callback;
    }

    public interface onClickCourseAdapter {
        void onDetailsClick(Course editcourse, int index);

        void onDelClick(Course delcourse, int index);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_days;
        TextView tv_type;
        ImageView delete;
        ConstraintLayout details;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_course_name);
            tv_days = (TextView) itemView.findViewById(R.id.tv_course_day);
            tv_type = (TextView) itemView.findViewById(R.id.tv_course_type);
            delete = (ImageView) itemView.findViewById(R.id.img_cs_delete);
            details = (ConstraintLayout) itemView.findViewById(R.id.layout_course);

        }
    }
}