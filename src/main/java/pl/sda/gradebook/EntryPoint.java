package pl.sda.gradebook;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class EntryPoint {

    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate ld = dateTimeFormatter.parseLocalDate("1991-02-12");
        System.out.println("Hello gradebook " + ld.toString("MM-dd-yyyy"));
    }
}
