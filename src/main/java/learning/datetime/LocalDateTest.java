package learning.datetime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LocalDateTest {

    public static void main(String[] args) {
        LocalDate dateNow = LocalDate.now();

        System.out.println(dateNow);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println(dateNow.format(formatter));

        String dateStr = "02-02-2020";

        LocalDate converted = LocalDate.parse(dateStr, formatter);
        System.out.println(converted);

        converted.plusDays(20).plusWeeks(5).plusYears(2);

        System.out.println(converted.getDayOfWeek());
    }
}
