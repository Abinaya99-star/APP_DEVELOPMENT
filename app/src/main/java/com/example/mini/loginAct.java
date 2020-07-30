package com.example.mini;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;

public class loginAct extends Fragment {
    Button bt;
    EditText email,pass;
    //FirebaseAuth fAuth;
    Switch studfac;
    String em,passs;
    //int flag;
    //SQLiteDatabase db;
    String status;

    String reg=" ",name=" ";
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_login, container, false);
        bt=(Button)v.findViewById(R.id.button);
        email=(EditText)v.findViewById(R.id.editText);
        pass=(EditText)v.findViewById(R.id.editText2);
        studfac=(Switch)v.findViewById(R.id.switch1);
       // Toast.makeText(getActivity(),"Auth",Toast.LENGTH_SHORT).show();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean che=studfac.isChecked();
                //Toast.makeText(getActivity(),"abc "+che,Toast.LENGTH_SHORT).show();
            if(che.equals(true))
            {
                status="s";
                }
            else {
                status="f";
                }
                em=email.getText().toString().trim();
                passs=pass.getText().toString().trim();
                if (em.isEmpty()) {
                    email.setError("Please enter mail id");
                    email.requestFocus();
                    return;
                }
                if (passs.isEmpty()){// passs.length()<6) {
                    pass.setError("Please enter the password");
                    pass.requestFocus();
                    return;
                }
                if(passs.length()<6)
                {
                    pass.setError("Password must have atleast 6 characters");
                    pass.requestFocus();
                    return;
                }
                Intent it = new Intent(getActivity(),FacultyRegis.class);
                it.putExtra("em",em);
                it.putExtra("reg",reg);
                it.putExtra("nam",name);
                it.putExtra("pass",passs);
                it.putExtra("cont",status);
               // Toast.makeText(getActivity(),em,Toast.LENGTH_SHORT).show();
                startActivity(it);
            }
            });
        return v;
    }
}
