package com.example.sjecadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout UploadNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UploadNotice = (LinearLayout) findViewById(R.id.uploadNotice);
        UploadNotice.setOnClickListener(this);
    }

    public void onClick(View v){
        Intent it=new Intent(this, com.example.sjecadmin.UploadNotice.class);
        startActivity(it);
    }
}