package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.Movement;
import api.storage.models.Space;

import java.util.List;
import java.util.Optional;

public interface DaoMovement {

    List<Movement> getAll()
        throws DaoException;

    List<Movement> getCurrent(Space space)
        throws DaoException;

    Optional<Movement> searchCurrent(int deviceId)
        throws DaoException;

    void insert(Movement movement)
        throws DaoException;

    void delete(Movement movement)
        throws DaoException;
}
