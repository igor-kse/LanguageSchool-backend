package by.poskrobko.model;

import java.util.Objects;
import java.util.Set;

public class Scale {
    private String name;
    private String description;
    private Set<Level> levels;

    public Scale(String name, String description, Set<Level> levels) {
        this.description = description;
        this.name = name;
        this.levels = levels;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Level> getLevels() {
        return levels;
    }

    public void setLevels(Set<Level> levels) {
        this.levels = levels;
    }

    @Override
    public final boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Scale that)) return false;

        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        return result;
    }

    @Override
    public String toString() {
        return "LanguageScale{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Level {
        private String id;
        private String name;

        public Level(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Level level)) return false;

            return Objects.equals(id, level.id) && Objects.equals(name, level.name);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(id);
            result = 31 * result + Objects.hashCode(name);
            return result;
        }

        @Override
        public String toString() {
            return "Level{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
