package api.storage.models;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

public class DeviceModel {

    @NotNull(message = "El id del modelo no puede ser nulo.")
    @NotNegative(message = "El id del modelo no puede ser negativo.")
    private final int id;

    @NotNull(message = "El nombre del modelo no puede ser nulo.")
    @NotEmpty(message = "El nombre del modelo no puede ser vacío.")
    private final String name;

    @NotNull(message = "La marca no puede ser nula.")
    private final DeviceBrand brand;

    public DeviceModel(int id, String name, DeviceBrand brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DeviceBrand getBrand() {
        return brand;
    }

}
