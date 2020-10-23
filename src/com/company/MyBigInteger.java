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
        boolean neg = false;

        if (this.Value.charAt(0) == '-' && x.Value.charAt(0) != '-'){
            this.Value = this.Value.substring(1);
            neg = true;
        } else if (this.Value.charAt(0) != '-' && x.Value.charAt(0) == '-'){
            x.Value = x.Value.substring(1);
            neg = true;
        } else if (this.Value.charAt(0) == '-' && x.Value.charAt(0) == '-'){
            this.Value = this.Value.substring(1);
            x.Value = x.Value.substring(1);
        }

        int thisLen = this.Value.length(), xLen = x.Value.length();
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

        int p = 0, prod, carry = 0;
        MyBigInteger storing = new MyBigInteger();
        for (int i = this.Value.length()-1; i >= 0; --i){
            int thisVal = convertToInt(this.Value.charAt(i));
            for (int j = x.Value.length()-1; j >= 0; --j){
                int xVal = convertToInt(x.Value.charAt(j));
                prod = thisVal * xVal + carry;

                if (prod > 9){
                    carry = prod/10;
                    prod = prod%10;
                } else{
                    carry = 0;
                }

                char hold = convertToChar(prod);
                String insert = String.valueOf(hold);
                if (j == x.Value.length()-1){
                    storing.Value = insert;
                } else{
                    storing.Value = insert + storing.ToString();
                }
            }

            for (int k = 0; k < p; ++k){
                storing.Value += "0";
            }
            result = result.Plus(storing);

            storing.Value = "0";
            p++;
        }

        if (neg){
            result.Value = "-" + result.ToString();
        }

        return result;
    }

    public MyBigInteger Minus(MyBigInteger x){
        MyBigInteger result = new MyBigInteger();

        if (x.Value.charAt(0) == '-'){ // subtracting a negative = addition
            x.Value = x.Value.substring(1);
            return this.Plus(x);
        }
        else if (this.Value.charAt(0) == '-') { // negative - positive = -1((-1 * negative) + positive)
            this.Value = this.Value.substring(1);
            result = this.Plus(x);
            result.Value = "-" + result.ToString();
            return result;
        }

        // only other option now is two positive numbers

        // determine which is larger. this will help us determine which to subtract which from (and track if it will be negative)
        if (x.Value.length() > this.Value.length()){
            // if the value we are subtracting by is larger, do x - this and then add a negative to front
            result = x.Minus(this);
            result.Value = "-" + result.Value;
            return result;
        } else if (x.Value.length() == this.Value.length()){ // if same value, loop through most sig digits to find which is larger
            // this is inefficient
            boolean found = false;
            int i = 0;
            while (!found && i < this.Value.length()){
                int thisVal = convertToInt(this.Value.charAt(i));
                int xVal = convertToInt(x.Value.charAt(i));
                if (xVal > thisVal){ // x is larger. same as above
                    result = x.Minus(this);
                    result.Value = "-" + result.Value;
                    return result;
                } else if (thisVal > xVal){ // this is larger, subtraction will result in positive. continue as normal
                    found = true;
                }

                // else they are equal, look at next most significant digit

                ++i;
            }

            if (!found){ // this and x are equal
                return result;
            }
        }
        // else this is larger, subtract as normal

        //pad x
        int lenDif = this.Value.length() - x.Value.length();
        char[] pad = new char[lenDif];
        Arrays.fill(pad, '0');
        String fill = new String(pad);
        x.Value = fill + x.Value;

        for (int i = this.Value.length() - 1; i >= 0; --i){
            int thisVal = convertToInt(this.Value.charAt(i));
            int xVal = convertToInt(x.Value.charAt(i));
            if (thisVal >= xVal){
                int dif = thisVal - xVal;
                char hold = convertToChar(dif);
                String insert = String.valueOf(hold);
                if (i == this.Value.length()-1){
                    result.Value = insert;
                }else {
                    result.Value = insert + result.Value;
                }
            }
            else{ // xVal > thisVal -- we need to steal from the next digit (or the next non-zero digit)
                int s = i - 1;
                while (convertToInt(this.Value.charAt(s)) == 0){
                    char[] temp = this.Value.toCharArray();
                    temp[s] = '9';
                    this.Value = String.valueOf(temp);
                    s--;
                }
                int change = convertToInt(this.Value.charAt(s)) - 1;
                char p = convertToChar(change);
                char[] temp = this.Value.toCharArray();
                temp[s] = p;
                this.Value = String.valueOf(temp);
                thisVal = thisVal + 10;
                int dif = thisVal - xVal;
                char hold = convertToChar(dif);
                String insert = String.valueOf(hold);
                if (i == this.Value.length()-1){
                    result.Value = insert;
                }else {
                    result.Value = insert + result.Value;
                }
            }
        }



        return result;
    }

    public int convertToInt(char a){
        return a - 48;
    }

    public char convertToChar(int a) {
        return (char)(a + 48);
    }
}
