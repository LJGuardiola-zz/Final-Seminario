package api.storage.models;

import com.sun.istack.internal.Nullable;
import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

public class User {

    @Nullable
    @NotNegative(message = "El ID del usuario no puede ser nulo.")
    private Integer ID;

    @NotNull(message = "El nombre de usuario no puede ser nulo.")
    @NotBlank(message = "El nombre de usuario no puede estar en blanco.")
    private final String username;

    @NotNull(message = "El correo electrónico no puede ser nulo.")
    @NotBlank(message = "El correo electrónico no puede estar en blanco.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    private String email;

    @NotNull(message = "La contraseña no puede ser nula.")
    @NotBlank(message = "La contraseña no puede estar en blanco.")
    private String password;

    @NotNull(message = "El estado no puede ser nulo.")
    private State state;

    @NotNull(message = "El rol no puede ser nulo.")
    private final Role role;

    public User(String username, String email, String password, Role role) {
        this(null, username, email, password, State.ENABLED, role);
    }

    public User(String username, String email, String password, Role role, int state) {
        this(null, username, email, password, State.getByCode(state), role);
    }

    public User(Integer ID, String username, String email, String password, State state, Role role) {
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

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean passwordEquals(String password) {
        return this.password.equals(password);
    }

    public Role getRole() {
        return role;
    }

    public boolean isDisabled() {
        return State.DISABLED.equals(state);
    }

    public boolean isEnabled() {
        return State.ENABLED.equals(state);
    }

    public boolean isSuspended() {
        return State.SUSPENDED.equals(state);
    }

    public State getState() {
        return state;
    }

    public int getStateCode() {
        return state.getCode();
    }

    public void setStateWithCode(int code) {
        state = State.getByCode(code);
    }

    public void enable() {
        state = State.ENABLED;
    }

    public void disable() {
        state = State.DISABLED;
    }

    public void suspend() {
        state = State.SUSPENDED;
    }

    public enum State {

        DISABLED(0, "No activo"),
        ENABLED(1, "Activo"),
        SUSPENDED(2, "Suspendido");

        private final int code;
        private final String output;

        State(int code, String output) {
            this.code = code;
            this.output = output;
        }

        public static State getByCode(int code) {
            for (State state : State.values())
                if (state.code == code)
                    return state;
            throw new RuntimeException("No existe un estado de usuario con código " + code);
        }

        public int getCode() {
            return code;
        }

        public String getOutput() {
            return output;
        }

    }

}
