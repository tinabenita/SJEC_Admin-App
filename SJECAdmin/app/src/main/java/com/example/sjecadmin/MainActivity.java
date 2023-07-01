package com.example.sjecadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sjecadmin.notice.UploadNotice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView uploadNotice,addGalleryImage,addEbook, faculty, deleteNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadNotice = (CardView)  findViewById(R.id.addNotice);
        uploadNotice.setOnClickListener(this);

        addGalleryImage = (CardView) findViewById(R.id.addGalleryImage);
        addGalleryImage.setOnClickListener(this);

        addEbook = (CardView) findViewById(R.id.addEbook);
        addEbook.setOnClickListener(this);

        faculty = (CardView) findViewById(R.id.faculty);
        faculty.setOnClickListener(this);

        deleteNotice = (CardView) findViewById(R.id.deleteNotice);
        deleteNotice.setOnClickListener(this);
    }

    public void onClick(View v){
        Intent it;
        switch(v.getId()) {
            case R.id.addNotice : it = new Intent(this, UploadNotice.class);
                                  startActivity(it); break;
            case R.id.addGalleryImage : it = new Intent(this, com.example.sjecadmin.UploadImage.class);
                                        startActivity(it); break;

            case R.id.addEbook : it = new Intent(this, com.example.sjecadmin.UploadPdfActivity.class);
                                        startActivity(it); break;
            case R.id.faculty:  it = new Intent(this, com.example.sjecadmin.faculty.UpdateFaculty.class);
                startActivity(it); break;
            case R.id.deleteNotice:  it = new Intent(this, com.example.sjecadmin.notice.DeleteNoticeActivity.class);
                startActivity(it); break;


        }
    }
}