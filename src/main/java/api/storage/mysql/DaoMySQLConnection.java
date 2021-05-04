package api.storage.mysql;

import api.storage.runner.interfaces.ConnectionProvider;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoMySQLConnection implements ConnectionProvider {

    private String
            dbHost = "localhost",
            dbPort = "3306",
            dbName = "epp_guardiola",
            dbUser = "epp_guardiola",
            dbPass = "epp_guardiola";

    private DaoMySQLConnection() {
        try (Reader reader = new FileReader("stg/database.properties")) {

            Properties properties = new Properties();
            properties.load(reader);

            dbHost = properties.getProperty("db_host");
            dbPort = properties.getProperty("db_port");
            dbName = properties.getProperty("db_name");
            dbUser = properties.getProperty("db_user");
            dbPass = properties.getProperty("db_pass");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DaoMySQLConnection getInstance() {
        return DaoMySQLConnectionHolder.INSTANCE;
    }

    private static class DaoMySQLConnectionHolder {
        private static final DaoMySQLConnection INSTANCE = new DaoMySQLConnection();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useLegacyDatetimeCode=false", dbUser, dbPass
        );
    }

}
