/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuAn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author dinhh
 */
public class XDate {

    static SimpleDateFormat formater = new SimpleDateFormat();

    /*
 * Chuyển đổi String sang Date
 * @param date là String cần chuyển
 * @param pattern là định dạng thời gian
 * @return Date kết quả
     */

    public static Date toDate(String date, String pattern) {
        try {
            formater.applyPattern(pattern); //pattern :"dd-mm-yyyy"
            return formater.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
    * Chuyển đổi từ Date sang String
    * @param date là Date cần chuyển đổi
    * @param pattern là định dạng thời gian
    * @return String kết quả
     */
    public static String toString(Date date, String... pattern) {
//        if (pattern.length > 0) {
//            formater.applyPattern(pattern[0]);
//        }
        formater.applyPattern("dd/MM/yyyy");
        if (date == null) {
            date = XDate.now();
        }
        return formater.format(date);
    }

    /*
    * Bổ sung số ngày vào thời gian
    * @param date thời gian hiện có
    * @param days số ngày cần bổ sung váo date
    * @return Date kết quả
     */
    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

    /*
    * Lấy thời gian hiện tại
    * @return Date kết quả
     */
    public static Date now() {
        return new Date();
    }

    public static Date add(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

}
