package com.example.mini;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;
import java.util.List;

public class view1 extends AppCompatActivity {
    ListView list;
    List<db> uploadPDFs;
    DatabaseReference databaseReference;
    String type,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view1);
        list = (ListView) findViewById(R.id.myList);
        Intent it=getIntent();
        type=it.getStringExtra("type");
        year=it.getStringExtra("yea");
        //myPDFListView=(ListView)findViewById(R.id.mylist);
        uploadPDFs = new ArrayList<>();
        viewAllFiles();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db upload = uploadPDFs.get(position);
                //MimeTypeMap myMime=MimeTypeMap.getSingleton();
               // Intent intent = new Intent();
                //intent.setType(Intent.ACTION_VIEW);
                //String mimetype=myMime.getMimeTypeFromExtension(fileEx)
                //intent.setData(Uri.parse(upload.getUrl()));
                //intent.putExtra("URL",upload.getUrl());
                //Intent j = Intent.createChooser(intent, "Choose an application to open with");

                Intent j=new Intent(view1.this,view.class);
                j.putExtra("URL",Uri.parse(upload.getUrl()).toString());
                j.putExtra("type",type);
                j.putExtra("year",year);
                j.putExtra("file",upload.getName());
               // Toast.makeText(view1.this,Uri.parse(upload.getUrl()).toString(),Toast.LENGTH_LONG).show();
                startActivity(j);

            }
        });
    }

    private void viewAllFiles() {
        databaseReference = FirebaseDatabase.getInstance().getReference(type+"/"+year);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    db upload = postSnapShot.getValue(db.class);
                    uploadPDFs.add(upload);
                }
                String[] uploads = new String[uploadPDFs.size()];
                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadPDFs.get(i).getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        //View row=layoutInflater.inflate(R.layout.row,parent,false);
                        View vi = super.getView(position, convertView, parent);
                        TextView myText = (TextView) vi.findViewById(android.R.id.text1);
                        //Button btn=(Button)vi.findViewById(android.R.id.button1);
                        //btn.setText("DOWNLOAD");
                        //btn.setTextColor(Color.BLACK);
                        myText.setTextColor(Color.BLACK);
                        return vi;
                    }
                };
                list.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

