package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.runner.Runner;
import api.storage.runner.interfaces.Mapper;
import api.storage.runner.interfaces.RequestWithResult;
import api.storage.runner.interfaces.RequestWithoutResult;
import api.storage.runner.interfaces.ResultSetHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class DaoMySQL {

    protected static <T> ResultSetHandler<T> getHandlerForOne(Mapper<T> mapper) {
        return rs -> {
            if (rs.next())
                return mapper.apply(rs);
            return null;
        };
    }

    protected static <T> ResultSetHandler<List<T>> getHandlerForMany(Mapper<T> mapper) {
        return rs -> {
            List<T> results = new ArrayList<>();
            while (rs.next())
                results.add(mapper.apply(rs));
            return results;
        };
    }

    private Runner getRunner() {
        return new Runner(
                DaoMySQLConnection.getInstance()
        );
    }

    protected  <T> T runWithResult(RequestWithResult<T> request) throws DaoException {
        return getRunner().runWithResult(request);
    }

    protected void runWithoutResult(RequestWithoutResult request) throws DaoException {
        getRunner().runWithoutResult(request);
    }

    protected void runInTransaction(RequestWithoutResult request) throws DaoException {
        getRunner().runInTransaction(request);
    }

}
