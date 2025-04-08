package by.poskrobko.model;

import java.util.Objects;

public class Language {

    private String name;

    private String scaleName;

    private String note;

    public Language(String name, String note, String scaleName) {
        this.name = name;
        this.note = note;
        this.scaleName = scaleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getScaleName() {
        return scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language language)) return false;

        return Objects.equals(name, language.name) && Objects.equals(scaleName, language.scaleName)
                && Objects.equals(note, language.note);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(scaleName);
        result = 31 * result + Objects.hashCode(note);
        return result;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                ", scaleName='" + scaleName + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
