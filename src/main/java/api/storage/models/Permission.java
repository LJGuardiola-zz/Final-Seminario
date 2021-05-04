package api.storage.models;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

import java.util.Objects;

public class Permission {

    @NotNull(message = "El ID del permiso no puede ser nulo.")
    @NotNegative(message = "El ID del permiso no puede ser negativo.")
    private final Integer ID;

    @NotNull(message = "El código del permiso no puede ser nulo.")
    @NotBlank(message = "El código del permiso no puede estar en blanco.")
    private final String code;

    @NotNull(message = "La descripción del permiso no puede ser nulo.")
    @NotBlank(message = "La descripción del permiso no puede estar en blanco.")
    private final String description;

    public Permission(Integer ID, String code, String description) {
        this.ID = ID;
        this.code = code;
        this.description = description;
    }

    public Integer getID() {
        return ID;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return ID.equals(that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

}
