package api.dto;

import api.storage.models.OpenSpace;
import api.storage.models.Space;

import java.util.Objects;

public class DtoSpace {

    private final int id;
    private final String name;
    private final boolean available;
    private final int capacity;
    private final String type;

    public DtoSpace(Space space) {
        this.id = space.getID();
        this.name = space.getName();
        this.available = space.isAvailable();
        this.capacity = space.getCapacity();
        this.type = space instanceof OpenSpace ? "Abierto" : "Cerrado";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoSpace dtoSpace = (DtoSpace) o;
        return id == dtoSpace.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
