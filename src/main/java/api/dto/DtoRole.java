package api.dto;

import api.storage.models.Permission;
import api.storage.models.Role;

import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;

public class DtoRole {

    private final int ID;
    private final String name;
    private final boolean enabled;
    private final Set<String> permissions;

    public DtoRole(Role role) {
        this(role.getID(), role.getName(), role.isEnabled(), role.getPermissions());
    }

    public DtoRole(int ID, String name, boolean enabled, Set<Permission> permissions) {
        this.ID = ID;
        this.name = name;
        this.enabled = enabled;
        this.permissions = permissions.stream()
                .map(Permission::getCode)
                .collect(Collectors.toSet());
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoRole dtoRole = (DtoRole) o;
        return ID == dtoRole.ID && name.equals(dtoRole.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name);
    }

}
