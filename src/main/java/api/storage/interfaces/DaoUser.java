package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.User;

import java.util.List;
import java.util.Optional;

public interface DaoUser {

    Optional<User> search(int id)
            throws DaoException;

    Optional<User> search(String username)
            throws DaoException;

    List<User> getAll()
            throws DaoException;

    void insert(User user)
            throws DaoException;

    void update(User user)
            throws DaoException;

    void delete(int id)
            throws DaoException;

}
