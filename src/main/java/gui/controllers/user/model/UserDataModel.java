package gui.controllers.user.model;

import api.dto.DtoUser;
import io.datafx.controller.injection.scopes.FlowScoped;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

@FlowScoped
public class UserDataModel {

    protected final ObjectProperty<ObservableList<User>> data = new SimpleObjectProperty<>();

    private User selected;

    public ObjectProperty<ObservableList<User>> getData() {
        return data;
    }

    public User getSelected() {
        return selected;
    }

    public void setSelected(User user) {
        selected = user;
    }

    public void load(List<DtoUser> users) {
        data.set(
                users.stream().map(User::new)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }

    public void delete(User user) {
        data.get().remove(user);
    }

}
