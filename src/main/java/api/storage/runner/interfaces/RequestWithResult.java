package api.storage.runner.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.runner.Runner;

import java.sql.SQLException;

@FunctionalInterface
public interface RequestWithResult<T> {
    T process(Runner.Executor ex) throws SQLException, DaoException;
}
