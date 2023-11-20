package com.example.yogamate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yogamate.adapter.CsDetailInstanceAdapter;
import com.example.yogamate.model.Course;
import com.example.yogamate.model.Instance;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {

    Course cs = new Course();
    DatabaseReference databaseReference;
    TextView cdName,cdDay,cdTime,cdCapacity,cdPrice,cdType,cdRoom,cdMat,cdDesc;
    ImageView edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent intent = getIntent();
        cs = (Course) intent.getSerializableExtra("course");
        cdName =  findViewById(R.id.tv_cd_name);
        cdDay =  findViewById(R.id.tv_cd_days);
        cdTime=  findViewById(R.id.tv_cd_time);
        cdCapacity =  findViewById(R.id.tv_cd_capacity);
        cdPrice =  findViewById(R.id.tv_cd_price);
        cdType =  findViewById(R.id.tv_cd_type);
        cdRoom =  findViewById(R.id.tv_cd_room);
        cdMat=  findViewById(R.id.tv_cd_mat);
        cdDesc =  findViewById(R.id.tv_cd_desc);
        edit = findViewById(R.id.img_cd_edit);
        getInstanceList(cs.getId());
        displayDate();

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getApplicationContext(),EditCourseActivity.class);
                    in.putExtra("course", (Serializable) cs);
                    startActivity(in);
                }
            });


    }

    public void displayDate(){
        cdName.setText(cs.getClassName());
        cdDay.setText(cs.getClassDay());
        cdTime.setText(cs.getClassTime());
        cdCapacity.setText(String.valueOf(cs.getClassCapacity()));
        cdPrice.setText(String.valueOf(cs.getClassFees()));
        cdType.setText(cs.getClassType());
        cdRoom.setText(cs.getRoomNo());
        cdMat.setText(cs.getYogaMat());
        cdDesc.setText(cs.getDescription());
    }

    public void getInstanceList(int id){


        ListView listView = findViewById(R.id.lv_instances);
        List<Instance> dataList = new ArrayList<>();
        CsDetailInstanceAdapter adapter = new CsDetailInstanceAdapter(this, dataList);
        dataList.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("course").child(String.valueOf(id)).child("instance");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Instance value = snapshot.getValue(Instance.class);

                    dataList.add(value);
                    adapter.notifyDataSetChanged();
                } else {


                }


            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        listView.setAdapter(adapter);
    }
}