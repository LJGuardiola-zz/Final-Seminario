package api.storage.runner;

import api.storage.exceptions.DaoException;
import api.storage.runner.interfaces.ConnectionProvider;
import api.storage.runner.interfaces.RequestWithResult;
import api.storage.runner.interfaces.RequestWithoutResult;
import api.storage.runner.interfaces.ResultSetHandler;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class Runner {

    private final ConnectionProvider connectionProvider;

    public Runner(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    private Connection getConnection() throws SQLException {
        return Objects.requireNonNull(
                connectionProvider.getConnection(), "Conexión nula."
        );
    }

    public <T> T runWithResult(RequestWithResult<T> request) throws DaoException {
        try (Connection connection = getConnection()) {
            return request.process(
                    new Executor(connection)
            );
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException);
        }
    }

    public void runWithoutResult(RequestWithoutResult request) throws DaoException {
        try (Connection connection = getConnection()) {
            request.process(
                    new Executor(connection)
            );
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException);
        }
    }

    public void runInTransaction(RequestWithoutResult request) throws DaoException {
        try (Connection connection = getConnection()) {
            try {
                connection.setAutoCommit(false);
                request.process(
                        new Executor(connection)
                );
                connection.commit();
            } catch (Throwable throwable) {
                if (connection != null)
                    connection.rollback();
                throw throwable;
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException);
        }
    }

    public static class Executor {

        private final Connection connection;

        private Executor(Connection connection) {
            this.connection = connection;
        }

        private void fillPreparedStatement(PreparedStatement ps, Object[] params) throws SQLException {
            for (int i = 0; i < params.length; i++)
                ps.setObject(i + 1, params[i]);
        }

        public <T> T query(String SQL, ResultSetHandler<T> handler, Object... params) throws SQLException, DaoException {
            try (PreparedStatement ps = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                fillPreparedStatement(ps, params);
                try (ResultSet resultSet = ps.executeQuery()) {
                    T result = handler.handle(resultSet);
                    if (result != null) {
                        List<ConstraintViolation> violations = new Validator().validate(result);
                        if (violations.isEmpty())
                            return result;
                        else throw new DaoException(violations);
                    } else return null;
                }
            }
        }

        public int insert(String sql, Object... params) throws SQLException {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                fillPreparedStatement(ps, params);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next())
                        return rs.getInt(1);
                    else throw new SQLException("No se pudo obtener la clave generada.");
                }
            }
        }

        public void update(String sql, Object... params) throws SQLException {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                fillPreparedStatement(ps, params);
                ps.executeUpdate();
            }
        }

    }

}
