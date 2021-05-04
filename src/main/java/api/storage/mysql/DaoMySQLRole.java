package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoRole;
import api.storage.models.Permission;
import api.storage.models.Role;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static api.storage.runner.Mappers.roleMapper;

public class DaoMySQLRole extends DaoMySQL implements DaoRole {

    private static final String
            SQL_SELECT_ALL      = "SELECT * FROM roles " +
                    "LEFT JOIN role_has_permission on roles.id = role_has_permission.role_id " +
                    "LEFT JOIN permissions on permissions.id = role_has_permission.permission_id ",
            SQL_SELECT_BY_ID    = "SELECT * FROM roles " +
                    "LEFT JOIN role_has_permission on roles.id = role_has_permission.role_id " +
                    "LEFT JOIN permissions on permissions.id = role_has_permission.permission_id " +
                    "WHERE roles.id = ?",
            SQL_ASSIGN_PERM     = "INSERT INTO role_has_permission (role_id, permission_id) VALUES (?, ?)",
            SQL_UNASSIGN_PERM   = "DELETE FROM role_has_permission WHERE role_id = ? AND permission_id = ?",
            SQL_INSERT          = "INSERT INTO roles (name, enabled) VALUES (?, ?)",
            SQL_UPDATE          = "UPDATE roles SET name = ?, enabled = ? WHERE id = ?",
            SQL_DELETE          = "DELETE FROM roles WHERE id = ?";

    private static final ResultSetHandler<Role> ONE_ROLE = rs -> {
        if (rs.next()) {
            Role role = Mappers.roleMapper().apply(rs);
            do {
                Mappers.permissionMapperForRole().apply(rs)
                        .ifPresent(role::assignPermission);
            } while (rs.next());
            return role;
        }
        return null;
    };

    private static final ResultSetHandler<List<Role>> MANY_ROLES = rs -> {
        List<Role> roles = new ArrayList<>();
        while (rs.next()) {
            Role role = Mappers.roleMapper().apply(rs);
            while (true) {
                Mappers.permissionMapperForRole().apply(rs)
                        .ifPresent(role::assignPermission);
                if (rs.next()) {
                    if (rs.getInt("roles.id") != role.getID()) {
                        rs.previous();
                        break;
                    }
                } else break;
            }
            roles.add(role);
        }
        return roles;
    };

    @Override
    public Optional<Role> search(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_BY_ID, ONE_ROLE, id)
                )
        );
    }

    @Override
    public List<Role> getAll() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL, MANY_ROLES)
        );
    }

    @Override
    public void insert(Role role) throws DaoException {
        runWithoutResult(
                ex -> role.setID(
                        ex.insert(SQL_INSERT,
                                role.getName(),
                                role.isEnabled()
                        )
                )
        );
    }

    @Override
    public void updatePermissions(Role role, Set<Permission> newPermissions) throws DaoException {
        runInTransaction(ex -> {
            Set<Permission> oldPermissions = role.getPermissions();
            for (Permission newPermission : newPermissions) {
                if (!oldPermissions.contains(newPermission)) {
                    ex.update(SQL_ASSIGN_PERM, role.getID(), newPermission.getID());
                }
            }
            for (Permission oldPermission : oldPermissions) {
                if (!newPermissions.contains(oldPermission)) {
                    ex.update(SQL_UNASSIGN_PERM, role.getID(), oldPermission.getID());
                }
            }
        });
    }

    @Override
    public void update(Role role) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_UPDATE,
                        role.getName(),
                        role.isEnabled(),
                        role.getID()
                )
        );
    }

    @Override
    public void delete(int id) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_DELETE, id)
        );
    }

}
