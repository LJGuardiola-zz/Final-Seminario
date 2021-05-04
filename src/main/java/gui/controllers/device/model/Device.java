package gui.controllers.device.model;

import api.dto.DtoDevice;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Device {

    private final StringProperty number;
    private final StringProperty company;
    private final StringProperty model;
    private final StringProperty start;
    private final StringProperty end;

    public Device(DtoDevice dtoDevice) {
        this.number = new SimpleStringProperty(dtoDevice.getNumber());
        this.company = new SimpleStringProperty(dtoDevice.getCompany().getName());
        this.model = new SimpleStringProperty(
                dtoDevice.getModel().getName() + " - " + dtoDevice.getModel().getBrand().getName()
        );
        this.start = new SimpleStringProperty(dtoDevice.getStartDate());
        this.end = new SimpleStringProperty(dtoDevice.getEndDate());
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty() {
        return company;
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public String getStart() {
        return start.get();
    }

    public StringProperty startProperty() {
        return start;
    }

    public String getEnd() {
        return end.get();
    }

    public StringProperty endProperty() {
        return end;
    }

}
