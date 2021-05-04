package api.storage.models;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

import java.time.LocalDateTime;

public class Notification {

    @NotNegative(message = "El id de la notificación no puede ser nulo.")
    private Integer id;

    @NotNull(message = "El dispositivo no puede ser nulo.")
    private Device device;

    @NotEmpty(message = "El mensaje no puede estar vacío.")
    private String message;

    private LocalDateTime date;

    public Notification(Device device, String message) {
        this(null, device, message, LocalDateTime.now());
    }

    public Notification(Integer id, Device device, String message, LocalDateTime date) {
        this.id = id;
        this.device = device;
        this.message = message;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
