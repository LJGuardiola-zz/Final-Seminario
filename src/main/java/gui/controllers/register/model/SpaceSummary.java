package gui.controllers.register.model;

import api.dto.DtoSpaceSummary;
import javafx.beans.property.*;

public class SpaceSummary {

    private final StringProperty space;
    private final IntegerProperty capacity;
    private final IntegerProperty devices;
    private final IntegerProperty amount;
    private final BooleanProperty supered;

    public SpaceSummary(DtoSpaceSummary summary) {
        this.space = new SimpleStringProperty(summary.getSpace());
        this.capacity = new SimpleIntegerProperty(summary.getCapacity());
        this.devices = new SimpleIntegerProperty(summary.getDevices());
        this.amount = new SimpleIntegerProperty(summary.getAmount());
        this.supered = new SimpleBooleanProperty(summary.isSupered());
    }

    public String getSpace() {
        return space.get();
    }

    public StringProperty spaceProperty() {
        return space;
    }

    public void setSpace(String space) {
        this.space.set(space);
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

    public int getDevices() {
        return devices.get();
    }

    public IntegerProperty devicesProperty() {
        return devices;
    }

    public void setDevices(int devices) {
        this.devices.set(devices);
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public boolean isSupered() {
        return supered.get();
    }

    public BooleanProperty superedProperty() {
        return supered;
    }

    public void setSupered(boolean supered) {
        this.supered.set(supered);
    }

}
