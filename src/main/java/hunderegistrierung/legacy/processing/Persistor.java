package hunderegistrierung.legacy.processing;

import hunderegistrierung.legacy.logging.CustomLogger;
import shared.domain.Dog;


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
