package ur.inf.lab2.pz.servicemanmanagement.utils;

import ur.inf.lab2.pz.servicemanmanagement.domain.DateRange;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static DateRange getWeekDateRangeByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date actualWeekMondaysDate = cal.getTime();
        cal.add(Calendar.DAY_OF_WEEK, 6);

        Date actualWeekSundaysDate = cal.getTime();

        return new DateRange(actualWeekMondaysDate, actualWeekSundaysDate);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String rawDate = format.format(date);

        return LocalDate.parse(rawDate).atTime(0, 0);
    }

    public static Date minusDays(Date date, int numOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, numOfDays * (-1));

        return cal.getTime();
    }

    public static Date plusDays(Date date, int numOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, numOfDays);

        return cal.getTime();
    }

}
