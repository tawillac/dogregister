package shared;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Locale;

public class DatabasePreparer {

    private static final Logger LOGGER = Logger.getLogger(DatabasePreparer.class);

    private static final String USER = "root";
    private static final String PASSWORD = "mysql";
    private static final String HOST = "localhost";
    private static final String PORT = "1234";
    private static final String DATABASE = "performanceTask";
    private static final String TABLE = "dogs";

    private static final String DB_CONNECTION_URL = "jdbc:mysql://" + HOST + ":" + PORT;
    private static final String SERVER_TIMEZONE_UTC = "?serverTimezone=UTC";
    private static final String CONNECTION_URL = DB_CONNECTION_URL + SERVER_TIMEZONE_UTC;
    private static final String CONNECTION_URL_WITH_DATABASE = DB_CONNECTION_URL + "/" + DATABASE + SERVER_TIMEZONE_UTC;

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE + " " +
            "(name VARCHAR(100), " +
            " race VARCHAR(100), " +
            " owner VARCHAR(100), " +
            " birthday DATETIME, " +
            " PRIMARY KEY ( name ))";
    private static final String SELECT_ALL_SQL = "SELECT COUNT(*) AS rowcount FROM " + TABLE;
    private static final String CREATE_DATABASE_SQL = "CREATE DATABASE IF NOT EXISTS " + DATABASE;
    private static final String TRUNCATE_TABLE_SQL = "TRUNCATE TABLE " + TABLE;
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE;

    public static void setUpDatabase() {
        Locale.setDefault(Locale.ENGLISH);
        org.apache.log4j.BasicConfigurator.configure();

        createDataBaseIfNotExist();
        createTableIfNotExist();
    }

    public static void createDataBaseIfNotExist() {
        executeUpdate(CONNECTION_URL, CREATE_DATABASE_SQL);
    }

    public static void createTableIfNotExist() {
        executeUpdate(CONNECTION_URL_WITH_DATABASE, CREATE_TABLE_SQL);
    }

    public static void truncateTable() {
        executeUpdate(CONNECTION_URL_WITH_DATABASE, TRUNCATE_TABLE_SQL);
    }

    public static void dropTable() {
        executeUpdate(CONNECTION_URL_WITH_DATABASE, DROP_TABLE_SQL);
    }

    public static int getRowCount() {

        try (Connection conn = DriverManager.getConnection(CONNECTION_URL_WITH_DATABASE, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(SELECT_ALL_SQL)
        ) {
            if (resultSet.next()) {
                return resultSet.getInt("rowcount");
            } else return 0;
        } catch (SQLException e) {
            LOGGER.error("Get row count failed!", e);
            return 0;
        }
    }

    private static void executeUpdate(String connectionUrl, String query) {
        try (Connection conn = DriverManager.getConnection(connectionUrl, USER, PASSWORD);
             Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(query);
            LOGGER.info(String.format("Execute update '%s' successful", query));
        } catch (SQLException e) {
            LOGGER.error(String.format("Execute update '%s' failed", query), e);
        }
    }
}
