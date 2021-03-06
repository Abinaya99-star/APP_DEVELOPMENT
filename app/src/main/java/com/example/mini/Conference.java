package com.example.mini;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Conference extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
String branch="",date="",auth="",pl="",tim="",pag="",name,yea;
EditText author,dat,nam_of,place,title;
Button bt;
private Spinner spin;
DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference);
        final Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        dat=(EditText)findViewById(R.id.editText12);
        bt=(Button)findViewById(R.id.button9);
        author=(EditText)findViewById(R.id.editText10);
        place=(EditText)findViewById(R.id.editText11);
        nam_of=(EditText)findViewById(R.id.editText13);
        title=(EditText)findViewById(R.id.editText16);
        spin=(Spinner)findViewById(R.id.spinner2);
        String[] query=getResources().getStringArray(R.array.acad_year);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,query);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth=author.getText().toString();
                pl=place.getText().toString();
                tim=title.getText().toString();
                pag=nam_of.getText().toString();
                if(branch.equals("") || date.equals("") || auth.equals("") || pl.equals("") || tim.equals("") ||pag.equals("") || yea.equals(""))
                {
                    Toast.makeText(Conference.this,"Please fill all the fields",Toast.LENGTH_LONG).show();
                    return;
                }
                //Toast.makeText(Conference.this,year,Toast.LENGTH_LONG).show();
                Intent it = getIntent();
                name = it.getStringExtra("emai");
                //Toast.makeText(Conference.this,yea,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Conference.this,Con2.class);
                intent.putExtra("name",name);
                intent.putExtra("branch",branch);
                intent.putExtra("dat",date);
                intent.putExtra("author",auth);
                intent.putExtra("place",pl);
                intent.putExtra("title",tim);

                intent.putExtra("year",yea);
                intent.putExtra("name_of",pag);
                startActivity(intent);
            }
        });
        dat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Conference.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Calendar newDate=calendar.getTime();
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = day+ "/" +month+ "/" +year;
                dat.setText(date);
            }
        };
        }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.journ:
                if (checked)
                    branch="International Conference";
                // Pirates are the best
                break;
            case R.id.journal:
                if (checked)
                    branch="National Conference";
                // Ninjas rule
                break;
        }
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner2) {
            String value = parent.getItemAtPosition(position).toString();
            if (value.equals("Choose Academic Year")) {
               // Toast.makeText(Conference.this, "Please choose one academic year", Toast.LENGTH_LONG).show();
                //return;
                yea="";
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
}

