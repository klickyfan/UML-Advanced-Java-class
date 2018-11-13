package edu.kimjones.advancedjava.stock.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class encapsulates test related utility methods.
 */
public class TestUtility {

    /**
     * @param dateString        a string containing a date of the form "mm/dd/yyyy"
     * @return                  an instance of the Calendar class for that date
     * @throws ParseException   if the string given is not of the form "mm/dd/yyyy"
     */
    public static Calendar parseDateString(String dateString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = formatter.parse(dateString);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal;
    }

    /**
     *  @param  hours           hours to add
     *  @param  beforeDate      date that will have hours added to it
     *  @return                 date object with the specified number of hours added to it
     */
    public static Date addHoursToDate(int hours, Date beforeDate){

        final long ONE_MINUTE_IN_MILLISECONDS = 60000; // milliseconds

        long currentTimeInMs = beforeDate.getTime();
        long timeAfterAddingHours = currentTimeInMs + (hours * 60 * ONE_MINUTE_IN_MILLISECONDS);

        return new Date(timeAfterAddingHours);
    }
}
