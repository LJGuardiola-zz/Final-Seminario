package api.dto;

import api.storage.models.DeviceModel;

import java.util.Objects;

public class DtoDeviceModel {

    private final int id;
    private final String name;
    private final DtoDeviceBrand brand;

    public DtoDeviceModel(DeviceModel model) {
        this.id = model.getId();
        this.name = model.getName();
        this.brand = new DtoDeviceBrand(model.getBrand());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DtoDeviceBrand getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoDeviceModel that = (DtoDeviceModel) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
