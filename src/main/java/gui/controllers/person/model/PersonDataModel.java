package gui.controllers.person.model;

import api.dto.DtoPerson;
import api.dto.DtoUser;
import gui.controllers.user.model.User;
import io.datafx.controller.injection.scopes.FlowScoped;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

@FlowScoped
public class PersonDataModel {

    protected final ObjectProperty<ObservableList<Person>> data = new SimpleObjectProperty<>();

    private Person selected;

    public ObjectProperty<ObservableList<Person>> getData() {
        return data;
    }

    public Person getSelected() {
        return selected;
    }

    public void setSelected(Person person) {
        selected = person;
    }

    public void load(List<DtoPerson> persons) {
        data.set(
                persons.stream().map(Person::new)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }

    public void delete(Person person) {
        data.get().remove(person);
    }

}
