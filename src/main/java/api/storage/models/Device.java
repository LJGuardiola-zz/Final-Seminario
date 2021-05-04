package api.storage.models;

import com.sun.istack.internal.Nullable;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

import java.time.LocalDateTime;

public class Device {

    @Nullable
    @NotNegative(message = "El id de dispositivo no puede ser negativo.")
    private Integer id;

    @NotNull(message = "El número no puede ser nulo.")
    @NotEmpty(message = "El número no puede estar vacío.")
    private String number;

    @NotNull(message = "La compañía no puede ser nula.")
    private DeviceCompany company;

    @NotNull(message = "El modelo no puede ser nulo.")
    private DeviceModel model;

    private LocalDateTime start, end;

    public Device(String number, DeviceCompany company, DeviceModel model) {
        this(null, number, company, model, null, null);
    }

    public Device(Integer id, String number, DeviceCompany company, DeviceModel model, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.number = number;
        this.company = company;
        this.model = model;
        this.start = start;
        this.end = end;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public DeviceCompany getCompany() {
        return company;
    }

    public void setCompany(DeviceCompany company) {
        this.company = company;
    }

    public DeviceModel getModel() {
        return model;
    }

    public void setModel(DeviceModel model) {
        this.model = model;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

}
