package me.dooger.duelsniffer.utils;

import java.text.DecimalFormat;

public class MathUtils {

    public static double formatDouble(int int1, int int2) {
        if (int2 == 0) {
            return int1;
        }
        double d;
        double result = (double) int1 / (double) int2;
        DecimalFormat format = new DecimalFormat("##.##");
        String formattedString = format.format(result).replace(",", ".");
        try {
            d = Double.parseDouble(formattedString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            d = int1;
        }
        return d;
    }

}
