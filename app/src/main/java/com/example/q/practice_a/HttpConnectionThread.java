package com.example.q.practice_a;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionThread extends AsyncTask<String,Void, String> {

    @Override
    protected String doInBackground(String... url) {
        URL murl;
        String response = null;

        try {
            murl = new URL(url[0]);
            HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            // conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.connect();

            conn.getOutputStream();
            OutputStream os =  conn.getOutputStream();

            JSONArray jarray = null;
            try {
                jarray = new JSONArray(url[1]);
                Log.e("POST",jarray.toString());
                os.write(jarray.toString().getBytes("UTF-8"));
            } catch (JSONException e) {
                Log.e("tt","Don/t do that");
                e.printStackTrace();
            }
            os.flush();
            os.close();
            response = conn.getResponseMessage();

        } catch (IOException e) {

        }
        return response;
    }

/*    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }*/
}