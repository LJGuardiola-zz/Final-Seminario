package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.Permission;
import api.storage.models.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DaoRole {

    Optional<Role> search(int id)
            throws DaoException;

    List<Role> getAll()
            throws DaoException;

    void insert(Role role)
            throws DaoException;

    void updatePermissions(Role role, Set<Permission> permissions)
            throws DaoException;

    void update(Role role)
            throws DaoException;

    void delete(int id)
            throws DaoException;

}
