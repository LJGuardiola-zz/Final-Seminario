package api.storage.models;

import com.sun.istack.internal.Nullable;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Role {

    @Nullable
    @NotNegative(message = "El ID del rol no puede ser negativo.")
    private Integer ID;

    @NotNull(message = "El nombre del rol no puede ser nulo.")
    @NotBlank(message = "El nombre del rol no puede estar en blanco.")
    private String name;

    private Set<Permission> permissions = new HashSet<>();

    private boolean enabled;

    public Role(String name, boolean enabled) {
        this(null, name, enabled);
    }

    public Role(Integer ID, String name, boolean enabled) {
        this.ID = ID;
        this.name = name;
        this.enabled = enabled;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void assignPermission(Permission permission) {
        permissions.add(permission);
    }

    public void assignPermissions(Permission... permissions) {
        for (Permission permission : permissions)
            assignPermission(permission);
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = new HashSet<>(permissions);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDisabled() {
        return !enabled;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(ID, role.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

}
