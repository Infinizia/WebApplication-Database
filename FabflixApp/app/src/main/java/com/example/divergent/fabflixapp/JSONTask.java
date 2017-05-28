package com.example.divergent.fabflixapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONTask extends AsyncTask<MyTask,String,String> {

    Context context;
    public JSONTask(Context context)
    {
        this.context = context.getApplicationContext();
    }
    @Override
    protected String doInBackground(MyTask... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            MyTask currentTask = params[0];
            URL myUrl = new URL(currentTask.getUrl());
            connection = (HttpURLConnection) myUrl.openConnection();
            //Set request type
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //Send request
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(currentTask.getJsonObject().toString());
            osw.flush();
            osw.close();

            //Receive response
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }
            //String responseResult = buffer.toString();
            // JSONObject data = new JSONObject(responseResult);
            //String custFirstName = data.getString("first_name");
            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            try{
                if (reader != null)
                    reader.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Intent myIntent = new Intent(context, MainActivity.class);
        myIntent.putExtra("jsonObj", result);
        context.startActivity(myIntent);
    }
}
