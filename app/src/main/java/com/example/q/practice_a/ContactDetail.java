package com.example.q.practice_a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


        Log.e("000",photo );


        mNumberOrEmail = (TextView) findViewById(R.id.textView3);
        mNumberOrEmail.setText(pn);
        mName = (TextView)findViewById(R.id.textView2);
        mName.setText(name);
        mPhoto = (ImageView)findViewById(R.id.imageView) ;
        new DownloadImageTask((ImageView) mPhoto)
                .execute(photo);

        //vhxh

        mFrom = (ImageView)findViewById(R.id.imageView2);

        Log.e("_____",from);
        if (from.equals("0")){
            Log.e("!!!","FACEBOOK");
            mFrom.setImageResource(R.drawable.facebook_logo);
            mNumberOrEmail.setVisibility(View.INVISIBLE);
        }else{
            Log.e("!!!","PHONEBOOK");
            mFrom.setImageResource(R.drawable.phonebook);
        }




    }
}
