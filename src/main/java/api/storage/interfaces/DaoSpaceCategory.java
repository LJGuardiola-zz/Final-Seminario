package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.SpaceCategory;

import java.util.List;
import java.util.Optional;

public interface DaoSpaceCategory {

    Optional<SpaceCategory> search(int id) throws DaoException;

    List<SpaceCategory> getAll() throws DaoException;

    void insert(SpaceCategory spaceCategory) throws DaoException;

    void update(SpaceCategory spaceCategory) throws DaoException;

    void delete(int id) throws DaoException;

}
