package learning.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LocalDateTimeTest {

    public static void main(String[] args) {
        LocalDateTime dateTimeNow = LocalDateTime.of(2020, 12, 12, 6, 10, 34, 8878787);

        System.out.println(dateTimeNow);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");

        System.out.println(dateTimeNow.format(formatter));

        String dateTimeStr = "2020-12-12T12:10:10";
        System.out.println(LocalDateTime.parse(dateTimeStr));


    }
}
