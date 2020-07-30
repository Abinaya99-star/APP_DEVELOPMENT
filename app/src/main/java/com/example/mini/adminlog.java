package com.example.mini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminlog extends AppCompatActivity {
    Button upload,view,home,query,con;
    String nam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlog);
        Intent it = getIntent();
        nam = it.getStringExtra("em");
        upload=(Button)findViewById(R.id.button4);
        view=(Button)findViewById(R.id.button10);
        home=(Button)findViewById(R.id.button8);
        query=(Button)findViewById(R.id.button11);
        con=(Button)findViewById(R.id.butto);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(adminlog.this,Conference.class);
                it.putExtra("emai",nam);
                startActivity(it);
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(adminlog.this,viewbyquery.class);
                startActivity(it);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ti=new Intent(adminlog.this,MainActivity.class);
                ti.putExtra("email",nam);
                startActivity(ti);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(adminlog.this,faclogbase.class);
                it.putExtra("emai",nam);
                startActivity(it);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(adminlog.this,vie.class);
                startActivity(it);
            }
        });
    }
}

