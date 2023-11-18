package com.example.yogamate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yogamate.adapter.CourseAdapter;
import com.example.yogamate.model.Course;

public class ViewCourseActivity extends AppCompatActivity implements CourseAdapter.onClickConductorAdapter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
    }

    @Override
    public void onAcceptClick(Course acceptUser, int index) {

    }

    @Override
    public void onDeclineClick(Course declineUser, int index) {

    }
}