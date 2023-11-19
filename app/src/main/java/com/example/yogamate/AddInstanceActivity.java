package com.example.yogamate;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yogamate.model.Instance;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddInstanceActivity extends AppCompatActivity {

    String courseId,courseName,dayOfweek;
    EditText inDate,teaName, inDesc;
    int id;
    Button saveInstance;
    TextView csName;
    Instance in = new Instance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instance);
        inDate = findViewById(R.id.et_in_date);
        inDesc = findViewById(R.id.et_in_desc);
        teaName  = findViewById(R.id.et_in_tname);
        csName = findViewById(R.id.tv_in_name);
        saveInstance = findViewById(R.id.btn_in_save);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseId = extras.getString("course_id");
            courseName = extras.getString("course_name");
            dayOfweek = extras.getString("day");
        }
        csName.setText(courseName);
        setInId();

        inDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaterialDatePicker(dayOfWeek(dayOfweek));
            }
        });

        saveInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inDate.getText().toString().trim().equals("")){
                    showAlert("Error","Please select a valid date");
                }
                else if (teaName.getText().toString().trim().equals("")) {
                    showAlert("Error","Please enter a teacher name");
                }
                else{
                    // save instave
                    in.setClassId(courseId);
                    in.setDate(inDate.getText().toString().trim());
                    in.setInstanceId(id);
                    in.setTeacher(teaName.getText().toString().trim());
                    in.setDescription(inDesc.getText().toString().trim());
                    saveData(in);

                }
            }
        });

    }

    private void saveData( Instance in){
        final String[] id = new String[1];
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
                    updateId(in.getInstanceId());
                }
            }
        };
        reff.child("course").child(courseId).child("instance").child(String.valueOf(in.getInstanceId())).setValue(in, complete);


    }

    private void showMaterialDatePicker(int dayofWeek) {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

        // Set a validator to allow only Mondays
        constraintsBuilder.setValidator(new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                // Convert the date to a Calendar object
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(date);

                // Check if the day of the week is Monday
                return calendar.get(Calendar.DAY_OF_WEEK) == dayofWeek;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(@NonNull Parcel parcel, int i) {

            }
        });

        // Set the minimum date to the current time to disable past dates
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        constraintsBuilder.setStart(today);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a Date")
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(today) // Set the initial selection to today
                .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                // Convert the selected timestamp to a Calendar object
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.setTimeInMillis(selection);

                // Check if the selected date is in the past
                if (selection < today) {
                    // Show an alert asking the user to select a future date
                    showAlert("Date Invalid","Please select future dates");
                } else {
                    // Update the value in the EditText called DATEET
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(selectedCalendar.getTime());
                    inDate.setText(formattedDate);
                }
            }
        });

        datePicker.show(getSupportFragmentManager(), "Date Picker");
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

    public int dayOfWeek(String day){
        if(day.equals("Monday")){
            return Calendar.MONDAY;
        }
        else if (day.equals("Tuesday")){
                return Calendar.TUESDAY;
        }
        else if (day.equals("Wednesday")){
            return Calendar.WEDNESDAY;
        }
        else if (day.equals("Friday")){
            return Calendar.FRIDAY;
        }
        else if (day.equals("Saturday")){
            return Calendar.SATURDAY;
        }
        else if (day.equals("Sunday")){
            return Calendar.SUNDAY;
        }
        else {
            return Calendar.MONDAY;
        }
    }

    public void showAlertToMove() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        new MaterialAlertDialogBuilder(this)
                .setTitle(" successFul")
                .setMessage("Data saved successfully !  ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        AddInstanceActivity.this.finish();

                    }
                }).show();

    }

    public void setInId() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id = Integer.parseInt(dataSnapshot.child("InstanceId").getValue().toString()) + 1;
                Log.e("xxxxxx", dataSnapshot.child("InstanceId").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("xxxx", "loadPost:onCancelled", databaseError.toException());
            }
        };
        reff.addValueEventListener(postListener);

    }

    public void updateId(int id) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
        DatabaseReference.CompletionListener complete = new DatabaseReference.CompletionListener() {
            @Override

            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    showAlert("Error", "NETWORK ERROR");
                } else {
                    showAlertToMove();
                }
            }
        };
        reff.child("InstanceId").setValue(String.valueOf(id), complete);


    }


}
