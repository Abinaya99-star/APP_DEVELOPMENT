package com.example.mini;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.fragment.app.Fragment;

import java.util.jar.Attributes;

public class RegisterActivity extends Fragment {
    Button bt;
    //FirebaseAuth fAuth;

    EditText name,email,reg,pass,repass;
    TextView tv;
    String nam,em,regno,passs;
    ProgressBar p;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_register, container, false);
        bt=(Button)v.findViewById(R.id.button2);
        name=(EditText)v.findViewById(R.id.editText3);
        email=(EditText)v.findViewById(R.id.editText4);
        reg=(EditText)v.findViewById(R.id.editText5);
        pass=(EditText)v.findViewById(R.id.editText6);
        repass=(EditText)v.findViewById(R.id.editText7);
       // tv=(TextView)v.findViewById(R.id.textView5);
        p=(ProgressBar)v.findViewById(R.id.progressBar);
      //  fAuth=FirebaseAuth.getInstance();
        //tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //tv.setOnClickListener(new View.OnClickListener() {
            //@Override
          //  public void onClick(View v) {
          //      Intent iat=new Intent(getActivity(),studlog.class);
            //    startActivity(iat);
            //}
        //});
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regno = reg.getText().toString().trim();
                nam = name.getText().toString().trim();
                em = email.getText().toString().trim();
                passs = pass.getText().toString().trim();
                if (em.isEmpty()) {
                    email.setError("Please enter mail id");
                    email.requestFocus();
                }
                if (passs.isEmpty() | passs.length() < 6) {
                    pass.setError("Please enter the password");
                    pass.requestFocus();
                }
                if (repass.getText().toString().isEmpty()) {
                    repass.setError("Please enter the password again");
                    repass.requestFocus();
                }
                if (!passs.equals(repass.getText().toString())) {
                    repass.setError("Password didn't match");
                    repass.requestFocus();
                }
                p.setVisibility(View.VISIBLE);
                Intent it = new Intent(getActivity(),FacultyRegis.class);
                it.putExtra("em",em);
                it.putExtra("reg",regno);
                it.putExtra("nam",nam);
                it.putExtra("pass",passs);
                it.putExtra("cont","a");
                //Toast.makeText(getActivity(),"Authenticati",Toast.LENGTH_SHORT).show();
                startActivity(it);
            }
        });
                return v;
            }

}
