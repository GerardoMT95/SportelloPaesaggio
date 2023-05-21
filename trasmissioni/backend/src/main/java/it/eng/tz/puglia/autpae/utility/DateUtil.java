package it.eng.tz.puglia.autpae.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	private DateUtil() { }

	public static String dataParse(Date data) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.format(data);
	}

    /**
     * Checks if two dates are on the same day ignoring time.
     * @param date1  the first date, not altered, not null
     * @param date2  the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     */
    private static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }
    
    /**
     * Checks if two calendars represent the same day ignoring time.
     * @param cal1  the first calendar, not altered, not null
     * @param cal2  the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    private static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
    
    /**
     * Checks if a date is today.
     * @param date the date, not altered, not null.
     * @return true if the date is today.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    @SuppressWarnings("unused")
	private static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    
    /**
     * @return l'oggetto @param Date riferito alla fine della giornata (ore 23:59:59)
     */
	public static Date endOfDay(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 23);
	    cal.set(Calendar.MINUTE, 59);
	    cal.set(Calendar.SECOND, 59);
	    cal.set(Calendar.MILLISECOND, 999);		
        return cal.getTime();
    }
	
	/**
	 * per ottenere il giorno precedente o successivo
	 * @autor Adriano Colaianni
	 * @date 9 apr 2021
	 * @param in
	 * @param day
	 * @return
	 */
	public static Date addDays(Date in,int day) {
		if(in==null) return in;
		Calendar outCal = new GregorianCalendar();
		outCal.setTime(in);
		outCal.add(Calendar.DATE, day);
		return outCal.getTime();
	}
	
    
}