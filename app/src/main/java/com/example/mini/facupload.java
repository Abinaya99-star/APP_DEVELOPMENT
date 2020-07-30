package com.example.mini;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class facupload extends AppCompatActivity {
EditText name,dta;
String nam,branch,index,first,mont,da,date;
Button bt;
Date dat;
SQLiteDatabase db;
StorageReference storageReference;
DateFormat df;
Date d1,d2;
int flag=0;
    int flag1=0;
    int flag2=0;
DatabaseReference databaseReference;
DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facupload);
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
name=(EditText)findViewById(R.id.editText8);
dta=(EditText)findViewById(R.id.editText17);
bt=(Button)findViewById(R.id.button7);
        dta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        facupload.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = day+ "/" +month+ "/" +year;
                dta.setText(date);
            }
        };
storageReference= FirebaseStorage.getInstance().getReference();
databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
            da="31/05/2020";
            df=new SimpleDateFormat("dd/MM/yyyy");
            d1=df.parse(date);
            d2=df.parse(da);
        }
        catch (Exception e)
        {

        }
        //Toast.makeText(facupload.this,dat.toString(),Toast.LENGTH_LONG).show();
        //Toast.makeText(facupload.this,df.format(d1),Toast.LENGTH_LONG).show();
        db = openOrCreateDatabase("FacultyDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS journal2(username VARCHAR,Date DATE,indexing VARCHAR,first VARCHAR,month VARCHAR,title VARCHAR,cur_date DATE);");
        //db.execSQL("DELETE FROM journal1");
        Intent it = getIntent();
        nam = it.getStringExtra("name");
        //branch=it.getStringExtra("branch");
        index=it.getStringExtra("index");
        first=it.getStringExtra("first");
        mont=it.getStringExtra("month");
        Cursor c2 = db.rawQuery("SELECT * FROM journal2 WHERE username='" + nam + "'", null);
       // StringBuffer buffer = new StringBuffer();
        while (c2.moveToNext()) {
            // ab=c2.getString(0);
         if(c2.getString(3).equals("Yes"))
         {
             flag=1;
         }
         if(flag==1 && first.equals("Yes"))
         {
             showMessage("ERROR","You already submitted journal publications as your First Time Publication");
             return;
         }
        }
        db.execSQL("INSERT INTO journal2 VALUES('" + nam + "','" + df.format(d1) + "','" + index + "','" + first + "','" + mont + "','" +name.getText().toString() + "','" + df.format(d2) + "');");
        selectPDFFile();
    }
});
    }
    private void selectPDFFile() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uploadPDFFile(data.getData());
        }
    }

    private void uploadPDFFile(Uri data) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("uploading......");
        progressDialog.show();
        StorageReference reference=storageReference.child("uploads"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete());
                Uri url=uri.getResult();
                db upload=new db(name.getText().toString(),url.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(upload);
         //       Toast.makeText(facupload.this,"File Uploaded",Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded :"+(int)progress+"%");
            }
        });
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(facupload.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
