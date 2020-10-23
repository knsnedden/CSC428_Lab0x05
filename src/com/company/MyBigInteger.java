package com.company;

import java.util.Arrays;

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
        boolean neg = false;

        // test for negative values
        if (this.Value.charAt(0) == '-' && x.Value.charAt(0) == '-'){ // if both negative, remove the negatives, add them as normal, and add "-" at end
            neg = true;
            this.Value = this.Value.substring(1);
            x.Value = x.Value.substring(1);
        } else if (this.Value.charAt(0) == '-'){ // if one is negative, just subtract
            this.Value = this.Value.substring(1);
            return x.Minus(this);
        } else if (x.Value.charAt(0) == '-'){ // if one is negative, just subtract
            x.Value = x.Value.substring(1);
            return this.Minus(x);
        }

        int thisLen = this.Value.length();
        int xLen = x.Value.length();

        // pad the smaller value with zeroes
        if (thisLen > xLen){
            int lenDif = thisLen - xLen;
            char[] pad = new char[lenDif];
            Arrays.fill(pad, '0');
            String fill = new String(pad);

            x.Value = fill + x.Value;
        } else if (xLen > thisLen){
            int lenDif = xLen - thisLen;
            char[] pad = new char[lenDif];
            Arrays.fill(pad, '0');
            String fill = new String(pad);

            this.Value = fill + this.Value;
        }

        int carry = 0;
        for (int i = this.Value.length()-1; i >= 0; --i){

            // convert character to correct int and add together
            int thisChar = convertToInt(this.Value.charAt(i));
            int xChar = convertToInt(x.Value.charAt(i));
            int sum = thisChar + xChar + carry;

            carry = 0; // reset carry

            // test for carry
            if (sum > 9){
                carry = 1;
                sum = sum - 10;
            }

            // convert back to character and insert at beginning of result string
            char hold = convertToChar(sum);
            String insert = String.valueOf(hold);
            if (i == this.Value.length()-1){
                result.Value = insert; // first instance needs to replace the "0" in result
            }else{
                result.Value = insert + result.ToString();
            }
        }

        if (carry == 1){  // if there is a leftover carry, add it to beginning
            char hold = convertToChar(1);
            String insert = String.valueOf(hold);
            result.Value = insert + result.ToString();
        }
        if (neg){ // if both negative, add the negative back in
            result.Value = "-" + result.ToString();
        }

        return result;
    }

    public MyBigInteger Times(MyBigInteger x){
        MyBigInteger result = new MyBigInteger();

        return result;
    }

    public MyBigInteger Minus(MyBigInteger x){
        MyBigInteger result = new MyBigInteger();

        return result;
    }

    public int convertToInt(char a){
        return a - 48;
    }

    public char convertToChar(int a) {
        return (char)(a + 48);
    }
}
