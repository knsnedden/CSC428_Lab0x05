package com.company;

import java.util.Arrays;



public class Main {

    public static void main(String[] args) {
       verification();
    }

    public static void verification() {
        System.out.println("Small Numbers:");

        MyBigInteger atest = new MyBigInteger("12"), atest2 = new MyBigInteger("3333");
        MyBigInteger stest = new MyBigInteger("12"), stest2 = new MyBigInteger("3333");
        MyBigInteger mtest = new MyBigInteger("12"), mtest2 = new MyBigInteger("3333");

        System.out.printf("Integers: %s %s\n", atest.ToString(), atest2.ToString());
        System.out.printf("Addition with MyBigInteger: %s || with standard addition: %d\n", atest.Plus(atest2).ToString(), 12+3333);
        System.out.printf("Subtraction with MyBigInteger: %s || with standard subtraction: %d\n", stest.Minus(stest2).ToString(), 12-3333);
        System.out.printf("Multiplication with MyBigInteger: %s || with standard multiplication: %d\n", mtest.Times(mtest2).ToString(), 12*3333);

        long num = 2112222222;

        atest = new MyBigInteger("2112222222");
        atest2 = new MyBigInteger("3333");
        stest = new MyBigInteger("2112222222");
        stest2 = new MyBigInteger("3333");
        mtest = new MyBigInteger("2112222222");
        mtest2 = new MyBigInteger("3333");

        System.out.printf("\nLong integers: %s %s\n", atest.ToString(), atest2.ToString());
        System.out.printf("Addition with MyBigInteger: %s || with standard addition: %d\n", atest.Plus(atest2).ToString(), num+3333);
        System.out.printf("Subtraction with MyBigInteger: %s || with standard subtraction: %d\n", stest.Minus(stest2).ToString(), num-3333);
        System.out.printf("Multiplication with MyBigInteger: %s || with standard multiplication: %d\n", mtest.Times(mtest2).ToString(), num*3333);

        atest = new MyBigInteger("2112222222000000000000000000000000000000000000000000001");
        atest2 = new MyBigInteger("2000000000000000000000000000000000000000000001");
        stest = new MyBigInteger("2112222222000000000000000000000000000000000000000000001");
        stest2 = new MyBigInteger("2000000000000000000000000000000000000000000001");
        mtest = new MyBigInteger("2112222222000000000000000000000000000000000000000000001");
        mtest2 = new MyBigInteger("100000");

        System.out.printf("\nVery long numbers (can no longer do standard arithmetic): %s\n", atest.ToString());
        System.out.printf("Addition (of %s) with MyBigInteger: %s\n", atest2.ToString(), atest.Plus(atest2).ToString());
        System.out.printf("Subtraction (of %s) with MyBigInteger: %s\n", stest2.ToString(), stest.Minus(stest2).ToString());
        System.out.printf("Multiplication (of %s) with MyBigInteger: %s", mtest2.ToString(), mtest.Times(mtest2).ToString());
    }
}
