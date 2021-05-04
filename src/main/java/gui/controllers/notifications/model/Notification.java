package gui.controllers.notifications.model;

import api.dto.DtoCategory;
import api.dto.DtoNotification;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Notification {

    private final StringProperty device;
    private final StringProperty message;
    private final StringProperty date;

    public Notification(DtoNotification notification) {
        this.device = new SimpleStringProperty(
                notification.getDevice().getModel().getName() + " " +
                        notification.getDevice().getModel().getBrand().getName()
        );
        this.message = new SimpleStringProperty(notification.getMessage());
        this.date = new SimpleStringProperty(notification.getDate());
    }

    public String getDevice() {
        return device.get();
    }

    public StringProperty deviceProperty() {
        return device;
    }

    public void setDevice(String device) {
        this.device.set(device);
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

}
