package com.example.yogamate;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yogamate.model.Instance;
import com.example.yogamate.model.SaveCourse;
import com.example.yogamate.model.WebService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WebServiceActivity extends AppCompatActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    Button btn_upload;
    EditText et_username;
    DatabaseReference databaseReference;
    WebService wb = new WebService();
    ArrayList<SaveCourse> list = new ArrayList<SaveCourse>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        btn_upload = findViewById(R.id.btn_upload_course);
        et_username = findViewById(R.id.et_cs_name);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_username.getText().toString().trim().equals("")) {
                    showAlert("Error", "Please enter the user id");
                } else {
                    saveWebService(et_username.getText().toString().trim());
                }
            }
        });


    }

    public void showAlert(String title, String msg) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    public void saveWebService(String userid) {
        wb.setUserId(userid);
        list.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("course");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.exists()) {
                    SaveCourse value = snapshot.getValue(SaveCourse.class);
                    ArrayList<Instance> inlist = new ArrayList<>();

                    // Check if the "instances" node exists in the snapshot
                    DataSnapshot instancesSnapshot = snapshot.child("instance");
                    if (instancesSnapshot.exists()) {
                        for (DataSnapshot instanceSnapshot : instancesSnapshot.getChildren()) {
                            Instance in = instanceSnapshot.getValue(Instance.class);
                            inlist.add(in);
                        }
                        value.setInstances(inlist);
                    }

                    list.add(value);
                }
                createJson();
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


    }

    public void createJson() {
        wb.setDetailList(list);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String outJson = gson.toJson(wb);
        Log.e("json :", outJson);
        sendJsonAndGetResponse(outJson);
    }

    private void sendJsonAndGetResponse(String jsonData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://stuiis.cms.gre.ac.uk/COMP1424CoreWS/comp1424cw/SubmitClasses");
                    //trustAllHosts();

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }

                    int responseCode = connection.getResponseCode();

                    // Handle the response code and read the response
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        try (BufferedReader reader = new BufferedReader(
                                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }


                            final String jsonResponse = response.toString();

                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        JSONObject jsonObject = new JSONObject(jsonResponse);
                                        String message = jsonObject.getString("message");
                                        showAlert("Response", message);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

}