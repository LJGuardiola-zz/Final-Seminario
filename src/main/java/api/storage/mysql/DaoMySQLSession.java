package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoSession;
import api.storage.models.Session;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.sql.Timestamp;
import java.util.Optional;

public class DaoMySQLSession extends DaoMySQL implements DaoSession {

    private static final String
            SQL_SELECT = "SELECT * FROM sessions WHERE ip = ? AND token = ?",
            SQL_INSERT = "INSERT INTO sessions (user_id, ip, token, update_at) VALUES (?, ?, ?, ?)",
            SQL_DELETE = "DELETE FROM sessions WHERE ip = ?",
            SQL_DELETE_BY_IP = "DELETE FROM sessions WHERE ip = ? AND id != ?";

    private static final ResultSetHandler<Session>
            ONE_SESSION = getHandlerForOne(Mappers.sessionMapper());

    @Override
    public Optional<Session> search(String ip, String token) throws DaoException {
        return Optional.ofNullable(
                runWithResult(ex -> {
                    Session session = ex.query(SQL_SELECT, ONE_SESSION, ip, token);
                    ex.update(SQL_DELETE_BY_IP, ip, session != null ? session.getID() : "");
                    return session;
                })
        );
    }

    @Override
    public void insert(Session session) throws DaoException {
        runWithoutResult(
                ex -> session.setID(
                        ex.insert(SQL_INSERT,
                                session.getUserID(),
                                session.getIP(),
                                session.getToken(),
                                Timestamp.from(
                                        session.getUpdateAt()
                                )
                        )
                )
        );
    }

    @Override
    public void delete(Integer userId, String ip) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_DELETE, ip)
        );
    }

}
