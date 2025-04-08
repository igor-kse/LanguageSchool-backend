package by.poskrobko.model;

import java.util.Objects;

public class Student {
    private User user;
    private int age;
    private String hobbies;
    private String channel;
    private String note;

    public Student(int age, String channel, String hobbies, String note, User user) {
        this.age = age;
        this.channel = channel;
        this.hobbies = hobbies;
        this.note = note;
        this.user = user;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;

        return age == student.age
                && Objects.equals(user, student.user)
                && Objects.equals(hobbies, student.hobbies)
                && Objects.equals(channel, student.channel)
                && Objects.equals(note, student.note);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(user);
        result = 31 * result + age;
        result = 31 * result + Objects.hashCode(hobbies);
        result = 31 * result + Objects.hashCode(channel);
        result = 31 * result + Objects.hashCode(note);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", user=" + user +
                ", hobbies='" + hobbies + '\'' +
                ", channel='" + channel + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
