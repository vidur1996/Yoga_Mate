package com.example.yogamate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {

    Button btnAddCourse, btntest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddCourse = findViewById(R.id.btn_add_course);
        btntest = findViewById(R.id.btn_test);


        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCourse = new Intent(getApplicationContext(), AddCourseActivity.class);
                 startActivity(addCourse);
            }
        });

        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ViewCourseActivity.class);
//                it.putExtra("course_name","hello");
//                it.putExtra("course_id","11");
//                it.putExtra("day","Sunday");
                startActivity(it);
            }
        });
    }


}
