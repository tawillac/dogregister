package performance.happy.processing;

import org.openjdk.jmh.annotations.*;
import shared.domain.Dog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReferencePersistor {

    private ReferenceDatabaseQueries databaseQueries = new ReferenceDatabaseQueries();
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public void testPersistDogs() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(Dog.builder().name("Wauzi").race("Dackel").owner("Max Mustermann").build());
        dogs.add(Dog.builder().name("Minnie").race("Chihuahua").owner("Martina Mustermann").build());
        this.persistDogs(dogs);
    }

    public void persistDogs(List<Dog> dogs) {
        logger.log(Level.INFO, "persistDogs()");

        dogs.forEach(dog -> {
            logger.log(Level.INFO, "persistDog(" + dog.getName() + ")");
            if (databaseQueries.containsDog(dog)) {
                Dog currentEntry = databaseQueries.getDog(dog.getName());
                currentEntry.setOwner(dog.getOwner()); // Update owner if dog is already in database
                databaseQueries.updateDog(currentEntry);
            } else {
                databaseQueries.insertDog(dog);
            }
        });
    }
}
