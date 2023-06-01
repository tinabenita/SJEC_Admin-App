package com.example.sjecadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class UploadNotice extends AppCompatActivity implements View.OnClickListener{
    private CardView addImage;
    private ImageView noticeImageView;
    private final int REQ=1;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);
        addImage =(CardView) findViewById(R.id.addImage);
        addImage.setOnClickListener(this);
        noticeImageView =(ImageView) findViewById(R.id.noticeImageView);
    }

    @Override
    public void onClick(View view) {
            openGallery();
    }

    private void openGallery() {
        Intent pickImage= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            noticeImageView.setImageBitmap(bitmap);
        }
    }
}