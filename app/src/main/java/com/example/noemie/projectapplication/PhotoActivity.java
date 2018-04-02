package com.example.noemie.projectapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity {

    public static Bitmap bitmap;
    Button pictureButton;
    Button save;
    ImageView imageView;
    File file;

    /*To save pictures in a file*/
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        pictureButton = findViewById(R.id.button);
        imageView = findViewById(R.id.imageview);
        save = findViewById(R.id.button_save);

        pictureButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent intent = new Intent(PhotoActivity.this, MainActivity.class);

                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

    }
}
