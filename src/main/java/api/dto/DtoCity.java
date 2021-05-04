package api.dto;

import api.storage.models.City;

import java.util.Objects;

public class DtoCity {

    private final int id;
    private final String name;
    private final String postcode;

    public DtoCity(City city) {
        this.id = city.getID();
        this.name = city.getName();
        this.postcode = city.getPostcode();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPostcode() {
        return postcode;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoCity dtoCity = (DtoCity) o;
        return id == dtoCity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
