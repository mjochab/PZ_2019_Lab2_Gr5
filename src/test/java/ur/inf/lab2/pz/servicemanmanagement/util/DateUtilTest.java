package ur.inf.lab2.pz.servicemanmanagement.util;

import org.junit.Test;
import ur.inf.lab2.pz.servicemanmanagement.domain.DateRange;
import ur.inf.lab2.pz.servicemanmanagement.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateUtilTest {

    @Test
    public void getWeekDateRangeByDate_someDaysInWeek_shouldReturnMondayAndSundayDates() throws ParseException {
        getWeekDateRangeByDateTest("2019-05-13", "2019-05-15", "2019-05-19");
        getWeekDateRangeByDateTest("2019-05-13", "2019-05-13", "2019-05-19");
        getWeekDateRangeByDateTest("2019-05-13", "2019-05-19", "2019-05-19");
        getWeekDateRangeByDateTest("2019-05-06", "2019-05-12", "2019-05-12");
    }

    private void getWeekDateRangeByDateTest(String expectedDateMonday, String date, String expectedDateSunday) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date wednesdayDate = format.parse(date);

        DateRange weekDateRangeByDate = DateUtils.getWeekDateRangeByDate(wednesdayDate);
        String mondayDateRaw = format.format(weekDateRangeByDate.getFrom());
        String sundayDateRaw = format.format(weekDateRangeByDate.getTo());

        assertEquals(expectedDateMonday, mondayDateRaw);
        assertEquals(expectedDateSunday, sundayDateRaw);
    }

    @Test
    public void minusDays_oneDay_returnDayBefore() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date secondOfMay = format.parse("2019-05-02");

        String firstOfMayAsString = format.format(DateUtils.minusDays(secondOfMay, 1));

        assertEquals("2019-05-01", firstOfMayAsString);
    }

}
