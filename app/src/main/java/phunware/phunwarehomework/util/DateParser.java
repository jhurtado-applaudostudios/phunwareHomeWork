package phunware.phunwarehomework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Juan Hurtado on 6/8/2015.
 */
public class DateParser {
    public static final String LINE_SEPARATOR = "\n";
    private static final String FRONT_SLASH = "/";
    public static final String WHITE_SPACE = " ";
    private static final String COLON = ":";
    private static final int CONFIRMATION_OK = -1;
    private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final String NEXT_EVENT = " to: ";
    private static final String PARSE_MISSING_VALUE = "0";
    private static final int SIMPLE_HOUR_FORMAT = 1;
    private static final int SIMPLE_DATE = 0;
    private final StringBuilder builder = new StringBuilder();

    public String getParsedDate(String startDate, String endDate) throws ParseException {
        StringBuilder mParseDateBuilder = new StringBuilder();
        String mStartDate = startDate;
        String mEndDate = endDate;
        int mConfirmedFormat = DateParser.CONFIRMATION_OK;

        String[] startParseData = mStartDate.split("\\s");
        String[] endParseData = mEndDate.split("\\s");


        if (mConfirmedFormat == CONFIRMATION_OK) {

            mParseDateBuilder.append(getDayOfTheWeek(startParseData[SIMPLE_DATE])).append(WHITE_SPACE).append(getMontDate(startParseData[SIMPLE_DATE])).append(WHITE_SPACE).append(getParsedHour(startParseData[SIMPLE_HOUR_FORMAT])).append(NEXT_EVENT).append(getParsedHour(endParseData[SIMPLE_HOUR_FORMAT])).append(LINE_SEPARATOR);

        }

        return mParseDateBuilder.toString();

    }

    private String getDayOfTheWeek(String date) throws ParseException {
        String dayOfTheWeek = "NONE";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(FORMATTER.parse(date));


        switch (calendar.get(Calendar.DAY_OF_WEEK)) {

            case Calendar.MONDAY:
                dayOfTheWeek = "Monday";
                break;
            case Calendar.TUESDAY:
                dayOfTheWeek = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayOfTheWeek = "Wednesday";
                break;
            case Calendar.THURSDAY:
                dayOfTheWeek = "thursday";
                break;
            case Calendar.FRIDAY:
                dayOfTheWeek = "Friday";
                break;
            case Calendar.SATURDAY:
                dayOfTheWeek = "Saturday";
                break;
            case Calendar.SUNDAY:
                dayOfTheWeek = "Sunday";
                break;
        }

        return dayOfTheWeek;

    }

    private String getMontDate(String date) {
        builder.delete(0, builder.length());
        String[] sortedDate = date.split("-");

        builder.append(sortedDate[1]).append(FRONT_SLASH).append(sortedDate[2]);

        return builder.toString();

    }

    private String getParsedHour(String hour) {
        builder.delete(0, builder.length());
        String timeIndicator = "am";
        String hourArray[] = hour.split(":");
        int parseHour = Integer.parseInt(hourArray[0]);


        if (parseHour > 12) {
            timeIndicator = "pm";
            parseHour = parseHour - 12;
        }


        if (parseHour < 10) {
            builder.append(PARSE_MISSING_VALUE).append(String.valueOf(parseHour)).append(COLON).append(hourArray[1]).append(WHITE_SPACE).append(timeIndicator);
        } else {
            builder.append(String.valueOf(parseHour)).append(COLON).append(hourArray[1]).append(WHITE_SPACE).append(timeIndicator);
        }


        return builder.toString();

    }
}
