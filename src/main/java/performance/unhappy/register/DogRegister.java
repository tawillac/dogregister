package performance.unhappy.register;

import shared.domain.Dog;
import shared.domain.DogReport;
import shared.FileOpener;
import org.openjdk.jmh.annotations.*;
import performance.unhappy.processing.Evaluator;
import performance.unhappy.input.DogReader;
import performance.unhappy.logging.CustomLogger;
import performance.unhappy.processing.Persistor;
import performance.unhappy.report.ReportCreator;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 3)
@Measurement(iterations = 10)
public class DogRegister {

    private CustomLogger logger;
    private File inputFile;
    private File reportFile;

    public DogRegister() {
        String[] test_reportfile_path = new String[]{"src", "main", "resources", "report_unhappy.json"};
        String[] test_inputfile_path = new String[]{"src", "main", "resources", "input_small.csv"};
        this.logger = new CustomLogger();
        this.inputFile = FileOpener.getFile(test_inputfile_path);
        this.reportFile = FileOpener.getFile(test_reportfile_path);
    }

    public DogRegister(File inputFile, File reportFile) {
        this.logger = new CustomLogger();
        this.inputFile = inputFile;
        this.reportFile = reportFile;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void register() {
        if (!inputFile.exists() || !inputFile.isFile()) {
            logger.warn("register() - InputFile is invalid!");
            return;
        }
        logger.log("register() - processDogs()");

        List<Dog> dogs = createListOfDogs(inputFile);

        for (Dog dog : dogs) {
            processDog(dog);
        }
    }

    private void processDog(Dog dog) {
        logger.log("processDog(" + dog + ")");

        ReportCreator reportCreator = new ReportCreator(reportFile);
        DogReport dogReport = reportCreator.createReport();
        persistDog(dog);
        evaluateDog(dog, dogReport);
        reportCreator.writeReport(dogReport);
    }

    private List<Dog> createListOfDogs(File inputFile) {
        logger.log("createListOfDogs(" + inputFile.getName() + ")");

        DogReader dogReader = new DogReader();
        List<Dog> dogs = dogReader.readDogsFromFile(inputFile);
        return dogs;
    }

    private void evaluateDog(Dog dog, DogReport dogReport) {
        logger.log("evaluateDog(" + dog.getName() + ")");

        Evaluator evaluator = new Evaluator();
        dogReport.addRaceCount(dog.getRace(), evaluator.getNumberOfDogsOfRace(dog.getRace()));
        dogReport.setAverageNumberOfDogsPerOwner(evaluator.getAverageNumberOfDogsPerOwner());
    }

    private void persistDog(Dog dog) {
        logger.log("persistDog(" + dog.getName() + ")");

        Persistor persistor = new Persistor();
        persistor.persistDog(dog);
    }
}
