package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoCity;
import api.storage.models.City;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.util.List;

public class DaoMySQLCity extends DaoMySQL implements DaoCity {

    private static final String
            SQL_SELECT_ALL = "SELECT * FROM cities";

    private static final ResultSetHandler<List<City>>
            MANY_CITIES = getHandlerForMany(Mappers.cityMapper());

    @Override
    public List<City> getAll() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL, MANY_CITIES)
        );
    }

}
