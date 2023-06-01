package com.example.sjecadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView uploadNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uploadNotice = (CardView)  findViewById(R.id.addNotice);
        uploadNotice.setOnClickListener(this);
    }

    public void onClick(View v){
        switch(v.getId()) {
            case R.id.addNotice : Intent it = new Intent(this, com.example.sjecadmin.UploadNotice.class);
                                     startActivity(it); break;
        }
    }
}