package api.dto;

import api.storage.models.DeviceCompany;

import java.util.Objects;

public class DtoDeviceCompany {

    private final int id;
    private final String name;

    public DtoDeviceCompany(DeviceCompany company) {
        this.id = company.getId();
        this.name = company.getName();
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
        DtoDeviceCompany that = (DtoDeviceCompany) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
