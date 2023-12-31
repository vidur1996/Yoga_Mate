package com.example.yogamate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yogamate.model.Course;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class AddCourseActivity extends AppCompatActivity {
    boolean[] courseDays = new boolean[7];
    MaterialButtonToggleGroup toggleGroup ;
    Button btn_save;
    int id;
    Spinner yTypes;
 RadioGroup radioGroup;
 RadioButton radioButtonYes, radioButtonNo;
    String buttonText  = "", mat;
    EditText cName,cTime,cCapacity,cPrice,cRoom,cDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        toggleGroup = findViewById(R.id.tg_btn_grp_days);
        btn_save = findViewById(R.id.btn_save_course);
        cName    = findViewById(R.id.et_c_name);
        cTime    = findViewById(R.id.et_c_time);
        cCapacity= findViewById(R.id.et_c_capacity);
        cPrice   = findViewById(R.id.et_c_price);
        cRoom    = findViewById(R.id.et_c_room);
        cDesc    = findViewById(R.id.et_c_desc);
        toggleGroup.setSingleSelection(true);
        yTypes = findViewById(R.id.spin_c_type);
        radioGroup = findViewById(R.id.rg_mat);
        radioButtonYes = findViewById(R.id.rb_mat_yes);
        radioButtonNo = findViewById(R.id.rb_mat_no);
        Course cs = new Course();
        setCsId();
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {


                    if (checkedId ==R.id.cbtn_mon){
                        buttonText = "Monday";
                    }
                    else if (checkedId ==R.id.cbtn_tue){
                        buttonText = "Tuesday";
                    }
                    else if (checkedId ==R.id.cbtn_wed){
                        buttonText = "Wednesday";
                    }
                    else if (checkedId ==R.id.cbtn_thu){
                        buttonText = "Thursday";
                    }
                    else if (checkedId ==R.id.cbtn_fri){
                        buttonText = "Friday";
                    }
                    else if (checkedId ==R.id.cbtn_sat){
                        buttonText = "Saturday";
                    }
                    else if (checkedId ==R.id.cbtn_sun){
                            buttonText = "Sunday";
                    }
                    else {
                            buttonText = "select a day";
                    }



                    Toast.makeText(AddCourseActivity.this, "Selected day: " + buttonText, Toast.LENGTH_SHORT).show();
                }
            }
        });
        cTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });
        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(AddCourseActivity.this
                , android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.yoga_types));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       yTypes.setAdapter(myadapter);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button was clicked
                if (checkedId == R.id.rb_mat_yes) {
                    // "Yes" is selected
                    mat = "Mat will be provided";
                } else if (checkedId == R.id.rb_mat_no) {
                    // "No" is selected
                    mat ="Bring your own mat";
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (cName.getText().toString().trim().equals("")){
                        showAlert("Error","Please enter name for the course");
                    }
                    else if(cTime.getText().toString().trim().equals("")){
                        showAlert("Error","Please enter Time for the course");
                    }
                    else if(cCapacity.getText().toString().trim().equals("")){
                        showAlert("Error","Please enter Capacity for the course");
                    }
                    else if(cPrice.getText().toString().trim().equals("")){
                        showAlert("Error","Please enter Fees for the course");
                    }
                    else if(cRoom.getText().toString().trim().equals("")){
                        showAlert("Error","Please enter Room Number for the course");
                    }
                    else if(yTypes.getSelectedItem().toString().trim().equals("")){
                        showAlert("Error","Please course type for the course");
                    }
                    else if(buttonText.equals("select a day")){
                        showAlert("Error","Please course day for the course");
                    }
                    else if(mat.equals("")){
                        showAlert("Error","Please select preference for Yoga mat");
                    }
                    else {
                        cs.setId(id);
                        cs.setClassName(cName.getText().toString().trim());
                        cs.setTimeOfDay(cTime.getText().toString().trim());
                        cs.setClassCapacity(Integer.parseInt(cCapacity.getText().toString().trim()));
                        cs.setClassFees(Double.parseDouble(cPrice.getText().toString().trim()));
                        cs.setRoomNo(cRoom.getText().toString().trim());
                        cs.setDescription(cDesc.getText().toString().trim());
                        cs.setClassType(yTypes.getSelectedItem().toString().trim());
                        cs.setDayOfWeek(buttonText.trim());
                        cs.setYogaMat(mat);
                        saveData(cs);

                    }

            }
        });






    }

    private void saveData(Course cs){

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();


        DatabaseReference.CompletionListener complete = new DatabaseReference.CompletionListener() {
            @Override

            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError != null)
                {
                    showAlert("Error","NETWORK ERROR");
                }
                else
                {
                    updateId(cs);
                }
            }
        };
        reff.child("course").child(String.valueOf(cs.getId())).setValue(cs ,complete);


    }

    private void showTimePicker() {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(30)
                .setTitleText("Select Class time")
                .build();

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected hour and minute
                int hour = picker.getHour();
                int minute = picker.getMinute();
                String amPm;
                if (hour >= 12) {
                    amPm = "PM";
                    if (hour > 12) {
                        hour -= 12;
                    }
                } else {
                    amPm = "AM";
                    if (hour == 0) {
                        hour = 12;
                    }
                }

                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hour, minute, amPm);

                cTime.setText(formattedTime);
            }
        });

        picker.show(getSupportFragmentManager(), "tag"); // Use getSupportFragmentManager() for AppCompatActivity
    }

    public void showAlert(String title,String msg) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).show();
    }

    public void showAlertToMove(Course cs) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        new MaterialAlertDialogBuilder(this)
                .setTitle(" successFul")
                .setMessage("Data saved successfully, now lets add instance  ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent it = new Intent(getApplicationContext(), InstancesActivity.class);
                        it.putExtra("course_name", cs.getClassName());
                        it.putExtra("course_id", String.valueOf(cs.getId()));
                            it.putExtra("day", cs.getDayOfWeek());
                        it.putExtra("title","Add Instance");
                        startActivity(it);

                    }
                }).show();

    }

    public void setCsId() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id = Integer.parseInt(dataSnapshot.child("CourseId").getValue().toString()) + 1;
                Log.e("xxxxxx", dataSnapshot.child("CourseId").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("xxxx", "loadPost:onCancelled", databaseError.toException());
            }
        };
        reff.addValueEventListener(postListener);

    }

    public void updateId(Course cs) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
        DatabaseReference.CompletionListener complete = new DatabaseReference.CompletionListener() {
            @Override

            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    showAlert("Error", "NETWORK ERROR");
                } else {
                    showAlertToMove(cs);
                }
            }
        };
        reff.child("CourseId").setValue(String.valueOf(cs.getId()), complete);


    }
}