package com.example.yogamate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.yogamate.adapter.CourseAdapter;
import com.example.yogamate.adapter.InstanceAdapter;
import com.example.yogamate.model.Course;
import com.example.yogamate.model.Instance;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewCourseActivity extends AppCompatActivity implements CourseAdapter.onClickCourseAdapter {

    CourseAdapter adapter;
    DatabaseReference databaseReference;
    ArrayList<Course> list = new ArrayList<Course>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        SetRecycler();
    }

    public void SetRecycler(){
        list.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("course");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Course value = snapshot.getValue(Course.class);

                    list.add(value);
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_Course);
        adapter = new CourseAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.onClickCourseAdapter(this);
    }

    @Override
    public void onDetailsClick(Course acceptUser, int index) {

    }

    @Override
    public void onDelClick(Course declineUser, int index) {
        new MaterialAlertDialogBuilder(ViewCourseActivity.this)
                .setTitle("Alert")
                .setMessage("Are you sure you want to drop course on " + declineUser.getClassName())
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

                        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            }
                        };

                        reff.child("course").child(String.valueOf(declineUser.getId())).removeValue();
                        list.remove(index);
                        adapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).show();
    }
}