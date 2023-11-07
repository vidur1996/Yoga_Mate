package com.example.yogamate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCourseActivity extends AppCompatActivity {
    boolean[] courseDays = new boolean[7];
    Button btn_mon,btn_tue,btn_wed,btn_thr,btn_fri,btn_sat,btn_sun;
    EditText cName,cTime,cCapacity,cPrice,cRoom,cDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        btn_mon = findViewById(R.id.cbtn_mon);
        btn_tue = findViewById(R.id.cbtn_tue);
        btn_wed = findViewById(R.id.cbtn_wed);
        btn_thr = findViewById(R.id.cbtn_thu);
        btn_fri = findViewById(R.id.cbtn_fri);
        btn_sat = findViewById(R.id.cbtn_sat);
        btn_sun = findViewById(R.id.cbtn_sun);
        cName    = findViewById(R.id.et_c_name);
        cTime    = findViewById(R.id.et_c_time);
        cCapacity= findViewById(R.id.et_c_capacity);
        cPrice   = findViewById(R.id.et_c_price);
        cRoom    = findViewById(R.id.et_c_room);
        cDesc    = findViewById(R.id.et_c_desc);
        btn_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseDays[0])
                {
                    btn_mon.setBackgroundColor(getResources().getColor(R.color.dark_orange));
                    courseDays[0] = true;
                }
                else{
                    btn_mon.setBackgroundColor(getResources().getColor(R.color.orange));
                    courseDays[0] = false;
                }


            }
        });


    }
}