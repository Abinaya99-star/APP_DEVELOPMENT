package com.example.mini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class studlog extends AppCompatActivity {
Button upload,home,uplo,conf;
String nam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studlog);
        Intent it = getIntent();
        nam = it.getStringExtra("em");
        upload=(Button)findViewById(R.id.button4);
        home=(Button)findViewById(R.id.button6);
        uplo=(Button)findViewById(R.id.button10);
        conf=(Button)findViewById(R.id.butto);
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(studlog.this,Conference.class);
                it.putExtra("emai",nam);
                startActivity(it);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ti=new Intent(studlog.this,MainActivity.class);
                ti.putExtra("email",nam);
                startActivity(ti);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(studlog.this,faclogbase.class);
                it.putExtra("emai",nam);
                startActivity(it);
            }
        });
        uplo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(studlog.this,vie.class);
                startActivity(it);
            }
        });
    }
}
