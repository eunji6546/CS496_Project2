package com.example.q.practice_a;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

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
        String photo = i.getStringExtra("profilePhoto");
        String from = i.getStringExtra("from");

        mNumberOrEmail = (TextView) findViewById(R.id.textView3);
        mNumberOrEmail.setText(pn);
        mName = (TextView)findViewById(R.id.textView2);
        mName.setText(name);
        mPhoto = (ImageView)findViewById(R.id.imageView) ;

        if (from.equals("0")){
            // from facebook
            new DownloadImageTask((ImageView) mPhoto).execute(photo);
        }else {
            //from phonebook
            InputStream input = null;

            input = ContactsContract.Contacts
                    .openContactPhotoInputStream(getContentResolver(), Uri.parse(photo));

            if (input != null) {
                mPhoto.setImageBitmap( BitmapFactory.decodeStream(input));
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        mFrom = (ImageView)findViewById(R.id.imageView2);

        if (from.equals("0")){
            mFrom.setImageResource(R.drawable.facebooklogo);
            mNumberOrEmail.setVisibility(View.INVISIBLE);
        }else{
            mFrom.setImageResource(R.drawable.phonebook);
        }
    }
}
