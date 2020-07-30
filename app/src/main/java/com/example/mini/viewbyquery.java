package com.example.mini;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class viewbyquery extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
private Spinner spinner;
Date dat;
SQLiteDatabase db;
 String ab,bc;
 DownloadManager dm;
    private static final int PERMISSION_STORAGE_CODE =1000 ;
 Long queueid;
 String nam,email,first,title,author,type,place,date,file,year,page,inde,vol,im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbyquery);
        spinner=findViewById(R.id.spinner);
        String[] query=getResources().getStringArray(R.array.queries);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,query);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(viewbyquery.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
public void startDownloading()
{
   // DownloadManager.Request request=new DownloadManager.Request(Uri.parse("https://docs.google.com/spreadsheets/d/1IHfv-N6FiH9-5rylEyEyzLblWlCUIux26tGPC9XGHM4/edit?usp=sharing"));
    DownloadManager.Request request=new DownloadManager.Request(Uri.parse("https://docs.google.com/spreadsheets/d/1IHfv-N6FiH9-5rylEyEyzLblWlCUIux26tGPC9XGHM4/edit#gid=0.xls"));
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
    request.setTitle("Sheets");
    request.setDescription("Downloading file......");
    request.allowScanningByMediaScanner();
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Down.pdf"+System.currentTimeMillis());
    DownloadManager manager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
    manager.enqueue(request);
}
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(viewbyquery.this,parent.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
        if(parent.getId()==R.id.spinner)
        {
            String value=parent.getItemAtPosition(position).toString();
            Toast.makeText(viewbyquery.this,value,Toast.LENGTH_LONG).show();
            String fo="01/04/2020";
            try {
                dat = new SimpleDateFormat("dd/MM/yyyy").parse(fo);
            }
            catch (Exception e)
            {

            }
            if(value.equals("View All the Conference Files uploaded"))
            {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                    }
                    else
                    {
                        startDownloading();
                    }
                }
                else
                {
                    startDownloading();
                }
            }

            if(value.equals("View All the conference files uploaded for the past 6 months"))
            {
                //https://script.google.com/macros/s/AKfycbyOADedBNgu7KQhGsJeVuPpDJuY53jbiGzOPnbRCaON1caIVQM/exec
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                    }
                    else
                    {
                        startDownloading();
                    }
                }
                else
                {
                    startDownloading();
                }
            }
            if(value.equals("View All the conference files uploaded for the past 12 months"))
            {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                    }
                    else
                    {
                        startDownloading();
                    }
                }
                else
                {
                    startDownloading();
                }
            }
            if(value.equals("View All the journals uploaded"))
            {
                db = openOrCreateDatabase("FacultyDB", Context.MODE_PRIVATE, null);
                //db.execSQL("CREATE TABLE IF NOT EXISTS publish1(username VARCHAR,branch VARCHAR,Date VARCHAR,indexing VARCHAR,first VARCHAR,month VARCHAR);");
                // Cursor c1 = db.rawQuery("SELECT * FROM publish1", null);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                    }
                    else
                    {
                        startDownloading();
                    }
                }
                else
                {
                    startDownloading();
                }
            }
            if(value.equals("View All the journal files uploaded for the past 6 months"))
            {
                db = openOrCreateDatabase("FacultyDB", Context.MODE_PRIVATE, null);
                //db.execSQL("CREATE TABLE IF NOT EXISTS publish1(username VARCHAR,branch VARCHAR,Date VARCHAR,indexing VARCHAR,first VARCHAR,month VARCHAR);");
                // Cursor c1 = db.rawQuery("SELECT * FROM publish1", null);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                    }
                    else
                    {
                        startDownloading();
                    }
                }
                else
                {
                    startDownloading();
                }
            }
            if(value.equals("View All the journal files uploaded for the past 12 months")) {
                db = openOrCreateDatabase("FacultyDB", Context.MODE_PRIVATE, null);
                //db.execSQL("CREATE TABLE IF NOT EXISTS publish1(username VARCHAR,branch VARCHAR,Date VARCHAR,indexing VARCHAR,first VARCHAR,month VARCHAR);");
                // Cursor c1 = db.rawQuery("SELECT * FROM publish1", null);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                    }
                    else
                    {
                        startDownloading();
                    }
                }
                else
                {
                    startDownloading();
                }
            }
        }
    }

  /*  public void addtosheet1(String url)
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
*/    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
