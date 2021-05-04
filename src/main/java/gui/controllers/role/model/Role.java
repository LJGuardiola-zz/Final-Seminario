package gui.controllers.role.model;

import api.dto.DtoRole;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class Role {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleBooleanProperty enabled;
    private final SimpleSetProperty<String> permissions;

    public Role(DtoRole role) {
        id = new SimpleIntegerProperty(role.getID());
        name = new SimpleStringProperty(role.getName());
        enabled = new SimpleBooleanProperty(role.isEnabled());
        permissions = new SimpleSetProperty<>(
                FXCollections.observableSet(role.getPermissions())
        );
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public SimpleBooleanProperty enabledProperty() {
        return enabled;
    }

    public ObservableSet<String> getPermissions() {
        return permissions.get();
    }

    public SimpleSetProperty<String> permissionsProperty() {
        return permissions;
    }

}
