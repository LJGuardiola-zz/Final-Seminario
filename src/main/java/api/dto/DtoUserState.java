package api.dto;

import api.storage.models.User;

import java.util.Objects;

public class DtoUserState {

    private final int code;
    private final String name;

    public DtoUserState(User.State state) {
        this(
                state.getCode(),
                state.getOutput()
        );
    }

    public DtoUserState(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoUserState that = (DtoUserState) o;
        return code == that.code && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

}
