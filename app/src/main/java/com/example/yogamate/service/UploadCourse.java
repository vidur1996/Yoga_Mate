package com.example.yogamate.service;

import com.example.yogamate.model.Response;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class UploadCourse {

//    private void sendJsonAndGetResponse(String jsonData) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL("https://stuiis.cms.gre.ac.uk/COMP1424CoreWS/comp1424cw/SubmitClasses");
//                    //trustAllHosts();
//
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("POST");
//                    connection.setRequestProperty("Content-Type", "application/json");
//                    connection.setDoOutput(true);
//
//                    // Replace this with your actual JSON data
//
//
//                    try (OutputStream os = connection.getOutputStream()) {
//                        byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
//                        os.write(input, 0, input.length);
//                    }
//
//                    int responseCode = connection.getResponseCode();
//
//                    // Handle the response code and read the response
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        try (BufferedReader reader = new BufferedReader(
//                                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
//
//                            StringBuilder response = new StringBuilder();
//                            String line;
//                            while ((line = reader.readLine()) != null) {
//                                response.append(line);
//                            }
//
//                            // Process the response as needed
//                            final String jsonResponse = response.toString();
//
//                            // Perform UI operations on the main thread if needed
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // Handle the response in the UI thread
//                                    // Example: Update UI elements with jsonResponse
//                                }
//                            });
//                        }
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
}

