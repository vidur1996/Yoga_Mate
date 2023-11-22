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
import java.util.concurrent.CompletableFuture;

public class UploadCourse {

    public CompletableFuture<Response> uploadJson(String jsonInput) {
        CompletableFuture<Response> future = new CompletableFuture<>();

        new Thread(() -> {
            HttpURLConnection urlConnection = null;
            DataOutputStream dataOutputStream = null;

            try {
                URL url = new URL("https://stuiis.cms.gre.ac.uk/COMP1424CoreWS/comp1424cw/SubmitClasses");
                urlConnection = (HttpURLConnection) url.openConnection();

                // Set up the connection
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                // Write the JSON data to the output stream
                OutputStream outputStream = urlConnection.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeBytes(jsonInput);

                // Get the response code
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response from the server
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Parse the JSON response using Gson
                    Gson gson = new Gson();
                    Response response = gson.fromJson(result.toString(), Response.class);

                    // Complete the CompletableFuture with the response
                    future.complete(response);
                } else {
                    // Handle error response code if needed
                    // You might want to set some default values or handle the error in another way
                    future.completeExceptionally(new RuntimeException("Error, Response Code: " + responseCode));
                }
            } catch (IOException e) {
                e.printStackTrace();
                future.completeExceptionally(e);
            } finally {
                // Close the connections and streams
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return future;
    }
}

