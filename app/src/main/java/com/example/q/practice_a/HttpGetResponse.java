package com.example.q.practice_a;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by q on 2016-07-06.
 */
public class HttpGetResponse extends AsyncTask<String,Void, String> {
    @Override
    protected String doInBackground(String... url) {
        URL murl;
        InputStream is = null;
        String responseData = "";

        try {
            murl = new URL(url[0]);
            HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");

            conn.connect();

            int response = conn.getResponseCode();
            is = conn.getInputStream();

//            // Convert the InputStream into a string
//            String contentAsString = readIt(is, len);
//           // Log.e("@@",contentAsString);
//            return contentAsString;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            JSONArray jsonResponse = null;
            //initiate strings to hold response data
            String inputLine;

            //read the InputStream with the BufferedReader line by line and add each line to responseData
            while ((inputLine = in.readLine()) != null) {
                responseData += inputLine;
            }


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        return responseData;
    }

    /*    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }*/
}
