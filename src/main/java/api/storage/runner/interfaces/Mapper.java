package api.storage.runner.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface Mapper<T> {
    T apply(ResultSet rs) throws SQLException;
}
