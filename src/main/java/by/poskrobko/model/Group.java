package by.poskrobko.model;

import java.util.Objects;
import java.util.UUID;

public class Group {
    private final String id;
    private String name;
    private Language language;
    private Teacher teacher;
    private Scale scale;
    private Scale.Level scaleLevel;

    public Group(String id, String name, Language language, Scale scale, Scale.Level scaleLevel, Teacher teacher) {
        this.id = id;
        this.language = language;
        this.name = name;
        this.scale = scale;
        this.scaleLevel = scaleLevel;
        this.teacher = teacher;
    }

    public Group(String name, Language language, Scale scale, Scale.Level scaleLevel, Teacher teacher) {
        this.id = UUID.randomUUID().toString();
        this.language = language;
        this.name = name;
        this.scale = scale;
        this.scaleLevel = scaleLevel;
        this.teacher = teacher;
    }

    public String getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public Scale.Level getScaleLevel() {
        return scaleLevel;
    }

    public void setScaleLevel(Scale.Level scaleLevel) {
        this.scaleLevel = scaleLevel;
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

        return Objects.equals(id, group.id) && Objects.equals(name, group.name)
                && Objects.equals(language, group.language) && Objects.equals(teacher, group.teacher)
                && Objects.equals(scale, group.scale) && Objects.equals(scaleLevel, group.scaleLevel);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(language);
        result = 31 * result + Objects.hashCode(teacher);
        result = 31 * result + Objects.hashCode(scale);
        result = 31 * result + Objects.hashCode(scaleLevel);
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", language=" + language +
                ", teacher=" + teacher +
                ", scale=" + scale +
                ", scaleLevel=" + scaleLevel +
                '}';
    }
}
