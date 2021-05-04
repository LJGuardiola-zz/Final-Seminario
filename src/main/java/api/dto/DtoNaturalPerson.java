package api.dto;

import api.storage.models.NaturalPerson;

import java.time.LocalDate;
import java.util.Optional;

public class DtoNaturalPerson {

    private final int id;
    private final String CUIL;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthdate;

    public DtoNaturalPerson(NaturalPerson person) {
        this.id = person.getID();
        this.CUIL = person.getCUI();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.birthdate = person.getBirthdate();
    }

    public int getId() {
        return id;
    }

    public String getCUIL() {
        return CUIL;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

}
