package by.poskrobko.model;

import java.util.Objects;
import java.util.UUID;

public class Grade {
    private final String id;
    private Language language;
    private CEFRLevel level;

    public Grade(String id, Language language, CEFRLevel level) {
        this.language = language;
        this.id = id;
        this.level = level;
    }

    public Grade(Language language, CEFRLevel level) {
        this.id = UUID.randomUUID().toString();
        this.language = language;
        this.level = level;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public CEFRLevel getLevel() {
        return level;
    }

    public void setLevel(CEFRLevel level) {
        this.level = level;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grade grade)) return false;

        return Objects.equals(id, grade.id) && Objects.equals(language, grade.language) && Objects.equals(level, grade.level);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(language);
        result = 31 * result + Objects.hashCode(level);
        return result;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "language=" + language +
                ", id=" + id +
                ", value='" + level + '\'' +
                '}';
    }
}
