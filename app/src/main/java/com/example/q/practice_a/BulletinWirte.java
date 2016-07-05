package com.example.q.practice_a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BulletinWirte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_wirte);

        Button storebtn = (Button)findViewById(R.id.storebtn);
        Button cancelbtn = (Button)findViewById(R.id.cancelbtn);

        if (cancelbtn != null) {
            cancelbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getBaseContext(), "길게 누르시면 기록이 지워집니다.",  Toast.LENGTH_LONG).show();
                }
            });
            cancelbtn.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getBaseContext(), "삭제합니다.",  Toast.LENGTH_LONG).show();
                    return true;
                }
            });
        }
        if (storebtn != null) {
            storebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), "저장 중 입니다..",  Toast.LENGTH_LONG).show();
                    new Thread() {
                        public void run() {
                            EditText name = (EditText)findViewById(R.id.writer);
                            EditText pw = (EditText)findViewById(R.id.pw);
                            EditText title = (EditText)findViewById(R.id.title);
                            EditText text = (EditText)findViewById(R.id.text);
                            JSONObject jo = new JSONObject();
                            JSONArray ja = new JSONArray();
                            try {
                                jo.put("pw",pw.getText().toString());
                                jo.put("name",name.getText().toString());
                                jo.put("title",title.getText().toString());
                                jo.put("text",text.getText().toString());
                                ja.put(jo);
                                new HttpConnectionThread().doInBackground("http://143.248.47.56:1337/postlist", ja.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            });
        }
    }
}
