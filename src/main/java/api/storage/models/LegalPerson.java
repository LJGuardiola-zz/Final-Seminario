package api.storage.models;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class LegalPerson extends Person {

    @NotNull(message = "El CUIT no puede ser nulo.")
    @NotEmpty(message = "El CUIT no puede estar en blanco.")
    @MatchPattern(pattern = "\\b(30|33|34)(-){1}[0-9]{8}(-){1}[0-9]", message = "El CUIT no tiene un formato válido")
    private final String CUIT;

    @NotNull(message = "El Nombre no puede ser nulo.")
    @NotEmpty(message = "El Nombre no puede estar en blanco.")
    private String name;

    public LegalPerson(String CUIT, String name) {
        this(null, CUIT, name);
    }

    public LegalPerson(Integer ID, String CUIT, String name) {
        super(ID);
        this.CUIT = CUIT;
        this.name = name;
    }

    @Override
    public String getCUI() {
        return CUIT;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
