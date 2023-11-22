package com.example.yogamate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {

    Button btnAddCourse, btnViewCourse,btnWebservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddCourse = findViewById(R.id.btn_add_course);
        btnViewCourse = findViewById(R.id.btn_view_course );
        btnWebservice = findViewById(R.id.btn_save_webservice);

        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCourse = new Intent(getApplicationContext(), AddCourseActivity.class);
                 startActivity(addCourse);
            }
        });

        btnViewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ViewCourseActivity.class);

                startActivity(it);
            }
        });

        btnWebservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), WebServiceActivity.class);
                startActivity(it);
            }
        });
    }


}
