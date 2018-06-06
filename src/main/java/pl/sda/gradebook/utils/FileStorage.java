package pl.sda.gradebook.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import pl.sda.gradebook.models.Student;
import pl.sda.gradebook.models.Subject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String STUDENTS_FILENAME = "students.json";
    private static final String SUBJECTS_FILENAME = "subjects.json";

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
            .enableComplexMapKeySerialization()
            .create();


    public static List<Subject> readSubjectsFromFile() {
        try {
            String json = FileUtils.readFileToString(new File(SUBJECTS_FILENAME), "UTF-8");
            Type type = new TypeToken<List<Subject>>() {
            }.getType();
            List<Subject> temp = gson.fromJson(json, type);
            return temp != null ? temp : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Failed to read from file: " + e.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    public static List<Student> readStudentsFromFile() {
        try {
            String json = FileUtils.readFileToString(new File(STUDENTS_FILENAME), "UTF-8");
            List<Student> temp = gson.fromJson(json, new TypeToken<List<Student>>() {
            }.getType());
            return temp != null ? temp : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Failed to read from file: " + e.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    public static void writeStudentsToFile(List<Student> students) {
        String json = gson.toJson(students);
        try (FileWriter fileWriter = new FileWriter(STUDENTS_FILENAME)) {
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("Failed to write to file: " + ex.getLocalizedMessage());
        }
    }

    public static void writeSubjectsToFile(List<Subject> subjects) {
        String json = gson.toJson(subjects);
        try (FileWriter fileWriter = new FileWriter(SUBJECTS_FILENAME)) {
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("Failed to write to file: " + ex.getLocalizedMessage());
        }
    }
}
