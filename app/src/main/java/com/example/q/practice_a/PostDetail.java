package com.example.q.practice_a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PostDetail extends AppCompatActivity {
    // Contact Detail 처럼
    // intent 로 넘겨온 것들 레이아웃에 set 해주고
    // 삭제 구현하기

    String title, writer, contents, password, keynum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Button deletebtn = (Button)findViewById(R.id.deletebtn);
        Button modifybtn = (Button)findViewById(R.id.modifybtn);

        Intent i = getIntent();
        title  = i.getStringExtra("title");
        writer = i.getStringExtra("writer");
        contents = i.getStringExtra("contents");
        password = i.getStringExtra("password");
        keynum = i.getStringExtra("keynum");

        if (deletebtn != null) {
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), "정말로 지우고 싶으시면, 길게 눌러주세요.",  Toast.LENGTH_SHORT).show();
                }
            });
            deletebtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    EditText pw = (EditText)findViewById(R.id.pw2);
                    Log.e("password",password);
                    Log.e("password",pw.getText().toString());
                    if (pw.getText().toString().equals(password)){
                        Toast.makeText(getBaseContext(), "삭제합니다.",  Toast.LENGTH_LONG).show();
                        new Thread(){
                            public void run() {
                                //new HttpDeleteRequest().doInBackground("http://143.248.47.61:8000/delete?keynum=\"" + keynum + "\"");
                                new HttpDeleteRequest().doInBackground("http://143.248.47.61:8000/delete",keynum);
                            }
                        }.start();

                        //finish();
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Wrong Password.",  Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
            });
        }

        // 삭제 구현은
        // 입력칸 하나 만들어서 거기 비번이랑 저기 인텐트에서 가져온 비번이랑 다르면 그냥 토스트 띄우고 아무것도 안하고
        // 맞으면 http request 를 POST로 보내고,
        // ~~~.~~~.~~~:~~~/deletpost?keynum="##" 으로 보내기
        // ## 이 저 키넘버
    }
}
