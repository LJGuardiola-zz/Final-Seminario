package api.storage.models;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

public class DeviceBrand {

    @NotNull(message = "El id de la marca no puede ser nulo.")
    @NotNegative(message = "El id de la marca no puede ser negativo.")
    private final int id;

    @NotNull(message = "El nombre de la marca no puede ser nulo.")
    @NotEmpty(message = "El nombre de la marca no puede ser vacío.")
    private final String name;

    public DeviceBrand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
