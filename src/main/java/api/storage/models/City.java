package api.storage.models;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

public class City {

    @NotNull(message = "El ID de una ciudad no puede ser nulo.")
    @NotNegative(message = "El ID de una ciudad no puede ser negativo.")
    private final Integer ID;

    @NotNull(message = "El nombre de una ciudad no puede ser nulo.")
    @NotEmpty(message = "El nombre de una ciudad no puede estar vacío.")
    private final String name;

    @NotNull(message = "El código postal de una ciudad no puede ser nulo.")
    @NotEmpty(message = "El código postal de una ciudad no puede estar vacío.")
    private final String postcode;

    public City(Integer ID, String name, String postcode) {
        this.ID = ID;
        this.name = name;
        this.postcode = postcode;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPostcode() {
        return postcode;
    }

}
