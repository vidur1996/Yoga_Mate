package com.example.yogamate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yogamate.AddInstanceActivity;
import com.example.yogamate.R;
import com.example.yogamate.adapter.InstanceAdapter;
import com.example.yogamate.model.Instance;

public class InstancesActivity extends AppCompatActivity implements InstanceAdapter.onClickConductorAdapter {
    ImageView img_add_inst;
    String courseId,courseName,dayOfweek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instances);
        img_add_inst = findViewById(R.id.img_add_instance);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseId = extras.getString("course_id");
            courseName = extras.getString("course_name");
            dayOfweek = extras.getString("day");
        }

        img_add_inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), AddInstanceActivity.class);
                it.putExtra("course_name",courseName);
                it.putExtra("course_id",courseId);
                it.putExtra("day",dayOfweek);
                startActivity(it);
            }
        });
    }

    @Override
    public void onAcceptClick(Instance acceptUser, int index) {

    }

    @Override
    public void onDeclineClick(Instance declineUser, int index) {

    }
}