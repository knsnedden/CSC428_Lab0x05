package com.company;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.Math;
import java.util.Random;
import java.math.BigInteger; // using this because of time issues with MyBigInteger, as seen in performace(), to get a more accurate performance test with fib

public class Main {

    public static void main(String[] args) {
       //performance();

    }

    public static BigInteger fibLoopBig(int X){
        BigInteger a = new BigInteger("0"), b = new BigInteger("1"), sum = new BigInteger("0");

        for (int i = 1; i < X; ++i){
            sum = a.add(b);
            a = b;
            b = sum;
        }

        return sum;
    }

    public static void performance(){
        int N = 1, prevN = 0;
        boolean keepGoing = true;
        long timeBefore = 0, timeAfter = 0, pTime = 0, mTime = 0, tTime = 0, pTimePrev = 0, mTimePrev = 0, tTimePrev = 0;
        float pDr, mDr, tDr, eDr;
        double maxTime = Math.pow(2,33);
        String str1 = "", str2 = "";

        System.out.println("         Plus()                         Minus()                      Times()");
        System.out.println("   N   |    Time    |  DR  | Exp. DR |    Time    |  DR  | Exp. DR |    Time    |  DR  | Exp. DR |");

        while (keepGoing){
            System.out.printf("%6d |", N);

            Random ranNum = new Random();

            for (int i = 0; i < N; ++i) {
                int tmp = ranNum.nextInt(10) + 48;
                char insert = (char) tmp;
                str1 = str1 + String.valueOf(insert);
                tmp = ranNum.nextInt(10) + 48;
                insert = (char) tmp;
                str2 = str2 + String.valueOf(insert);
            }

            MyBigInteger pfirst = new MyBigInteger(str1), psecond = new MyBigInteger(str2);
            MyBigInteger mfirst = new MyBigInteger(str1), msecond = new MyBigInteger(str2);
            MyBigInteger tfirst = new MyBigInteger(str1), tsecond = new MyBigInteger(str2);

            if (pTime < maxTime){
                timeBefore = getCpuTime();
                pfirst.Plus(psecond);
                timeAfter = getCpuTime();
                pTime = timeAfter - timeBefore;
                System.out.printf("%11d |", pTime);
                if (pTimePrev == 0){
                    System.out.printf("  na  |    na   |");
                }else{ // linear time complexity
                    pDr = (float)pTime/(float)pTimePrev;
                    eDr = (float)(N/prevN);
                    System.out.printf("%5.2f | %7.2f |", pDr, eDr);
                }
                pTimePrev = pTime;
            } else{
                System.out.printf("    --      |  --  |   --    |");
            }

            if (mTime < maxTime){
                timeBefore = getCpuTime();
                mfirst.Minus(msecond);
                timeAfter = getCpuTime();
                mTime = timeAfter - timeBefore;
                System.out.printf("%11d |", mTime);
                if (mTimePrev == 0){
                    System.out.printf("  na  |    na   |");
                }else{ // linear time complexity
                    mDr = (float)mTime/(float)mTimePrev;
                    eDr = (float)(N/prevN);
                    System.out.printf("%5.2f | %7.2f |", mDr, eDr);
                }
                mTimePrev = mTime;
            } else{
                System.out.printf("    --      |  --  |   --    |");
            }

            if (tTime < maxTime){
                timeBefore = getCpuTime();
                tfirst.Times(tsecond);
                timeAfter = getCpuTime();
                tTime = timeAfter - timeBefore;
                System.out.printf("%11d |", tTime);
                if (tTimePrev == 0){
                    System.out.printf("  na  |    na   |");
                }else{ // linear time complexity
                    tDr = (float)tTime/(float)tTimePrev;
                    eDr = (float)(Math.pow(N,2)/Math.pow(prevN,2));
                    System.out.printf("%5.2f | %7.2f |", tDr, eDr);
                }
                tTimePrev = tTime;
            } else{
                System.out.printf("    --      |  --  |   --    |");
            }

            System.out.println();
            if (pTime > maxTime && mTime > maxTime && tTime > maxTime){
                keepGoing = false;
            }
            prevN = N;
            N = N*2;
        }
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

    public static long getCpuTime(){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }
}
