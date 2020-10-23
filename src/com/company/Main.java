package com.company;

import java.util.Arrays;



public class Main {

    public static void main(String[] args) {
        MyBigInteger test = new MyBigInteger("1234"), test2 = new MyBigInteger("1730");

        System.out.println(test.Plus(test2).ToString());
        test = new MyBigInteger("1234");
        test2 = new MyBigInteger("1730");
        System.out.println(test.Minus(test2).ToString());

    }
}
