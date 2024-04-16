package DuAn.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dinhh
 */
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class Common {

    public static String formatVietnameseMoney(double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,##0.##", symbols); // Changed format to #,##0.##

        String formattedAmount = df.format(amount);

        // Remove ".00" if decimal part is zero
        if (formattedAmount.endsWith(",00")) {
            formattedAmount = formattedAmount.substring(0, formattedAmount.length() - 3);
        }

        return formattedAmount + " VNĐ";
    }

    public static double parseVietnameseMoney(String formattedAmount) throws ParseException {
        // Remove non-numeric characters and replace comma with dot for parsing
        String cleanAmount = formattedAmount.replaceAll("[^0-9,]", "").replace(",", ".");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,##0.##", symbols);
        double parsedAmount = df.parse(cleanAmount).doubleValue();

        return parsedAmount;
    }
}
