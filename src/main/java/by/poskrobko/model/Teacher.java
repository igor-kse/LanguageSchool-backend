package by.poskrobko.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Teacher {
    private User user;
    private String education;
    private Set<String> languages;

    public Teacher(User user, String education, Set<String> languages) {
        this.education = education;
        this.user = user;
        this.languages = languages;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return user.getUserId();
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher teacher)) return false;

        return Objects.equals(user, teacher.user);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "education='" + education + '\'' +
                ", user=" + user +
                '}';
    }
}
