package com.company;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.Math;
import java.util.Random;
import java.math.BigInteger; // using this because of time issues with MyBigInteger, as seen in performace(), to get a more accurate performance test with fib
import java.math.BigDecimal;

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

    public static BigInteger fibMatrixBig(int X){
        BigInteger[][] arr = new BigInteger[][]{{new BigInteger("1"),new BigInteger("1")},{new BigInteger("1"),new BigInteger("0")}};


        if (X == 0){
            BigInteger zero = new BigInteger("0");
            return zero;
        }
        matrixPower(arr,(int)X-1);

        return arr[0][0];
    }

    public static void matrixPower(BigInteger[][] arr, int n){
        if (n == 0 || n == 1){
            return;
        }

        BigInteger[][] place = new BigInteger[][]{{new BigInteger("1"),new BigInteger("1")},{new BigInteger("1"),new BigInteger("0")}};

        matrixPower(arr,n/2); // recursive: find the the smallest power and work up; n/2 because f(n+1), f(n), and f(n-1) are found

        // multiply the matrix by itself
        BigInteger a,b,c,d;
        a = (arr[0][0].multiply(arr[0][0])).add(arr[0][1].multiply(arr[1][0]));
        b = (arr[0][0].multiply(arr[0][1])).add(arr[0][1].multiply(arr[1][1]));
        c = (arr[1][0].multiply(arr[0][0])).add(arr[1][1].multiply(arr[1][0]));
        d = (arr[1][0].multiply(arr[0][1])).add(arr[1][1].multiply(arr[1][1]));

        // move the results into the matrix
        arr[0][0] = a;
        arr[0][1] = b;
        arr[1][0] = c;
        arr[1][1] = d;

        // if n is odd, this gives correct answer
        if (n%2 != 0){
            BigInteger w,x,y,z;
            w = (arr[0][0].multiply(place[0][0])).add(arr[0][1].multiply(place[1][0]));
            x = (arr[0][0].multiply(place[0][1])).add(arr[0][1].multiply(place[1][1]));
            y = (arr[1][0].multiply(place[0][0])).add(arr[1][1].multiply(place[1][0]));
            z = (arr[1][0].multiply(place[0][1])).add(arr[1][1].multiply(place[1][1]));

            arr[0][0] = w;
            arr[0][1] = x;
            arr[1][0] = y;
            arr[1][1] = z;
        }


    }

    public static double fibFormula(int X){
        double result = 0, a, b;

        a = (1+Math.sqrt(5))/2;
        b = (1-Math.sqrt(5))/2;

        result = ((Math.pow(a,X))-Math.pow(b,X))/Math.sqrt(5);

        return result;
    }

    public static BigDecimal fibFormulaBig(int X){
        BigDecimal result = new BigDecimal("0"), a = new BigDecimal((1+Math.sqrt(5))/2), b = new BigDecimal((1-Math.sqrt(5))/2), sq = new BigDecimal(Math.sqrt(5));

        result = (a.pow(X).subtract(b.pow(X))).divide(sq);

        return result;


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
