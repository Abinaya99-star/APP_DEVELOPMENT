package com.example.mini;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class view extends AppCompatActivity {
    private static final int PERMISSION_STORAGE_CODE =1000 ;
    ListView myPDFListView;
DatabaseReference databaseReference;
String url,uu,file;
List<db> uploadPDFs;
Button home,down,view;
String year,type;
StorageReference storageReference;
StorageReference ref;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        down=(Button)findViewById(R.id.button12);
        view=(Button)findViewById(R.id.button13);
        Intent it=getIntent();
        type=it.getStringExtra("type");
        year=it.getStringExtra("year");
        uu=it.getStringExtra("URL");
        file=it.getStringExtra("file");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uu));
                Intent j = Intent.createChooser(intent, "Choose an application to open with");
                startActivity(j);
            }
        });
        home=(Button)findViewById(R.id.button14);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(view.this,MainActivity.class);
                startActivity(it);
            }
        });
       down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
               // Toast.makeText(view.this,"domemkr",Toast.LENGTH_LONG).show();
                //download();
            }
        });

       // type=it.getStringExtra("type");
        //year=it.getStringExtra("yea");
        //year="2017-2018";
       /* home=(Button)findViewById(R.id.button9);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(view.this,MainActivity.class);
                startActivity(it);
            }
        });*/
        //myPDFListView=(ListView)findViewById(R.id.mylist);
      /*  uploadPDFs=new ArrayList<>();
 //       viewAllFiles();
        myPDFListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db upload=uploadPDFs.get(position);
                //MimeTypeMap myMime=MimeTypeMap.getSingleton();
                Intent intent=new Intent();
                intent.setType(Intent.ACTION_VIEW);
                //String mimetype=myMime.getMimeTypeFromExtension(fileEx)
                intent.setData(Uri.parse(upload.getUrl()));
                Intent j=Intent.createChooser(intent,"Choose an application to open with");
                startActivity(j);
            }
        });*/
    }

    private void startDownloading() {
        storageReference= FirebaseStorage.getInstance().getReference();

       // Toast.makeText(view.this,file,Toast.LENGTH_LONG).show();
       // Toast.makeText(view.this,uu,Toast.LENGTH_LONG).show();
        //ref=storageReference.child(type+"/"+year+"/"+file+".pdf");
       // ref=storageReference.child("con/2016-2017/identity.pdf");
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(uu));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle(year);
        request.setDescription("Downloading file......");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Down.pdf"+System.currentTimeMillis());
        DownloadManager manager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);
       /* ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri.toString();
                //url="https://firebasestorage.googleapis.com/v0/b/mini-project-8b4e5.appspot.com/o/con%2F2016-2017%2F1591458067462.pdf?alt=media&token=95b0f69c-d347-4f19-b5ab-bd452d7dbbfb";
                //Toast.makeText(view.this,year+" "+type,Toast.LENGTH_LONG).show();
                DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                request.setTitle(year);
                request.setDescription("Downloading file......");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());
                DownloadManager manager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                manager.enqueue(request);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(view.this,ref.getDownloadUrl().toString(),Toast.LENGTH_LONG).show();
            }
        });*/

        //url="https://firebasestorage.googleapis.com/v0/b/mini-project-8b4e5.appspot.com/o/con%2F2015-20161591289575808.pdf?alt=media&token=e7be8338-58e3-4860-87a3-a32bfe5db560";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_STORAGE_CODE:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    startDownloading();
                }
            }
        }
    }


    /*public void download()
{
    storageReference= FirebaseStorage.getInstance().getReference();
    ref=storageReference.child("312217205003.pdf");
   // File local=File.createTempFile("images","pdf");
    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(Uri uri) {
            String url=uri.toString();
           // gs=FirebaseDatabase.getInstance().getReferenceFromUrl("gs://mini-project-8b4e5.appspot.com/312217205003.pdf");
            //Toast.makeText(view.this,url,Toast.LENGTH_LONG).show();
            downloadFile("312217205003",url);
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(view.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    });
}
public void downloadFile(String fileName, String url)
{
    //DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    Uri uri=Uri.parse(url);
    DownloadManager.Request request=new DownloadManager.Request(uri);
    String temp=fileName.replace("","_");
    request.setTitle(temp);
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
    {
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
    }
    request.setVisibleInDownloadsUi(false);
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,temp+".pdf");
    request.setMimeType("application.pdf");
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
    DownloadManager downloadManager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
    long downloadID=downloadManager.enqueue(request);
    Intent i=new Intent();
    i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
    startActivity(i);
    Toast.makeText(view.this,"asdf",Toast.LENGTH_LONG).show();
    //request.setDestinationInExternalFilesDir(cntext,destinationDirectory,fileName+fileExtension);
    //downloadManager.enqueue(request);
}
    private void viewAllFiles() {
        databaseReference= FirebaseDatabase.getInstance().getReference(type+"/"+year);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                {
                    db upload=postSnapShot.getValue(db.class);
                    uploadPDFs.add(upload);
                }
                String[] uploads=new String[uploadPDFs.size()];
                for(int i=0;i<uploads.length;i++)
                {
                    uploads[i]=uploadPDFs.get(i).getName();
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,uploads){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View vi=super.getView(position,convertView,parent);
                        TextView myText=(TextView)vi.findViewById(android.R.id.text1);
                        myText.setTextColor(Color.BLACK);
                        return vi;
                    }
                };
                myPDFListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}
