package api.storage.models;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Past;

import java.time.LocalDate;

public class NaturalPerson extends Person {

    @NotNull(message = "El CUIL no puede ser nulo.")
    @NotEmpty(message = "El CUIL no puede estar en blanco.")
    @MatchPattern(pattern = "\\b(20|23|24|27)(-){1}[0-9]{8}(-){1}[0-9]", message = "El CUIL no tiene un formato válido")
    private final String CUIL;

    @NotNull(message = "El Nombre no puede ser nulo.")
    @NotEmpty(message = "El Nombre no puede estar en blanco.")
    private String firstName;

    @NotNull(message = "El Apellido no puede ser nulo.")
    @NotEmpty(message = "El Apellido no puede estar en blanco.")
    private String lastName;

    @NotNull(message = "La fecha de nacimiento no puede ser nulo.")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasado.")
    private final LocalDate birthdate;

    public NaturalPerson(String CUIL, String firstName, String lastName, LocalDate birthdate) {
        this(null, CUIL, firstName, lastName, birthdate);
    }

    public NaturalPerson(Integer ID, String CUIL, String firstName, String lastName, LocalDate birthdate) {
        super(ID);
        this.CUIL = CUIL;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    @Override
    public String getCUI() {
        return CUIL;
    }

    @Override
    public String getName() {
        return String.join(" ", firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

}
