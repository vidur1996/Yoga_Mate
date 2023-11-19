package com.example.yogamate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yogamate.adapter.CourseAdapter;
import com.example.yogamate.model.Course;

public class ViewCourseActivity extends AppCompatActivity implements CourseAdapter.onClickCourseAdapter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
    }

    @Override
    public void onEditClick(Course acceptUser, int index) {

    }

    @Override
    public void onDelClick(Course declineUser, int index) {

    }
}