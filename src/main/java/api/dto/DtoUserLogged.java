package api.dto;

import api.storage.models.NaturalPerson;
import api.storage.models.User;

public class DtoUserLogged {

    private final Integer ID;

    private final String username;

    private final String email;

    private final DtoUserState state;

    private final DtoRole role;

    private final DtoNaturalPerson person;

    public DtoUserLogged(User user, NaturalPerson person) {
        this.ID = user.getID();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.state = new DtoUserState(user.getState());
        this.role = new DtoRole(user.getRole());
        this.person = new DtoNaturalPerson(person);
    }

    public Integer getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public DtoUserState getState() {
        return state;
    }

    public DtoRole getRole() {
        return role;
    }

    public DtoNaturalPerson getPerson() {
        return person;
    }

}
