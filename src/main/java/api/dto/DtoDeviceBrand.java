package api.dto;

import api.storage.models.DeviceBrand;

import java.util.Objects;

public class DtoDeviceBrand {

    private final int id;
    private final String name;

    public DtoDeviceBrand(DeviceBrand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoDeviceBrand that = (DtoDeviceBrand) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
