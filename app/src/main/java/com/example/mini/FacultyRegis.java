package com.example.mini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FacultyRegis extends AppCompatActivity {
    Button bt;
   // private FirebaseAuth fAuth;
    //EditText name, email, reg;
    //TextView tv;
    SQLiteDatabase db;
    String nam, em, facid, passs;
    ProgressBar p;
String cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_regis);
        bt = (Button) findViewById(R.id.button2);
        p = (ProgressBar) findViewById(R.id.p1);
        Intent it = getIntent();
        nam = it.getStringExtra("nam");
        facid = it.getStringExtra("reg");
        em = it.getStringExtra("em");
        passs = it.getStringExtra("pass");
        cont = it.getStringExtra("cont");

        if (cont.equals("a"))
        {
            db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS student(id VARCHAR,name VARCHAR,email VARCHAR,password VARCHAR);");
          //  db.execSQL("DELETE FROM student");
            Cursor c = db.rawQuery("SELECT * FROM student", null);
            if(c.getCount()!=0) {
                while (c.moveToNext()) {
                    String ad = c.getString(0);
                    String ab = c.getString(2);
                    if (ad.equals(facid) || ab.equals(em)) {
                        showMessage("ERROR", "ID or Email Already Exists");
                        // clearText();
                        return;
                    }
                }
               // db.execSQL("INSERT INTO student VALUES('" + facid + "','" + nam + "','" + em + "','" + passs + "');");
            }
                db.execSQL("INSERT INTO student VALUES('" + facid + "','" + nam + "','" + em + "','" + passs + "');");

            //p.setVisibility(View.GONE);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor c1 = db.rawQuery("SELECT * FROM student", null);
                    StringBuffer buffer = new StringBuffer();
                    while (c1.moveToNext()) {
                        buffer.append("ID: " + c1.getString(0) + "\n");
                        buffer.append("Name: " + c1.getString(1) + "\n");
                        buffer.append("Email: " + c1.getString(2) + "\n");
                        buffer.append("password: " + c1.getString(3) + "\n\n");
                    }
                    showMessage("Student Registration", "Successfully Registered");
                }
            });
           // Intent aa = new Intent(FacultyRegis.this, loginAct.class);
            //startActivity(aa);
        }

        else if(cont.equals("b"))
        {
            db = openOrCreateDatabase("FacultyDB", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS faculty(facultyid VARCHAR,name VARCHAR,email VARCHAR,password VARCHAR);");
            //db.execSQL("DELETE FROM faculty");
            Cursor c = db.rawQuery("SELECT * FROM faculty", null);
            if(c.getCount()!=0) {
                while (c.moveToNext()) {
                    String ad = c.getString(0);
                    String ab = c.getString(2);
                    if (ad.equals(facid) || ab.equals(em)) {
                        showMessage("ERROR", "ID or Email Already Exists");
                        // clearText();
                        return;
                    }
                }
                // db.execSQL("INSERT INTO student VALUES('" + facid + "','" + nam + "','" + em + "','" + passs + "');");
            }
                db.execSQL("INSERT INTO faculty VALUES('" + facid + "','" + nam + "','" + em + "','" + passs + "');");


            //p.setVisibility(View.GONE);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor c1 = db.rawQuery("SELECT * FROM faculty", null);
                    StringBuffer buffer = new StringBuffer();
                    while (c1.moveToNext()) {
                        buffer.append("ID: " + c1.getString(0) + "\n");
                        buffer.append("Name: " + c1.getString(1) + "\n");
                        buffer.append("Email: " + c1.getString(2) + "\n");
                        buffer.append("password: " + c1.getString(3) + "\n\n");
                    }
                    showMessage("Faculty Registration", "Successfully Registered");
                }
            });
           // Intent aa = new Intent(FacultyRegis.this, loginAct.class);
            //startActivity(aa);
        }
        else if(cont.equals("s"))
        {
            //Toast.makeText(FacultyRegis.this,"Email or password incorrect",Toast.LENGTH_LONG).show();
           db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
            Cursor c=db.rawQuery("SELECT * FROM student where email='"+em+"' and password='"+passs+"'", null);
            if(c.getCount()==0) {
                Toast.makeText(FacultyRegis.this,"Email or password incorrect",Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
               Intent i=new Intent(FacultyRegis.this,studlog.class);
                i.putExtra("em",em);
               startActivity(i);
            }
        }
        else
        { db = openOrCreateDatabase("FacultyDB", Context.MODE_PRIVATE, null);
            Cursor c=db.rawQuery("SELECT * FROM faculty where email='"+em+"' and password='"+passs+"'", null);
            if(c.getCount()==0) {
                Toast.makeText(FacultyRegis.this,"Email or password incorrect",Toast.LENGTH_LONG).show();
                return;
            }
            else if(em.equals("admin@gmail.com") && passs.equals("admin123"))
            {
                //Toast.makeText(FacultyRegis.this,"ADMIN",Toast.LENGTH_LONG).show();
                Intent ai=new Intent(FacultyRegis.this,adminlog.class);
               ai.putExtra("em",em);
                startActivity(ai);
            }
            else
            {
                Intent ai=new Intent(FacultyRegis.this,faclog.class);
                ai.putExtra("ema",em);
                //Toast.makeText(FacultyRegis.this,em,Toast.LENGTH_LONG).show();
                startActivity(ai);
            }

        }
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FacultyRegis.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}