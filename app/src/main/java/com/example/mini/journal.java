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
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class journal extends AppCompatActivity {
    EditText pa,fil,imf,voll;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String index,branch,pag,name,branc,dat,autho,issu,titl,yea,connam,fname,vov,imm;
    String email,first,date,author,inde,vol,im,place,title,year,nam,page,type,file,temp;
    Button bt;
    DateFormat df;
    Date d1,d2,d3;
    SQLiteDatabase db;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        pa=(EditText)findViewById(R.id.editText15);
        bt=(Button)findViewById(R.id.button9);
        fil=(EditText)findViewById(R.id.editText8);
        imf=(EditText)findViewById(R.id.editText14);
        voll=(EditText)findViewById(R.id.editText18);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pag=pa.getText().toString();
                fname=fil.getText().toString();
                vov=voll.getText().toString();
                imm=imf.getText().toString();
                Intent it=getIntent();
                name=it.getStringExtra("name");
                branc=it.getStringExtra("branch");
                dat=it.getStringExtra("dat");
                autho=it.getStringExtra("author");
                issu=it.getStringExtra("issue");
                titl=it.getStringExtra("title");
                yea=it.getStringExtra("year");
                connam=it.getStringExtra("name_of");
                temp=fname;
                if(pag.equals("") || branch.equals("") || index.equals("") || fname.equals("") || vov.equals("") || imm.equals(""))
                {
                    Toast.makeText(journal.this,"Please fill all the fields",Toast.LENGTH_LONG).show();
                    return;
                }
                String[] fi=pag.split("-");
                int start=Integer.parseInt(fi[0]);
                int fin=Integer.parseInt(fi[1]);
                if(fin<start)
                {
                    Toast.makeText(journal.this,"Invalid Page No",Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    df = new SimpleDateFormat("dd/MM/yyyy");
                    d1 = df.parse(dat);
                    d2=df.parse("01/01/2020");
                    d3=df.parse("07/06/2019");
                }
                catch (Exception e)
                {

                }
                //Toast.makeText(Con2.this,title+year,Toast.LENGTH_LONG).show();
                db = openOrCreateDatabase("FacultyDB", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS jou(email VARCHAR,firstornot VARCHAR,condate DATE,author VARCHAR,issue VARCHAR,title VARCHAR,year VARCHAR,conname VARCHAR,page VARCHAR,type VARCHAR,indexi VARCHAR,file VARCHAR,volume VARCHAR,impactfactor VARCHAR);");
               //db.execSQL("DELETE FROM jou");
                Cursor c2 = db.rawQuery("SELECT * FROM jou WHERE email='" + name + "'", null);
                Cursor c5 = db.rawQuery("SELECT * FROM jou", null);
                while(c5.moveToNext()) {
                    if (fname.equals(c5.getString(11))) {
                        showMessage("ERROR", "File Name Already Exists");
                        return;
                    }
                }
                // StringBuffer buffer = new StringBuffer();
                while (c2.moveToNext()) {
                    // ab=c2.getString(0);
                    if (c2.getString(1).equals("Yes")) {
                        flag = 1;
                    }
                    if (flag == 1 && branch.equals("Yes")) {
                        showMessage("ERROR", "You already submitted journal publications as your First Time Publication");
                        return;
                    }
                }
                storageReference= FirebaseStorage.getInstance().getReference();
                databaseReference= FirebaseDatabase.getInstance().getReference("jou/"+yea);
                selectPDFFile();
                db.execSQL("INSERT INTO jou VALUES('" + name + "','" + branch + "','" + df.format(d1) + "','" + autho + "','" + issu + "','" + titl + "','" + yea + "','" + connam + "','" + pag + "','" + branc + "','" + index + "','" + fname + "','" + vov + "','" + imm + "');");

                email=name;
                first=branch;
                date=df.format(d1);
                author=autho;
                place=issu;
                title=titl;
                year=yea;
                nam=connam;
                page=pag;
                type=branc;
                inde=index;
                file=fname;
                vol=vov;
                im=imm;
//https://script.google.com/macros/s/AKfycbynqptALqWvnYgzvE6c7APj4RFUHnUks_EoYEmaTsSvQgs_IXMK/exec
                addtosheet1("https://script.google.com/macros/s/AKfycbynqptALqWvnYgzvE6c7APj4RFUHnUks_EoYEmaTsSvQgs_IXMK/exec");
                if(d1.compareTo(d2)>=0)
                {
                    addtosheet1("https://script.google.com/macros/s/AKfycbyII7G88IiJjX0-G7KhDQyVhLLQin5-9zSAeIbzYlsAkXUu5Fc/exec");
                }
                if(d1.compareTo(d3)>=0)
                {
                    addtosheet1("https://script.google.com/macros/s/AKfycbzQtwVEvq0qISJroOCMnJeyqpMAGaElh4puWz8NWPTfPc2bQ48/exec");
                }
              /*  Cursor c3 = db.rawQuery("SELECT * FROM jou WHERE '01/01/2020' AND condate <= '06/07/2020' AND file='" + fname + "'", null);
                //StringBuffer buffer = new StringBuffer();
                while (c3.moveToNext()) {
                    email=c3.getString(0);
                    first=c3.getString(1);
                    date=c3.getString(2);
                    author=c3.getString(3);
                    place=c3.getString(4);
                    title=c3.getString(5);
                    year=c3.getString(6);
                    nam=c3.getString(7);
                    page=c3.getString(8);
                    type=c3.getString(9);
                    inde=c3.getString(10);
                    file=c3.getString(11);
                    vol=c3.getString(12);
                    im=c3.getString(13);

//https://script.google.com/macros/s/AKfycbyII7G88IiJjX0-G7KhDQyVhLLQin5-9zSAeIbzYlsAkXUu5Fc/exec
                    addtosheet1("https://script.google.com/macros/s/AKfycbyII7G88IiJjX0-G7KhDQyVhLLQin5-9zSAeIbzYlsAkXUu5Fc/exec");
                }
                Cursor c4 = db.rawQuery("SELECT * FROM jou WHERE condate >= '2019-06-07' AND condate <= '2020-06-07' AND file='" + fname + "'", null);
                //StringBuffer buffer = new StringBuffer();
                while (c4.moveToNext()) {
                    email = c4.getString(0);
                    first = c4.getString(1);
                    date = c4.getString(2);
                    author = c4.getString(3);
                    place = c4.getString(4);
                    title = c4.getString(5);
                    year = c4.getString(6);
                    nam = c4.getString(7);
                    page = c4.getString(8);
                    type = c4.getString(9);
                    inde = c4.getString(10);
                    file = c4.getString(11);
                    vol = c4.getString(12);
                    im = c4.getString(13);

//https://script.google.com/macros/s/AKfycbzQtwVEvq0qISJroOCMnJeyqpMAGaElh4puWz8NWPTfPc2bQ48/exec
                    addtosheet1("https://script.google.com/macros/s/AKfycbzQtwVEvq0qISJroOCMnJeyqpMAGaElh4puWz8NWPTfPc2bQ48/exec");
                }*/
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
        StorageReference reference=storageReference.child("jou/"+year+"/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete());
                Uri url=uri.getResult();
                db upload=new db(fil.getText().toString(),url.toString());
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
    public void firstor(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.first:
                if (checked)
                    branch="Yes";
                // Pirates are the best
                break;
            case R.id.nott:
                if (checked)
                    branch="No";
                // Ninjas rule
                break;
        }
    }
    public void indexi(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.reuters:
                if (checked)
                    index="Thomson Reuters";
                // Pirates are the best
                break;
            case R.id.scopus:
                if (checked)
                    index="Scopus";
                // Ninjas rule
                break;
            case R.id.radioButton:
                if (checked)
                    index="Web of Science";
                // Pirates are the best
                break;
            case R.id.radioButton2:
                if (checked)
                    index="None";
                // Ninjas rule
                break;
        }
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(journal.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void addtosheet1(String url)
    {//https://script.google.com/macros/s/AKfycbxFIr17xGIHQTAYUmjen1-b5iGEe7KxI2-7vbdM/exec
        //https://script.google.com/macros/s/AKfycbzL7Yzt3xt7jWmuUqKz_WeSMpki0lPz2n6E7pAxlOifQP_IKHQ/exec
        //https://script.google.com/macros/s/AKfycbzL7Yzt3xt7jWmuUqKz_WeSMpki0lPz2n6E7pAxlOifQP_IKHQ/exec

        //https://script.google.com/macros/s/AKfycbzEMwp1P6Wa5ybN87le2bknfiTpcrB9Thz3sTuKPiPct4ti1CM9/exec
        final ProgressDialog progressDialog=ProgressDialog.show(this,"Processing","Please wait");
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //Toast.makeText(viewbyquery.this,response,Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        //startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("email",email);
                parmas.put("first",first);
                parmas.put("condate",date);
                parmas.put("author",author);
                parmas.put("place",place);
                parmas.put("title",title);
                parmas.put("year",year);
                parmas.put("conname",nam);
                parmas.put("page",page);
                parmas.put("type",type);
                parmas.put("index",inde);
                parmas.put("file",file);
                parmas.put("vol",vol);
                parmas.put("im",im);
//https://script.google.com/macros/s/AKfycbzL7Yzt3xt7jWmuUqKz_WeSMpki0lPz2n6E7pAxlOifQP_IKHQ/exec
                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }
}
