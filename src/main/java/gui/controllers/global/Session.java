package gui.controllers.global;

import api.Api;
import api.dto.DtoUser;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class Session {

    private final ReadOnlyObjectWrapper<DtoUser> userLogged = new ReadOnlyObjectWrapper<>();
    private final ReadOnlyBooleanWrapper isLogged = new ReadOnlyBooleanWrapper(false);

    private static final Session INSTANCE = new Session();

    private Session() {
        userLogged.addListener((o, oldValue, newValue) -> isLogged.set(newValue != null));
    }

    public static Session getInstance() {
        return INSTANCE;
    }

    public void login(String username, String password) {
        userLogged.set(
                new DtoUser(
                        Api.getInstance().app().login(username, password)
                )
        );
    }

    public void logout() {
        userLogged.set(null);
        Api.getInstance().app().logout();
    }

    public boolean isLogged() {
        return isLogged.get();
    }

    public ReadOnlyBooleanProperty isLoggedProperty() {
        return isLogged.getReadOnlyProperty();
    }

    public DtoUser getUserLogged() {
        return userLogged.get();
    }

    public boolean isCitizen() {
        return userLogged.get().getRole().getID() == 3;
    }

    public boolean isGob() {
        return userLogged.get().getRole().getID() == 2;
    }

    public boolean hasPermission(String permission) {
        return userLogged.get().getRole().getPermissions().contains(permission);
    }
}
