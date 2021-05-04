package gui.controllers.person.model;

import api.dto.DtoPerson;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty cui;
    private final StringProperty type;

    public Person(DtoPerson person) {
        this.id = new SimpleIntegerProperty(person.getId());
        this.name = new SimpleStringProperty(person.getName());
        this.cui = new SimpleStringProperty(person.getCui());
        this.type = new SimpleStringProperty(person.getType());
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getCui() {
        return cui.get();
    }

    public StringProperty cuiProperty() {
        return cui;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

}
