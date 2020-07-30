package com.example.mini;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class helpact extends Fragment {
    Button bt;
    EditText help;
    String hel;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_help, container, false);
        bt = (Button) v.findViewById(R.id.button3);
        help=(EditText)v.findViewById(R.id.editText9);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hel=help.getText().toString().trim();
                if (hel.isEmpty()) {
                    help.setError("Please enter your query");
                    help.requestFocus();
                }
                if(hel.equals("How to register as a student"))
                {
                    help.setText("Go to Downward navigation bar -> Then click Student Register ->Once you registered you will be logged in.");
                }
            }
        });
        return v;
    }
}
