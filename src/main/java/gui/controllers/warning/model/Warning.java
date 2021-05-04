package gui.controllers.warning.model;

import api.dto.DtoWarning;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Warning {

    private final StringProperty spaceName;
    private final StringProperty spaceType;
    private final IntegerProperty capacity;
    private final IntegerProperty registeredDevices;
    private final IntegerProperty surpassedBy;

    public Warning(DtoWarning warning) {
        this.spaceName = new SimpleStringProperty(warning.getSpaceName());
        this.spaceType = new SimpleStringProperty(warning.getSpaceType());
        this.capacity = new SimpleIntegerProperty(warning.getCapacity());
        this.registeredDevices = new SimpleIntegerProperty(warning.getAmount());
        this.surpassedBy = new SimpleIntegerProperty(warning.getAmount() - warning.getCapacity());
    }

    public String getSpaceName() {
        return spaceName.get();
    }

    public StringProperty spaceNameProperty() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName.set(spaceName);
    }

    public String getSpaceType() {
        return spaceType.get();
    }

    public StringProperty spaceTypeProperty() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType.set(spaceType);
    }

    public int getCapacity() {
        return capacity.get();
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public int getRegisteredDevices() {
        return registeredDevices.get();
    }

    public IntegerProperty registeredDevicesProperty() {
        return registeredDevices;
    }

    public void setRegisteredDevices(int registeredDevices) {
        this.registeredDevices.set(registeredDevices);
    }

    public int getSurpassedBy() {
        return surpassedBy.get();
    }

    public IntegerProperty surpassedByProperty() {
        return surpassedBy;
    }

    public void setSurpassedBy(int surpassedBy) {
        this.surpassedBy.set(surpassedBy);
    }

}
