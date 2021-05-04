package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.City;

import java.util.List;

public interface DaoCity {

    List<City> getAll()
            throws DaoException;

}
