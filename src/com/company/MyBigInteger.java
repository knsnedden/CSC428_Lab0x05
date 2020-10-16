package com.company;

public class MyBigInteger {
    String Value;

    public MyBigInteger(){
        Value = "0";
    }

    public MyBigInteger(String x){
        Value = x;
    }

    public String ToString(){
        return Value;
    }

    public MyBigInteger Plus(MyBigInteger x){
        MyBigInteger result = new MyBigInteger();

        return result;
    }

    public MyBigInteger Times(MyBigInteger x){
        MyBigInteger result = new MyBigInteger();

        return result;
    }
}
