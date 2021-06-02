package hunderegistrierung.legacy.processing;

import shared.domain.Dog;
import org.openjdk.jmh.annotations.*;
import hunderegistrierung.legacy.logging.CustomLogger;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 3)
@Measurement(iterations = 10)
public class Evaluator {

    private DatabaseQueries databaseQueries = new DatabaseQueries();
    private CustomLogger logger = new CustomLogger();

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int testGetNumberOfDogsOfRace() {
        return this.getNumberOfDogsOfRace("Dackel");
    }

    public int getNumberOfDogsOfRace(String race) {
        logger.log("getNumberOfDogsOfRace(" + race + ")");
        ArrayList<Dog> dogsOfRace = databaseQueries.getAllDogsOfRace(race);
        int numberOfDogsOfRace = 0;
        for (Dog dog : dogsOfRace) {
            numberOfDogsOfRace++;
        }
        return numberOfDogsOfRace;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public float getAverageNumberOfDogsPerOwner() {
        logger.log("getAverageNumberOfDogsPerOwner()");
        int numberOfOwners = countOwners();
        int numberOfDogs = countDogs();
        float averageNumberOfDogsPerOwner = (float)numberOfDogs / numberOfOwners;
        return averageNumberOfDogsPerOwner;
    }

    private int countOwners() {
        int numberOfOwners = 0;
        ArrayList<String> owners = databaseQueries.getAllOwners();
        for (String owner : owners) {
            numberOfOwners++;
        }
        return numberOfOwners;
    }


    private int countDogs() {
        int numberOfDogs = 0;
        ArrayList<Dog> dogs = databaseQueries.getAllDogs();
        for (Dog dog : dogs) {
            numberOfDogs++;
        }
        return numberOfDogs;
    }

}
