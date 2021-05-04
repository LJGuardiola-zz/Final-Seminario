package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoPermission;
import api.storage.models.Permission;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.util.List;
import java.util.Optional;

public class DaoMySQLPermission extends DaoMySQL implements DaoPermission {

    private static final String
            SQL_SELECT_ALL  = "SELECT * FROM permissions",
            SQL_SEARCH      = "SELECT * FROM permissions " +
                                "JOIN role_has_permission on permissions.id = role_has_permission.permission_id " +
                                "WHERE role_id = ? AND permissions.code = ?";

    private static final ResultSetHandler<Permission>
            ONE_PERMISSION = getHandlerForOne(Mappers.permissionMapper());

    private static final ResultSetHandler<List<Permission>>
            MANY_PERMISSIONS = getHandlerForMany(Mappers.permissionMapper());

    @Override
    public List<Permission> getAll() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL, MANY_PERMISSIONS)
        );
    }

    @Override
    public Optional<Permission> search(int userId, String permissionCode) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SEARCH, ONE_PERMISSION, userId, permissionCode)
                )
        );
    }

}
