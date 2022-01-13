package utils;

import java.util.Calendar;
import java.util.Date;

public class DateCreator {

//    String[] months = {"JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER"};

    public Date calcDate (int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();



        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        return calendar.getTime();
    }
}
