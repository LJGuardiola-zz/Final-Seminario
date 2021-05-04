package api.dto;

import api.storage.models.Device;

import java.time.format.DateTimeFormatter;

public class DtoDevice {

    private final int id;
    private final String number;
    private final DtoDeviceCompany company;
    private final DtoDeviceModel model;
    private final String startDate, endDate;

    public DtoDevice(Device device) {
        this.id = device.getId();
        this.number = device.getNumber();
        this.company = new DtoDeviceCompany(device.getCompany());
        this.model = new DtoDeviceModel(device.getModel());
        this.startDate = device.getStart().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.endDate = device.getEnd() != null
                ? device.getEnd().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
                : "Activo";
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public DtoDeviceCompany getCompany() {
        return company;
    }

    public DtoDeviceModel getModel() {
        return model;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

}
