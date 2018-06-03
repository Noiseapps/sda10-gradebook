package pl.sda.gradebook;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import pl.sda.gradebook.models.Student;
import pl.sda.gradebook.models.Subject;
import pl.sda.gradebook.utils.FileStorage;

import java.util.ArrayList;
import java.util.List;

public class EntryPoint implements Runnable {

    private final List<Student> students = new ArrayList<>();
    private final List<Subject> subjects = new ArrayList<>();

    public static void main(String[] args) {
        new EntryPoint().run();
    }

    private LocalDate dateForString(String dateString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        return dateTimeFormatter.parseLocalDate(dateString);
    }

    public void run() {
        students.addAll(FileStorage.readStudentsFromFile());

        if (students.isEmpty()) {
            int indexNumber = 133522;
            students.add(new Student("Jan Kowalski", dateForString("1991-02-12"), indexNumber++));
            students.add(new Student("Jan Nowak", dateForString("1991-05-12"), indexNumber++));
            students.add(new Student("Piotr Kasprzak", dateForString("1991-02-12"), indexNumber++));
            students.add(new Student("Julian Tuwim", dateForString("1991-02-12"), indexNumber++));
            students.add(new Student("Adam Miauczyński", dateForString("1991-02-12"), indexNumber));
        }
        System.out.println(students);
        FileStorage.writeStudentsToFile(students);
    }
}
