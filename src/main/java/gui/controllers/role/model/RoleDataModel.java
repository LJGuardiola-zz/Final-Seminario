package gui.controllers.role.model;

import api.dto.DtoRole;
import io.datafx.controller.injection.scopes.FlowScoped;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

@FlowScoped
public class RoleDataModel {

    protected final ObjectProperty<ObservableList<Role>> data = new SimpleObjectProperty<>();

    private Role selected;

    public ObjectProperty<ObservableList<Role>> getData() {
        return data;
    }

    public Role getSelected() {
        return selected;
    }

    public void setSelected(Role role) {
        selected = role;
    }

    public void load(List<DtoRole> roles) {
        data.set(
                roles.stream().map(Role::new)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }

    public void delete(Role role) {
        data.get().remove(role);
    }

}
