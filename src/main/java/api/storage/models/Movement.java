package api.storage.models;

import com.sun.istack.internal.Nullable;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

import java.time.LocalDateTime;

public class Movement {

    @Nullable
    @NotNegative(message = "El ID de un movimiento no puede ser negativo.")
    private Integer id;

    @NotNull(message = "El dispositivo no puede ser nulo.")
    private Device device;

    @NotNull(message = "El dispositivo no puede ser nulo.")
    private Space space;

    @NotNull(message = "La cantidad de personas no puede ser nula.")
    @NotNegative(message = "El cantidad de personas no puede ser negativa.")
    private int amount;

    private LocalDateTime entryDate;

    private LocalDateTime exitDate;

    public Movement(Device device, Space space, int amount) {
        this(null, device, space, amount, LocalDateTime.now(), null);
    }

    public Movement(Integer id, Device device, Space space, int amount, LocalDateTime entryDate, LocalDateTime exitDate) {
        this.id = id;
        this.device = device;
        this.space = space;
        this.amount = amount;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
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

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }

}
