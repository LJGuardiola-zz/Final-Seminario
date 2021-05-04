package api.dto;

import api.storage.models.Notification;

import java.time.format.DateTimeFormatter;

public class DtoNotification {

    private final int id;
    private final DtoDevice device;
    private final String message;
    private final String date;

    public DtoNotification(Notification notification) {
        this.id = notification.getId();
        this.device = new DtoDevice(notification.getDevice());
        this.message = notification.getMessage();
        this.date = notification.getDate().format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
        );
    }

    public int getId() {
        return id;
    }

    public DtoDevice getDevice() {
        return device;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

}
