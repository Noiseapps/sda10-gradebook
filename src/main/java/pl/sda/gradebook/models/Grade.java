package pl.sda.gradebook.models;

import org.joda.time.DateTime;

public class Grade {

    private final double value;
    private final DateTime createdAt;

    public Grade(double value) {
        this.value = value;
        this.createdAt = DateTime.now();
    }

    public double getValue() {
        return value;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "value=" + value +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grade grade = (Grade) o;

        if (Double.compare(grade.value, value) != 0) return false;
        return createdAt != null ? createdAt.equals(grade.createdAt) : grade.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
