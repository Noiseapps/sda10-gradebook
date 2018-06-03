package pl.sda.gradebook.models;

import org.joda.time.LocalDate;

public class Student {

    private final String name;
    private final LocalDate dateOfBirth;
    private final int indexNumber;

    public Student(String name, LocalDate dateOfBirth, int indexNumber) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.indexNumber = indexNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", indexNumber=" + indexNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (indexNumber != student.indexNumber) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        return dateOfBirth != null ? dateOfBirth.equals(student.dateOfBirth) : student.dateOfBirth == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + indexNumber;
        return result;
    }
}
