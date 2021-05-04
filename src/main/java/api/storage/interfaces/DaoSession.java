package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.Session;

import java.util.Optional;

public interface DaoSession {

    Optional<Session> search(String ip, String token)
            throws DaoException;

    void insert(Session session)
            throws DaoException;

    void delete(Integer userId, String ip)
            throws DaoException;

}
