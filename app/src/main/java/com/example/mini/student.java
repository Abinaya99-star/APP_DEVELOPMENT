package com.example.mini;

public class student {
    private String name,email,pass;
    private int reg;
    public student(String name,int reg,String email,String pass){
        this.name=name;
        this.reg=reg;
        this.email=email;
        this.pass=pass;
    }
    public String getName()
    {
        return name;
    }
    public String getEmail()
    {
        return email;
    }
    public String getPass()
    {
        return pass;
    }
    public Integer getreg()
    {
        return reg;
    }
}
