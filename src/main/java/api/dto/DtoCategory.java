package api.dto;

import api.storage.models.SpaceCategory;

import java.util.Objects;

public class DtoCategory {
    private final int id;
    private final String name;
    private final String description;

    public DtoCategory(SpaceCategory category) {
        this.id = category.getID();
        this.name = category.getName();
        this.description = category.getDescription();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoCategory that = (DtoCategory) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
