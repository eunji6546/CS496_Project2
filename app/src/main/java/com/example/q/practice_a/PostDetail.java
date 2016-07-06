package com.example.q.practice_a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PostDetail extends AppCompatActivity {
    // Contact Detail 처럼
    // intent 로 넘겨온 것들 레이아웃에 set 해주고
    // 삭제 구현하기

    String title, writer, contents, password, keynum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Intent i = getIntent();
         title  = i.getStringExtra("title");
         writer = i.getStringExtra("writer");
        contents = i.getStringExtra("contents");
        password = i.getStringExtra("password");
        keynum = i.getStringExtra("keynum");

        // 삭제 구현은
        // 입력칸 하나 만들어서 거기 비번이랑 저기 인텐트에서 가져온 비번이랑 다르면 그냥 토스트 띄우고 아무것도 안하고
        // 맞으면 http request 를 POST로 보내고,
        // ~~~.~~~.~~~:~~~/deletpost?keynum="##" 으로 보내기
        // ## 이 저 키넘버

    }
}
