package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.Permission;

import java.util.List;
import java.util.Optional;

public interface DaoPermission {

    List<Permission> getAll()
            throws DaoException;

    Optional<Permission> search(int userId, String permissionCode)
            throws DaoException;

}
