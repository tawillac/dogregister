package performance.happy.register;

import org.openjdk.jmh.annotations.*;
import performance.happy.input.ReferenceDogReader;
import performance.happy.processing.ReferenceEvaluator;
import performance.happy.processing.ReferencePersistor;
import performance.happy.report.ReferenceReportCreator;
import shared.FileOpener;
import shared.domain.Dog;
import shared.domain.DogReport;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 3)
@Measurement(iterations = 10)
public class ReferenceDogRegister {

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private File inputFile;
    private File reportFile;

    public ReferenceDogRegister() {
        String[] test_reportfile_path = new String[]{"src", "main", "resources", "report.json"};
        String[] test_inputfile_path = new String[]{"src", "main", "resources", "input.csv"};
        this.inputFile = FileOpener.getFile(test_inputfile_path);
        this.reportFile = FileOpener.getFile(test_reportfile_path);
    }

    public ReferenceDogRegister(File inputFile, File reportFile) {
        this.inputFile = inputFile;
        this.reportFile = reportFile;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void register() {
        if (!inputFile.exists() || !inputFile.isFile()) {
            logger.log(Level.WARNING, "register() - InputFile is invalid!");
            return;
        }
        List<Dog> dogs = createListOfDogs(inputFile);
        processDogs(dogs);
    }

    private void processDogs(List<Dog> dogs) {
        logger.log(Level.INFO, "processDogs()");

        ReferenceReportCreator reportCreator = new ReferenceReportCreator(reportFile);
        DogReport dogReport = new DogReport();

        persistDogs(dogs);
        evaluateDogs(dogReport);
        reportCreator.writeReport(dogReport);
    }

    private List<Dog> createListOfDogs(File inputFile) {
        logger.log(Level.INFO, "createListOfDogs(" + inputFile.getName() + ")");

        ReferenceDogReader dogReader = new ReferenceDogReader();
        List<Dog> dogs = dogReader.readDogsFromFile(inputFile);
        return dogs;
    }

    private void evaluateDogs(DogReport dogReport) {
        logger.log(Level.INFO, "evaluateDogs()");

        ReferenceEvaluator evaluator = new ReferenceEvaluator();
        dogReport.setRaceCount(evaluator.getNumberOfDogsPerRace());
        dogReport.setAverageNumberOfDogsPerOwner(evaluator.getAverageNumberOfDogsPerOwner());
    }

    private void persistDogs(List<Dog> dogs) {
        logger.log(Level.INFO, "persistDogs()");

        ReferencePersistor persistor = new ReferencePersistor();
        persistor.persistDogs(dogs);
    }
}
