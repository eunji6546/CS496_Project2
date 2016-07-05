package com.example.q.practice_a;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by q on 2016-07-06.
 */
public class GetClass extends AsyncTask<String, Void, Void> {

    private final Context context;
    public GetClass(Context c){
        this.context = c;
    }

    protected void onPreExecute(){
    }

    @Override
    protected Void doInBackground(String... params) {

        return null;
    }
}
