package com.example.q.practice_a;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class ServerRequests {

    private static final int CONNECTION_TIMEOUT = 1000*5;
    private static final int MAX_RETRY = 0;
    private static final String SERVER_ADDRESS = "http://143.248.47.163:3000";
    private static final String TAG = "ServerRequests";
    private Context context;
    private ProgressDialog progressDialog;

    public ServerRequests(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
        this.context = context;
    }

    public void uploadImage(Bitmap image, String name, final GetUserCallback callback){
        progressDialog.show();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(MAX_RETRY, CONNECTION_TIMEOUT);
        final RequestParams params = new RequestParams();
        JSONObject overall = new JSONObject();
        try {
            overall.put("name",name);
            overall.put("image",encodedImage);
        } catch (Exception e){
            e.printStackTrace();
            showError();
        }
        params.put("payload",overall.toString());
        client.post(SERVER_ADDRESS+"/UploadImage",params,new JsonHttpResponseHandler(){
            @Override
            public void onStart(){
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    GetUserCallback userCallback = callback;                        //TODO : try calling callback directly
                    progressDialog.dismiss();
                    userCallback.flagged(response.getBoolean("Status"));            //TODO : Remove critical parse flaw
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.e(TAG,"JSON parse error while registering");
                    showError();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressDialog.dismiss();
                Log.e(TAG,"Network Failure");
                showError();
            }
        });
    }

    private void showError(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage("Network Error try again later");
        dialogBuilder.setPositiveButton("OK",null);
        dialogBuilder.show();
    }

    private void showImageError(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage("Image not found on server");
        dialogBuilder.setPositiveButton("OK",null);
        dialogBuilder.show();
    }

}