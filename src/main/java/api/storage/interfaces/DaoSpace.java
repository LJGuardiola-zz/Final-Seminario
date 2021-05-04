package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.*;

import java.util.List;
import java.util.Optional;

public interface DaoSpace {

    Optional<Space> search(int id)
            throws DaoException;

    Optional<OpenSpace> searchOpen(int id)
            throws DaoException;

    Optional<ClosedSpace> searchClosed(int id)
            throws DaoException;

    List<OpenSpace> getAllOpen()
            throws DaoException;

    List<ClosedSpace> getAllClosed()
            throws DaoException;

    List<Space> getAll()
            throws DaoException;

    Optional<City> getCity(int id)
            throws DaoException;

    List<City> getAllCities()
            throws DaoException;

    void insert(OpenSpace openSpace)
            throws DaoException;

    void insert(ClosedSpace closedSpace)
            throws DaoException;

    void update(OpenSpace openSpace)
            throws DaoException;

    void update(ClosedSpace closedSpace)
            throws DaoException;

    void updateResponsible(ClosedSpace closedSpace, Person person)
            throws DaoException;

    void delete(int id)
            throws DaoException;

}
