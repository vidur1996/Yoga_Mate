package com.example.yogamate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.yogamate.model.Course;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class EditCourseActivity extends AppCompatActivity {
    MaterialButtonToggleGroup toggleGroup ;
    Button btn_update,btn_in_update;
    Spinner yTypes;

    String buttonText  = "";
    EditText cName,cTime,cCapacity,cPrice,cRoom,cDesc;
    Course cs = new Course();

    RadioGroup radioGroup;
    RadioButton radioButtonYes, radioButtonNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        toggleGroup = findViewById(R.id.tg_ebtn_grp_days);
        btn_update = findViewById(R.id.btn_update_course);
        btn_in_update = findViewById(R.id.btn_edit_instance);
        cName    = findViewById(R.id.et_ec_name);
        cTime    = findViewById(R.id.et_ec_time);
        cCapacity= findViewById(R.id.et_ec_capacity);
        cPrice   = findViewById(R.id.et_ec_price);
        cRoom    = findViewById(R.id.et_ec_room);
        cDesc    = findViewById(R.id.et_ec_desc);
        radioGroup = findViewById(R.id.rg_emat);
        radioButtonYes = findViewById(R.id.rb_emat_yes);
        radioButtonNo = findViewById(R.id.rb_emat_no);
        toggleGroup.setSingleSelection(true);
        yTypes = findViewById(R.id.spin_ec_type);
        Intent intent = getIntent();
        cs = (Course) intent.getSerializableExtra("course");
        displayData();

        btn_update.setOnClickListener(new View.OnClickListener() {
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
                else {
                    cs.setClassName(cName.getText().toString().trim());
                    cs.setClassTime(cTime.getText().toString().trim());
                    cs.setClassCapacity(Integer.parseInt(cCapacity.getText().toString().trim()));
                    cs.setClassFees(Double.parseDouble(cPrice.getText().toString().trim()));
                    cs.setRoomNo(cRoom.getText().toString().trim());
                    cs.setDescription(cDesc.getText().toString().trim());
                    cs.setClassType(yTypes.getSelectedItem().toString().trim());
                    cs.setClassDay(buttonText.trim());
                    saveData(cs);
                    // boolean responce =  saveData(cs);

                    //  if (responce){
                    //      showAlert("Error","NETWORK ERROR");
                    //  }
                    //  else {
                    //      showAlert("Succesful","Data saved successfully !");
                    //   }
                }

            }
        });

        btn_in_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), InstancesActivity.class);
                in.putExtra("course_id",String.valueOf(cs.getId()));
                in.putExtra("course_name",cs.getClassName());
                in.putExtra("day",cs.getClassDay());
                in.putExtra("title","Update Instance");
                startActivity(in);
            }
        });
        cTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

    }
    public void displayData(){
        cName.setText(cs.getClassName());
        setDayButton(cs.getClassDay());
        cTime.setText(cs.getClassTime());
        cCapacity.setText(String.valueOf(cs.getClassCapacity()));
        cPrice.setText(String.valueOf(cs.getClassFees()));
        setSpinnerSelected(cs.getClassType());
        cRoom.setText(cs.getRoomNo());
        cDesc.setText(cs.getDescription());
        if(cs.getYogaMat().equals("Mat will be provided")){
            radioButtonYes.setChecked(true);
            radioButtonNo.setChecked(false);
        }
        else{
            radioButtonYes.setChecked(false);
            radioButtonNo.setChecked(true);
        }

    }

    public void setDayButton(String day){
        if(day.equals( "Monday")){
                toggleGroup.check(R.id.ecbtn_mon);
            }
        if(day.equals( "Tuesday")){
        toggleGroup.check(R.id.ecbtn_tue);
            }
        if(day.equals( "Tuesday")){
            toggleGroup.check(R.id.ecbtn_tue);
            }
        if(day.equals( "Wednesday")){
            toggleGroup.check(R.id.ecbtn_wed);
            }
        if(day.equals( "Thursday")){
            toggleGroup.check(R.id.ecbtn_thu);
            }
        if(day.equals( "Friday")){
            toggleGroup.check(R.id.ecbtn_fri);
            }
        if(day.equals( "Saturday")){
            toggleGroup.check(R.id.ecbtn_sat);
            }
        else {
            toggleGroup.check(R.id.ecbtn_sun);
            }

    }

    public void setSpinnerSelected(String type){
        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(EditCourseActivity.this
                , android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.yoga_types));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yTypes.setAdapter(myadapter);
        if (type.equals("Flow Yoga")){
            yTypes.setSelection(0);
        }
        else if (type.equals("Aerial Yoga")){
            yTypes.setSelection(1);
        }
        else{
            yTypes.setSelection(2);
        }

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
                    showAlertToMove();
                }
            }
        };
        reff.child("course").child(String.valueOf( cs.getId())).setValue(cs ,complete);


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

    public void showAlertToMove() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        new MaterialAlertDialogBuilder(this)
                .setTitle(" successFul")
                .setMessage("Data saved successfully,  /n now lets add instance  ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                       finish();

                    }
                }).show();

    }
}