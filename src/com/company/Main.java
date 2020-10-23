package com.company;

import java.util.Arrays;



public class Main {

    public static void main(String[] args) {
        MyBigInteger test = new MyBigInteger("11477"), test2 = new MyBigInteger("832");

        System.out.println(test2.Times(test).ToString());

    }
}
