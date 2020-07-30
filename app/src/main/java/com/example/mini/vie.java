package com.example.mini;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class vie extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String branch="",yea="";
    ListView myPDFListView;
    DatabaseReference databaseReference;
    List<db> uploadPDFs;
    Button home;
    Spinner spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vie);
        home=(Button)findViewById(R.id.button9);
        spin=(Spinner)findViewById(R.id.spinner2);
        String[] query=getResources().getStringArray(R.array.acad_year);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,query);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(vie.this,branch+" "+yea, Toast.LENGTH_LONG).show();
                if(branch.equals("")|| yea.equals(""))
                {
                    Toast.makeText(vie.this, "Please fill all the particulars", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent it=new Intent(vie.this,view1.class);
                it.putExtra("type",branch);
                it.putExtra("yea",yea);
                startActivity(it);
            }
        });
       /* home=(Button)findViewById(R.id.button9);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(view.this,MainActivity.class);
                startActivity(it);
            }
        });*/
        /*myPDFListView=(ListView)findViewById(R.id.mylist);
        uploadPDFs=new ArrayList<>();
        viewAllFiles();
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
        });
    }

    private void viewAllFiles() {
        databaseReference= FirebaseDatabase.getInstance().getReference("conference/2019-2020");
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
        });*/
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton3:
                if (checked)
                    branch="jou";
                // Pirates are the best
                break;
            case R.id.radioButton4:
                if (checked)
                    branch="con";
                // Ninjas rule
                break;
        }
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner2) {
            String value = parent.getItemAtPosition(position).toString();
            if (value.equals("Choose Academic Year")) {
                //Toast.makeText(vie.this, "Please choose one academic year", Toast.LENGTH_LONG).show();
                yea="";
                //return;
            }
            else
            {
                yea=parent.getSelectedItem().toString();

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
