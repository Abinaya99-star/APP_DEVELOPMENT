package com.example.mini;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class db {
    public String name;
    public String url;
 public db()
 {

 }
 public db(String name,String url)
 {
     this.name=name;
     this.url=url;
 }
    public String getName()
 {
     return name;
 }

 public String getUrl()
 {
     return url;
 }

}
