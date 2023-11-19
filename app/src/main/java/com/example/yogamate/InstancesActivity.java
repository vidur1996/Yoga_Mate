package com.example.yogamate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.example.yogamate.AddInstanceActivity;
import com.example.yogamate.R;
import com.example.yogamate.adapter.InstanceAdapter;
import com.example.yogamate.model.Instance;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class InstancesActivity extends AppCompatActivity implements InstanceAdapter.onClickInstanceAdapter {
    ImageView img_add_inst;

    DatabaseReference databaseReference;
    ArrayList<Instance> list = new ArrayList<Instance>();
    InstanceAdapter adapter;
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
        SetRecycler();


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


    public void SetRecycler(){
            list.clear();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("course").child(courseName).child("instance");
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                    if (snapshot.exists()) {
                        Instance value = snapshot.getValue(Instance.class);

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

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_instance);
            adapter = new InstanceAdapter(list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            adapter.onClickInstanceAdapter(this);
        }


    @Override
    public void onAcceptClick(Instance editIn, int index) {
        Intent it = new Intent(getApplicationContext(), EditInstancesActivity.class);
        it.putExtra("course_name",courseName);
        it.putExtra("course_id",courseId);
        it.putExtra("day",dayOfweek);
        it.putExtra("instance", (Serializable) editIn);
        startActivity(it);

    }

    @Override
    public void onDeclineClick(Instance deleteIn, int index) {
        new MaterialAlertDialogBuilder(InstancesActivity.this)
                .setTitle("Alert")
                .setMessage("Are you sure you want to drop course on " + deleteIn.getDate())
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

                        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            }
                        };

                        reff.child("course").child(courseName).child("instance").child(String.valueOf(deleteIn.getInstanceId())).removeValue();
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