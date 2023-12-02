package com.example.yogamate;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.yogamate.model.Response;
import com.example.yogamate.model.SaveCourse;
import com.example.yogamate.model.WebService;
import com.example.yogamate.model.webCourse;
import com.example.yogamate.model.webInstance;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class WebServiceActivity extends AppCompatActivity {
    Button btn_upload;
    EditText et_username;
    DatabaseReference databaseReference;
    WebService wb = new WebService();
    ArrayList<webCourse> list = new ArrayList<webCourse>();

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
                    showAlert("Error", "Please enter the user id",false);
                } else {
                    saveWebService(et_username.getText().toString().trim());

                }
            }
        });


    }

    public void showAlert(String title, String msg,boolean onMove) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            if (onMove){
                                Intent exit = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(exit);

                            }
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
                    webCourse value = snapshot.getValue(webCourse.class);
                    ArrayList<webInstance> inlist = new ArrayList<>();

                    // Check if the "instances" node exists in the snapshot
                    DataSnapshot instancesSnapshot = snapshot.child("instance");
                    if (instancesSnapshot.exists()) {
                        for (DataSnapshot instanceSnapshot : instancesSnapshot.getChildren()) {
                            webInstance in = instanceSnapshot.getValue(webInstance.class);
                            inlist.add(in);
                        }
                        value.setClassList(inlist);
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
       
        save(outJson);
    }

    //code obtained form lecture notes on connectivity
       public void save(  String data){
        try {
            URL pageURL = new URL("https://stuiis.cms.gre.ac.uk/COMP1424CoreWS/comp1424cw/SubmitClasses");
            trustAllHosts();
            HttpURLConnection con = (HttpURLConnection)pageURL.openConnection();

            String jsonString = data;

            JsonThread myTask = new JsonThread(this, con, jsonString);
            Thread t1 = new Thread(myTask, "JSON Thread");
            t1.start();

        } catch (IOException e) { e.printStackTrace(); }
    }


//code obtained form lecture notes on connectivity
    private void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//code obtained form lecture notes on connectivity
    class JsonThread implements Runnable
    {
        private AppCompatActivity activity;
        private HttpURLConnection con;
        private String jsonPayLoad;

        public JsonThread(AppCompatActivity activity, HttpURLConnection con, String jsonPayload)
        {
            this.activity = activity;
            this.con = con;
            this.jsonPayLoad = jsonPayload;
        }

        @Override
        public void run()
        {
            String response = "";
            if (prepareConnection()) {
                response = postJson();
            } else {
                response = "Error preparing the connection";
            }
            showResult(response);
        }

//code obtained form lecture notes on connectivity
        private void showResult(String response) {
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Gson gson = new Gson();
                    Response rs = gson.fromJson(response, Response.class);
                    showAlert(rs.getUploadResponseCode().toString(),
                            rs.getMessage(),
                            rs.getUploadResponseCode().toString().equals("SUCCESS"));
                }
            });
        }

//code obtained form lecture notes on connectivity
        private String postJson() {
            String response = "";
            try {
                String postParameters = "jsonpayload=" + URLEncoder.encode(jsonPayLoad, "UTF-8");
                con.setFixedLengthStreamingMode(postParameters.getBytes().length);
                PrintWriter out = new PrintWriter(con.getOutputStream());
                out.print(postParameters);
                out.close();
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    response = readStream(con.getInputStream());
                } else {
                    response = "Error contacting server: " + responseCode;
                }
            } catch (Exception e) {
                response = e.toString();//"Error executing code";
            }
            return response;
        }

//code obtained form lecture notes on connectivity
        private String readStream(InputStream in) {
            StringBuilder sb = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                String nextLine = "";
                while ((nextLine = reader.readLine()) != null) {
                    sb.append(nextLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }



//code obtained form lecture notes on connectivity
        private boolean prepareConnection() {
            try {
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                return true;

            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}