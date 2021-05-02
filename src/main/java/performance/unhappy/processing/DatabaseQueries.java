package performance.unhappy.processing;

import shared.domain.Dog;
import org.openjdk.jmh.annotations.*;
import performance.unhappy.logging.CustomLogger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@State(Scope.Benchmark)
public class DatabaseQueries {

    private String user = "root";
    private String password = "mysql";
    private String host = "localhost";
    private String port = "1234";
    private String database = "performanceTask";
    private String table = "dogs";
    private String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=UTC";

    private CustomLogger logger = new CustomLogger();


    public DatabaseQueries() {
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
        logger.log("containsDog() " + dog);

        String selectQuery = "SELECT * FROM " + table;
        boolean contains = false;
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement ps = conn.prepareStatement(selectQuery);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                if (name.equalsIgnoreCase(dog.getName())) {
                    contains = true;
                }
            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        }
        return contains;
    }

    public Dog getDog(String dogName) {
        logger.log("getDog() " + dogName);

        String selectQuery = "SELECT name, race, owner, birthday FROM " + table;
        Dog result = null;
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement ps = conn.prepareStatement(selectQuery);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String race = rs.getString("race");
                String owner = rs.getString("owner");
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                if (name.equalsIgnoreCase(dogName)) {
                    result = new Dog(name, race, owner, LocalDate.from(birthday));
                }
            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        }
        return result;
    }

    public void insertDog(final Dog dog) {
        logger.log("insertDog(" + dog + ")");
        String insertQuery = "INSERT INTO " + table + " (name, race, owner, birthday) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, dog.getName());
            preparedStatement.setString(2, dog.getRace());
            preparedStatement.setString(3, dog.getOwner());
            preparedStatement.setDate(4, java.sql.Date.valueOf(dog.getBirthday()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.warn("Exception: " + e.getMessage());
        }
    }

    public void updateDog(final Dog dog) {
        logger.log("updateDog(" + dog.getName() + ")");
        String updateQuery = "UPDATE " + table + " SET owner=? WHERE name=?";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, dog.getOwner());
            preparedStatement.setString(2, dog.getName());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.warn("Exception: " + e.getMessage());
        }
    }

    public ArrayList<Dog> getAllDogsOfRace(String raceName) {
        logger.log("getAllDogsOfRace(" + raceName + ")");

        String selectQuery = "SELECT name, race, owner, birthday FROM " + table + " WHERE race=?";
        ArrayList<Dog> dogs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, raceName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String race = rs.getString("race");
                String owner = rs.getString("owner");
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                dogs.add(new Dog(name, race, owner, LocalDate.from(birthday)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dogs;
    }

    public ArrayList<String> getAllOwners() {
        logger.log("getAllOwners()");

        String selectQuery = "SELECT owner FROM " + table;
        ArrayList<String> owners = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement ps = conn.prepareStatement(selectQuery);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String owner = rs.getString("owner");
                if (!owners.contains(owner)) {
                    owners.add(owner);
                }

            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        }
        return owners;
    }

    public ArrayList<Dog> getAllDogs() {
        logger.log("getAllDogs()");

        String selectQuery = "SELECT name, race, owner, birthday FROM " + table ;
        ArrayList<Dog> dogs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String race = rs.getString("race");
                String owner = rs.getString("owner");
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                dogs.add(new Dog(name, race, owner, LocalDate.from(birthday)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dogs;
    }
}
