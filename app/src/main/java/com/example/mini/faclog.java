package com.example.mini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class faclog extends AppCompatActivity {
Button upload,view,home,conf;
String nam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faclog);
        Intent it = getIntent();
        nam = it.getStringExtra("ema");
        //Toast.makeText(faclog.this,nam,Toast.LENGTH_LONG).show();
        upload=(Button)findViewById(R.id.button4);
        view=(Button)findViewById(R.id.button5);
        home=(Button)findViewById(R.id.button8);
        conf=(Button)findViewById(R.id.butto);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ti=new Intent(faclog.this,MainActivity.class);
                ti.putExtra("email",nam);
                startActivity(ti);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(faclog.this,faclogbase.class);
                it.putExtra("emai",nam);
                startActivity(it);
            }
        });
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(faclog.this,Conference.class);
                it.putExtra("emai",nam);
                startActivity(it);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(faclog.this,vie.class);
                startActivity(it);
            }
        });

    }
}
