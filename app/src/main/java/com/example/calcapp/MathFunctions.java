package com.example.calcapp;

public class MathFunctions {
    public double fact(double number){
        if (number == 0.0){
            return 1;
        }else if (number<0 || number%1!=0){
            return 0.0;
        }else{
            return number*fact(number-1);
        }
    }

    public double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
