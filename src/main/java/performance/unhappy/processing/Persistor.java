package performance.unhappy.processing;

import shared.domain.Dog;
import performance.unhappy.logging.CustomLogger;


public class Persistor {

    private DatabaseQueries databaseQueries = new DatabaseQueries();
    private CustomLogger logger = new CustomLogger();

    public void persistDog(Dog dog) {
        logger.log("persistDog(" + dog + ")");
        if (databaseQueries.containsDog(dog)) {
            Dog currentEntry = databaseQueries.getDog(dog.getName());
            currentEntry.setOwner(dog.getOwner()); // Update owner if dog is already in database
            databaseQueries.updateDog(currentEntry);
        } else {
            databaseQueries.insertDog(dog);
        }
    }

}
