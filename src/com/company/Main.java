package com.company;

import java.util.Arrays;



public class Main {

    public static void main(String[] args) {
        MyBigInteger test = new MyBigInteger("99999999999999999999999999"), test2 = new MyBigInteger("999999999999999999999999999999999999999999");

        System.out.println(test.Plus(test2).ToString());

    }
}
