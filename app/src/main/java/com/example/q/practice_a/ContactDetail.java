package com.example.q.practice_a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetail extends AppCompatActivity {
    ImageView mPhoto;
    TextView mName;
    TextView mNumberOrEmail;
    ImageView mFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        Intent i = getIntent();
        String pn = i.getStringExtra("numberOrEmail");
        String name = i.getStringExtra("name");
        String photo = i.getStringExtra("profilePhoto"); //되나?
        String from = i.getStringExtra("from");


        mNumberOrEmail = (TextView) findViewById(R.id.textView3);
        mNumberOrEmail.setText(pn);
        mName = (TextView)findViewById(R.id.textView2);
        mName.setText(name);

        //vhxh

        mFrom = (ImageView)findViewById(R.id.imageView2);

        if (from == "0"){
            mFrom.setImageResource(R.drawable.facebook_logo);
        }else{
            mFrom.setImageResource(R.drawable.phonebook);
        }




    }
}
