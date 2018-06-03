package pl.sda.gradebook.models;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.Collection;

public class Subject {

    private static int nextId = 0;

    private final String name;
    private final String lecturer;
    private final MultiValuedMap<Student, Grade> grades = new ArrayListValuedHashMap<Student, Grade>();
    private final int id;

    public Subject(String name, String lecturer) {
        this.name = name;
        this.lecturer = lecturer;
        this.id = nextId++;
    }

    public void addGrade(Student student, Grade grade) {
        this.grades.put(student, grade);
    }

    public Collection<Grade> getGrades(Student student) {
        return grades.get(student);
    }

    public String getName() {
        return name;
    }

    public String getLecturer() {
        return lecturer;
    }

    public MultiValuedMap<Student, Grade> getGrades() {
        return grades;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (id != subject.id) return false;
        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;
        if (lecturer != null ? !lecturer.equals(subject.lecturer) : subject.lecturer != null) return false;
        return grades.equals(subject.grades);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (lecturer != null ? lecturer.hashCode() : 0);
        result = 31 * result + grades.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", grades=" + grades +
                ", id=" + id +
                '}';
    }
}
