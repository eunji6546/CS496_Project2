package com.example.q.practice_a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModifyDetail extends AppCompatActivity {
    String title, writer, contents, password, keynum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_detail);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        writer = intent.getStringExtra("writer");
        contents = intent.getStringExtra("contents");
        password = intent.getStringExtra("password");
        keynum = intent.getStringExtra("keynum");

        EditText titleText = (EditText)findViewById(R.id.modify_title);
        titleText.setText(title, TextView.BufferType.EDITABLE);
        EditText writerText = (EditText)findViewById(R.id.modify_writer);
        writerText.setText(writer, TextView.BufferType.EDITABLE);
        EditText contentsText = (EditText)findViewById(R.id.modify_text);
        contentsText.setText(contents, TextView.BufferType.EDITABLE);

        Button restorebtn = (Button)findViewById(R.id.restorebtn);
        if (restorebtn != null) {
            restorebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText pw = (EditText)findViewById(R.id.check_pw);
                    if (pw.getText().toString().equals(password)){
                        Toast.makeText(getBaseContext(), "변경 사항 중 입니다..",  Toast.LENGTH_LONG).show();

                        new Thread() {
                            public void run() {
                                EditText ename = (EditText)findViewById(R.id.modify_writer);
                                EditText etitle = (EditText)findViewById(R.id.modify_title);
                                EditText etext = (EditText)findViewById(R.id.modify_text);
                                JSONObject jo = new JSONObject();
                                JSONArray ja = new JSONArray();
                                try {
                                    title = etitle.getText().toString();
                                    writer = ename.getText().toString();
                                    contents = etext.getText().toString();
                                    jo.put("pw",pw.getText().toString());
                                    jo.put("writer",writer);
                                    jo.put("title",title);
                                    jo.put("contents",contents);
                                    jo.put("keynum",keynum);
                                    ja.put(jo);
                                    new HttpConnectionThread().execute("http://143.248.47.61:8000/update", ja.toString());
                                    Intent intent = new Intent(getApplicationContext(),PostDetail.class);
                                    intent.putExtra("title", title);
                                    intent.putExtra("writer", writer);
                                    intent.putExtra("contents", contents);
                                    intent.putExtra("password", password);
                                    intent.putExtra("keynum", keynum);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                    else {
                        Toast.makeText(getBaseContext(), "비밀번호가 틀렸습니다.",  Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
