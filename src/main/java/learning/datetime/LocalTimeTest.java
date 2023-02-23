package learning.datetime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LocalTimeTest {

    public static void main(String[] args) {
        //creare
        LocalTime timeNow = LocalTime.now();

        System.out.println(timeNow);

        //formatare
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        System.out.println(timeNow.format(formatter));

        //parsare

        String stringTime = "12:23:50";

        LocalTime convertedTime = LocalTime.parse(stringTime);
        System.out.println(convertedTime);

        convertedTime = convertedTime.plusHours(2).plusMinutes(30).plusSeconds(50);

        System.out.println(convertedTime.compareTo(timeNow));

        System.out.println(convertedTime);
        //manipulare
    }
}
