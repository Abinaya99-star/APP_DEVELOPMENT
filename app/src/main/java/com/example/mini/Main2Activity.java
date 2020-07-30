package com.example.mini;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    EditText name;
    int flag=0;
    String dd,nam,branch,index,first,month,author,date,place,time,issue,vol,page;
    Button bt;
    Date dat;
    SQLiteDatabase db;
    DateFormat df;
    Date d1,d2;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name=(EditText)findViewById(R.id.editText8);
        bt=(Button)findViewById(R.id.button7);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("con");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd=name.getText().toString();
               /* Date c= Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-YYYY");
                String formatdate=dateFormat.format(c);
                db = openOrCreateDatabase("FacultyDB", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS conference1(username VARCHAR,Date DATE,indexing VARCHAR,first VARCHAR,month VARCHAR,title VARCHAR,branch VARCHAR,con_date VARCHAR,con_time VARCHAR,con_author VARCHAR,con_place VARCHAR,issue VARCHAR,volume VARCHAR,page VARCHAR);");
                //db.execSQL("DELETE FROM conference");
                Intent it = getIntent();
                nam = it.getStringExtra("name");
                branch=it.getStringExtra("bran");
                index=it.getStringExtra("index");
                first=it.getStringExtra("first");
                month=it.getStringExtra("month");
                author=it.getStringExtra("author");
                place=it.getStringExtra("place");
                date=it.getStringExtra("dat");
               // da=dta.getText().toString();
                // Date c= Calendar.getInstance().getTime();
                //SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/YYYY");
                //String formatdate=dateFormat.format(da);
                try {
                    dd="31/05/2020";
                    df=new SimpleDateFormat("dd/MM/yyyy");
                    d1=df.parse(date);
                    d2=df.parse(dd);
                }
                catch (Exception e)
                {

                }
                time=it.getStringExtra("time");
                issue=it.getStringExtra("issue");
                vol=it.getStringExtra("volume");
                page=it.getStringExtra("page");
                Cursor c2 = db.rawQuery("SELECT * FROM conference1 WHERE username='" + nam + "'", null);
                // StringBuffer buffer = new StringBuffer();
                while (c2.moveToNext()) {
                    // ab=c2.getString(0);
                    if(c2.getString(3).equals("Yes"))
                    {
                        flag=1;
                    }
                    if(flag==1 && first.equals("Yes"))
                    {
                        showMessage("ERROR","You already submitted conference publication as your First Time Publication");
                        return;
                    }
                }
                db.execSQL("INSERT INTO conference1 VALUES('" + nam + "','" + df.format(d2) + "','" + index + "','" + first + "','" + month + "','" +name.getText().toString() + "','" + branch + "','" + df.format(d1) + "','" + time + "','" + author + "','" + place + "','" + issue + "','" + vol + "','" + page + "');");
                */selectPDFFile();
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
        StorageReference reference=storageReference.child("con/"+dd+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete());
                Uri url=uri.getResult();
                db upload=new db(name.getText().toString(),url.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(upload);
                Toast.makeText(Main2Activity.this,"File Uploaded",Toast.LENGTH_LONG).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
