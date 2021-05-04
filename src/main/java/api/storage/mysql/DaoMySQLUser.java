package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoUser;
import api.storage.models.Role;
import api.storage.models.User;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoMySQLUser extends DaoMySQL implements DaoUser {

    public static final String
            SQL_SELECT_ALL          = "SELECT * FROM users " +
                                        "JOIN roles on roles.id = users.role_id " +
                                        "LEFT JOIN role_has_permission on roles.id = role_has_permission.role_id " +
                                        "LEFT JOIN permissions on permissions.id = role_has_permission.permission_id ",
            SQL_SELECT_BY_ID        = "SELECT * FROM users " +
                                        "JOIN roles on roles.id = users.role_id " +
                                        "LEFT JOIN role_has_permission on roles.id = role_has_permission.role_id " +
                                        "LEFT JOIN permissions on permissions.id = role_has_permission.permission_id " +
                                        "WHERE users.id = ?",
            SQL_SELECT_BY_USERNAME = "SELECT * FROM users " +
                                        "JOIN roles on roles.id = users.role_id " +
                                        "LEFT JOIN role_has_permission on roles.id = role_has_permission.role_id " +
                                        "LEFT JOIN permissions on permissions.id = role_has_permission.permission_id " +
                                        "WHERE users.username = ?",
            SQL_INSERT              = "INSERT INTO users (username, email, password, state, role_id) VALUES (?, ?, ?, ?, ?)",
            SQL_UPDATE              = "UPDATE users SET email = ?, password = ?, state = ? WHERE id = ?",
            SQL_DELETE              = "DELETE FROM users WHERE id = ?";

    private static final ResultSetHandler<User> ONE_USER = rs -> {
        if (rs.next()) {
            User user = Mappers.userMapper().apply(rs);
            do {
                Mappers.permissionMapperForRole().apply(rs)
                        .ifPresent(user.getRole()::assignPermission);
            } while (rs.next());
            return user;
        }
        return null;
    };

    private static final ResultSetHandler<List<User>> MANY_USERS = rs -> {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = Mappers.userMapper().apply(rs);
            while (true) {
                Mappers.permissionMapperForRole().apply(rs)
                        .ifPresent(user.getRole()::assignPermission);
                if (rs.next()) {
                    if (rs.getInt("users.id") != user.getID()) {
                        rs.previous();
                        break;
                    }
                } else break;
            }
            users.add(user);
        }
        return users;
    };

    @Override
    public Optional<User> search(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_BY_ID, ONE_USER, id)
                )
        );
    }

    @Override
    public Optional<User> search(String username) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_BY_USERNAME, ONE_USER, username)
                )
        );
    }

    @Override
    public List<User> getAll() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL, MANY_USERS)
        );
    }

    @Override
    public void insert(User user) throws DaoException {
        runWithoutResult(
                ex -> user.setID(
                        ex.insert(SQL_INSERT,
                                user.getUsername(),
                                user.getEmail(),
                                user.getPassword(),
                                user.getStateCode(),
                                user.getRole().getID()
                        )
                )
        );
    }

    @Override
    public void update(User user) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_UPDATE,
                        user.getEmail(),
                        user.getPassword(),
                        user.getStateCode(),
                        user.getID()
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
