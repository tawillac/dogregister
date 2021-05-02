package performance.happy.processing;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import performance.unhappy.logging.CustomLogger;
import shared.domain.Dog;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@State(Scope.Benchmark)
public class ReferenceDatabaseQueries {

    private String user = "root";
    private String password = "mysql";
    private String host = "localhost";
    private String port = "1234";
    private String database = "performanceTask";
    private String table = "dogs";
    private String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=UTC";

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());


    public ReferenceDatabaseQueries() {
        initDBDriver();
    }

    private void initDBDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public boolean containsDog(Dog dog) {
        logger.log(Level.INFO,"containsDog(" + dog + ")");

        String selectQuery = "SELECT * FROM " + table + " WHERE name=? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, dog.getName());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "SQLException: " + e.getMessage());
        }
        return false;
    }

    public Dog getDog(String dogName) {
        logger.log(Level.INFO, "getDog(" + dogName + ")");

        String selectQuery = "SELECT name, race, owner, birthday FROM " + table + " WHERE name=? LIMIT 1";
        Dog result = null;
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, dogName);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String race = rs.getString("race");
                String owner = rs.getString("owner");
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                result = new Dog(name, race, owner, LocalDate.from(birthday));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "SQLException: " + e.getMessage());
        }
        return result;
    }

    public void insertDog(final Dog dog) {
        logger.log(Level.INFO, "insertDog( " + dog + ")");
        String insertQuery = "INSERT INTO " + table + " (name, race, owner, birthday) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, dog.getName());
            preparedStatement.setString(2, dog.getRace());
            preparedStatement.setString(3, dog.getOwner());
            preparedStatement.setDate(4, Date.valueOf(dog.getBirthday()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.WARNING, "SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception: " + e.getMessage());
        }
    }

    public void updateDog(final Dog dog) {
        logger.log(Level.INFO, "updateDog(" + dog.getName() + ")");
        String updateQuery = "UPDATE " + table + " SET owner=? WHERE name=?";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, dog.getOwner());
            preparedStatement.setString(2, dog.getName());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.WARNING,"SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception: " + e.getMessage());
        }
    }

    public Map<String, Integer> getNumberOfDogsPerRace() {
        logger.log(Level.INFO, "getNumberOfDogsPerRace()");

        String selectQuery = "SELECT race, COUNT(race) FROM dogs GROUP BY race;";
        Map<String, Integer> raceCount = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String race = rs.getString(1);
                Integer numberOfDogs = rs.getInt(2);
                raceCount.put(race, numberOfDogs);
            }

        } catch (SQLException ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
        return raceCount;
    }

    public float getAverageNumberOfDogsPerOwner() {
        logger.log(Level.INFO, "getAverageNumberOfDogsPerOwner()");

        String selectQuery = "SELECT COUNT(DISTINCT name) / COUNT(DISTINCT owner) FROM " + table ;
        float result = -1;
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                result = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
        return result;
    }
}
