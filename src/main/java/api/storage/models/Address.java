package api.storage.models;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class Address {

    @NotNull(message = "La calle no puede ser nula.")
    @NotEmpty(message = "La calle no puede ser vacía.")
    private final String street;

    @NotNull(message = "La ciudad no puede ser nula.")
    private final City city;

    public Address(String street, City city) {
        this.street = street;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public City getCity() {
        return city;
    }

}
