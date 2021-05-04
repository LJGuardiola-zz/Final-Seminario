package api.modules;

import api.dto.DtoRole;
import api.exceptions.ApiException;
import api.exceptions.InvalidDataException;
import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoManager;
import api.storage.models.Permission;
import api.storage.models.Role;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static api.storage.models.Perms.*;

public class ModuleRole extends Module {

    public ModuleRole(DaoManager daoManager) {
        super(daoManager);
    }

    private Role getRole(int id) {
        try {
            Optional<Role> result = daoRole().search(id);
            if (result.isPresent())
                return result.get();
            throw new ApiException("No se encontró ningún rol con ID " + id);
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public DtoRole search(int id) {
        verifyAuthorization(role_read);
        return new DtoRole(getRole(id));
    }

    private List<DtoRole> getDtoRoles(List<Role> roles) {
        return roles.stream()
                .map(DtoRole::new)
                .collect(Collectors.toList());
    }

    public List<DtoRole> getAll() {
        try {
            verifyAuthorization(role_read);
            return getDtoRoles(
                    daoRole().getAll()
            );
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public List<DtoRole> getAllAssignable() {
        return getAll().stream()
                .filter(dtoRole -> dtoRole.getID() != 3)
                .filter(DtoRole::isEnabled)
                .collect(Collectors.toList());
    }

    public DtoRole create(String name, boolean enable) {
        try {
            verifyAuthorization(role_create);
            Role role = new Role(name, enable);
            List<ConstraintViolation> violations = new Validator().validate(role);
            if (violations.isEmpty()) {
                daoRole().insert(role);
                return new DtoRole(role);
            } else throw new InvalidDataException(violations);
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public void enable(int id) {
        verifyAuthorization(role_update);
        Role role = getRole(id);
        if (!role.isEnabled()) {
            role.enable();
            updateRole(role);
        } else throw new ApiException("El rol " + role.getName() + " ya se encuentra activo.");
    }

    public void disable(int id) {
        verifyAuthorization(role_update);
        Role role = getRole(id);
        if (!role.isDisabled()) {
            role.disable();
            updateRole(role);
        } else throw new ApiException("El rol " + role.getName() + " ya se encuentra desactivado.");
    }

    public void assignPermission(int roleId, Set<String> perms) {
        try {
            verifyAuthorization(role_update);
            Role role = getRole(roleId);

            Set<Permission> newPermissions = daoPermission().getAll().stream()
                    .filter(permission -> perms.contains(permission.getCode()))
                    .collect(Collectors.toSet());

            daoRole().updatePermissions(role, newPermissions);

        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public DtoRole update(int roleId, String name, boolean enabled) {
        verifyAuthorization(role_update);
        Role role = getRole(roleId);
        role.setName(name);
        if (enabled) role.enable();
        else role.disable();
        List<ConstraintViolation> violations = new Validator().validate(role);
        if (violations.isEmpty()) {
            updateRole(role);
            return new DtoRole(role);
        } else throw new InvalidDataException(violations);
    }

    private void updateRole(Role role) {
        try {
            if (role.getID() > 3)
                daoRole().update(role);
            else throw new ApiException("Este rol no se puede editar.");
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public void delete(int id) {
        try {
            verifyAuthorization(role_delete);
            if (id > 3)
                daoRole().delete(id);
            else throw new ApiException("Este rol es imprescindible para el correcto funcionamiento del programa.");
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

}
