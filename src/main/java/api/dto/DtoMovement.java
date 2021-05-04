package api.dto;

import api.storage.models.Movement;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DtoMovement {

    private final int id;
    private final DtoDevice device;
    private final DtoSpace space;
    private final int amount;
    private final String entryDate;
    private final String exitDate;

    public DtoMovement(Movement movement) {
        this.id = movement.getId();
        this.device = new DtoDevice(movement.getDevice());
        this.space = new DtoSpace(movement.getSpace());
        this.amount = movement.getAmount();
        this.entryDate = movement.getEntryDate().format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
        );
        this.exitDate = movement.getExitDate() != null
                ? movement.getEntryDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
                : "";
    }

    public int getId() {
        return id;
    }

    public DtoDevice getDevice() {
        return device;
    }

    public DtoSpace getSpace() {
        return space;
    }

    public int getAmount() {
        return amount;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getExitDate() {
        return exitDate;
    }

}
