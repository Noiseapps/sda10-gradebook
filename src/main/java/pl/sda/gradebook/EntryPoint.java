package pl.sda.gradebook;

import com.sun.istack.internal.NotNull;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import pl.sda.gradebook.models.Grade;
import pl.sda.gradebook.models.Student;
import pl.sda.gradebook.models.Subject;
import pl.sda.gradebook.utils.FileStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EntryPoint implements Runnable {

    private final List<Student> students = new ArrayList<>();
    private final List<Subject> subjects = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new EntryPoint().run();
    }

    private LocalDate dateForString(String dateString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        return dateTimeFormatter.parseLocalDate(dateString);
    }

    public void run() {
        students.clear();
        subjects.clear();

        subjects.addAll(FileStorage.readSubjectsFromFile());
        students.addAll(FileStorage.readStudentsFromFile());

        while (true) { // main loop
            Actions action = readAction();
            switch (action) {
                case ADD_SUBJECT:
                    Subject subject = makeSubject();
                    subjects.add(subject);
                    System.out.println("Przedmiot " + subject + " dodany do listy.");
                    break;
                case ADD_STUDENT:
                    Student student = makeStudent();
                    students.add(student);
                    System.out.println("Student " + student + " dodany do listy.");
                    break;
                case ADD_GRADE:
                    makeGrade();
                    break;
                case LIST_STUDENTS:
                    listStudents();
                    break;
                case LIST_SUBJECTS:
                    listSubjects();
                    break;
                case EXIT:
                    System.out.println("Dzięki za współpracę, zapraszamy ponownie.");
                    return;
            }
            FileStorage.writeStudentsToFile(students);
            FileStorage.writeSubjectsToFile(subjects);
        }
    }

    private void listSubjects() {
        System.out.println("Lista przedmiotów dodanych do programu:\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (Subject subject : subjects) {
            stringBuilder.append(subject.toString()).append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    private void listStudents() {
        System.out.println("Lista studentów dodanych do programu:\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (Student student : students) {
            stringBuilder.append(student.toString()).append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    private void listStudents(Subject subject) {
        System.out.printf("Lista studentów zapisanych do %s:\n", subject.getName());

        StringBuilder stringBuilder = new StringBuilder();
        subjects.stream()
                .filter(subject1 -> subject1.equals(subject))
                .flatMap(subject1 -> subject.getGrades().entrySet().stream())
                .forEach(entry -> stringBuilder
                        .append(entry.getKey().getName())
                        .append(" (")
                        .append(entry.getKey().getIndexNumber())
                        .append(")"));
        System.out.println(stringBuilder.toString());
    }

    private void makeGrade() {
        System.out.println("Podaj identyfikator");
        System.out.println("Dostępne opcje: ");
        listSubjects();

        while (true) {
            Subject subject = getSubject();
            Student student = getStudent();

            System.out.printf("Ok. Wystawiasz ocenę dla %s (%d) z %s\n", student.getName(), student.getIndexNumber(), subject.getName());
            System.out.println("Potwierdzasz? y/n");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                double gradeValue = getDouble("Podaj jaką wystawiasz ocenę: ");
                subject.addGrade(student, new Grade(gradeValue));
                System.out.println("Ok, ocena wystawiona!");
                break;
            }
        }

    }

    private Actions readAction() {
        while (true) {
            System.out.println("Co chcesz uczynić, Panie?\n" + Actions.listOptions());
            String s = scanner.nextLine();
            try {
                int action = Integer.valueOf(s);
                return Actions.forId(action);
            } catch (NumberFormatException | Actions.ActionNotFoundException ex) {
                System.out.println("Nie mogę tego uczynić. Spróbuj ponownie.\n");
            }

        }
    }

    @NotNull
    private Subject getSubject() {
        System.out.println("Podaj identyfikator przedmiotu");
        while (true) {
            try {
                long subjectId = Long.valueOf(scanner.nextLine());
                if (subjectId < 0) {
                    throw new NumberFormatException("Identyfikator musi być większy od 0");
                }
                Optional<Subject> first = subjects.stream()
                        .filter(subject -> subject.getId() == subjectId)
                        .findFirst();
                if (!first.isPresent()) throw new NumberFormatException("Nie ma przedmiotu o takim ID");
                return first.get();
            } catch (NumberFormatException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

    @NotNull
    private Student getStudent() {
        System.out.println("Podaj numer indeksu studenta");
        while (true) {
            try {
                long studentId = Long.valueOf(scanner.nextLine());
                if (studentId < 0) {
                    throw new NumberFormatException("Numer indeksu musi być większy od 0");
                }
                Optional<Student> first = students.stream()
                        .filter(subject -> subject.getIndexNumber() == studentId)
                        .findFirst();
                if (!first.isPresent()) throw new NumberFormatException("Nie ma studenta o takim numerze indeksu");
                return first.get();
            } catch (NumberFormatException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

    private double getDouble(String message) {
        System.out.println(message);
        while (true) {
            try {
                double value = Double.valueOf(scanner.nextLine());
                if (value <= 0) {
                    throw new NumberFormatException();
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Wartość nie jest poprawna");
            }
        }
    }

    private Subject makeSubject() {
        System.out.println("Nowy przedmiot?");
        System.out.println("Podaj nazwę przedmiotu");
        String subjectName = scanner.nextLine();
        System.out.println(subjectName + "? Kto to będzie chciał prowadzić?");
        String lecturerName = scanner.nextLine();
        return new Subject(subjectName, lecturerName);
    }

    private Student makeStudent() {
        System.out.println("A więc masz nowego ucznia? Hm...");
        System.out.println("Podaj jego dane osobowe, RODO hehehehe");
        String personName = scanner.nextLine();
        LocalDate date = getBirthDate();
        long indexNumber = getIndexNumber();
        return new Student(personName, date, indexNumber);
    }

    private LocalDate getBirthDate() {
        System.out.println("Ok, to teraz data urodzenia w formacie YYYY-MM-DD");
        while (true) {
            try {
                String dateOfBirthString = scanner.nextLine();
                return dateForString(dateOfBirthString);
            } catch (IllegalArgumentException ex) {
                System.out.println("Przecież podałem, jaki ma być format...");
            }

        }
    }

    private long getIndexNumber() {
        System.out.println("I na koniec numer indeksu");
        while (true) {
            try {
                long indexNumber = Long.valueOf(scanner.nextLine());
                if (indexNumber <= 0) {
                    throw new NumberFormatException();
                }
                return indexNumber;
            } catch (NumberFormatException ex) {
                System.out.println("NUMER indeksu... :|");
            }
        }
    }

    private enum Actions {
        ADD_SUBJECT(1, "Dodaj przedmiot"),
        ADD_STUDENT(2, "Dodaj studenta"),
        ADD_GRADE(3, "Dodaj ocenę"),
        LIST_STUDENTS(4, "Wyświetl studentów"),
        LIST_SUBJECTS(5, "Wyświetl przedmioty"),
        EXIT(9, "Zamknij");
        final int actionId;
        final String name;

        Actions(int actionId, String name) {
            this.actionId = actionId;
            this.name = name;
        }

        public static String listOptions() {
            StringBuilder stringBuilder = new StringBuilder();
            for (Actions actions : values()) {
                stringBuilder
                        .append(actions.actionId)
                        .append(": ")
                        .append(actions.name)
                        .append("\n");
            }
            return stringBuilder.toString();
        }

        public static Actions forId(int id) throws ActionNotFoundException {
            for (Actions actions : values()) {
                if (actions.actionId == id) return actions;
            }
            throw new ActionNotFoundException();
        }

        static class ActionNotFoundException extends Exception {
        }
    }
}
