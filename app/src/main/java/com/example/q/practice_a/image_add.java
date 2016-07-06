package com.example.q.practice_a;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class image_add extends AppCompatActivity implements View.OnClickListener {

    ImageView imageToUpload;
    Button bUploadImage;
    EditText uploadImageName;
    private static final int RESULT_LOAD_IMAGE = 9002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_add);

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);

        bUploadImage = (Button) findViewById(R.id.bUploadImage);

        uploadImageName = (EditText) findViewById(R.id.etUplaodNames);

        imageToUpload.setOnClickListener(this);
        bUploadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bitmap image;
        String name;
        switch (v.getId()){
            case R.id.imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.bUploadImage:
                image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
                name = uploadImageName.getText().toString();

                new ServerRequests(image_add.this).uploadImage(image, name, new GetUserCallback() {
                    @Override
                    public void flagged(boolean flag) {
                        if (flag == true){
                            Toast.makeText(image_add.this,"Image Upload Successful",Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(image_add.this);
                            dialogBuilder.setMessage("Image Upload Failed. Try uploading again");
                            dialogBuilder.setPositiveButton("OK",null);
                            dialogBuilder.show();
                        }
                    }

                    @Override
                    public void imgData(Bitmap image) {}
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(null);
            imageToUpload.setImageURI(selectedImage);
        }
    }

}