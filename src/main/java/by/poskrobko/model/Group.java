package by.poskrobko.model;

import java.util.Objects;
import java.util.UUID;

public class Group {
    private final String id;
    private String name;
    private Grade grade;
    private Teacher teacher;

    public Group(String id, String name, Grade grade, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.teacher = teacher;
    }

    public Group(Teacher teacher, String name, Grade grade) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.grade = grade;
        this.teacher = teacher;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group group)) return false;

        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Group{" +
                "grade=" + grade +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}
