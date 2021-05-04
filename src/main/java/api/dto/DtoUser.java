package api.dto;

import api.storage.models.User;

import java.util.Objects;

public class DtoUser {

    private final Integer ID;

    private final String username;

    private final String email;

    private final String password;

    private final DtoUserState state;

    private final DtoRole role;

    public DtoUser(User user) {
        this(
                user.getID(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                new DtoUserState(
                        user.getState().getCode(),
                        user.getState().getOutput()
                ),
                new DtoRole(user.getRole())
        );
    }

    public DtoUser(DtoUser dtoUser) {
        this(
                dtoUser.getID(),
                dtoUser.getUsername(),
                dtoUser.getEmail(),
                dtoUser.getPassword(),
                dtoUser.getState(),
                dtoUser.getRole()
        );
    }

    public DtoUser(Integer ID, String username, String email, String password, DtoUserState state, DtoRole role) {
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.state = state;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public DtoUserState getState() {
        return state;
    }

    public DtoRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "DtoUser{" +
                "ID=" + ID +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", state='" + state + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoUser dtoUser = (DtoUser) o;
        return ID.equals(dtoUser.ID) && username.equals(dtoUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, username);
    }

}
