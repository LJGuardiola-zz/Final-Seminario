package api.dto;

import api.storage.models.NaturalPerson;
import api.storage.models.Person;

import java.util.Objects;

public class DtoPerson {
    private final int id;
    private final String name;
    private final String cui;
    private final String type;

    public DtoPerson(Person person) {
        this.id = person.getID();
        this.name = person.getName();
        this.cui = person.getCUI();
        this.type = person instanceof NaturalPerson ? "Física" : "Jurídica";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCui() {
        return cui;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " (" + cui + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoPerson dtoPerson = (DtoPerson) o;
        return id == dtoPerson.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
