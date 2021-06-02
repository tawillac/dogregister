package hunderegistrierung.optimized.processing;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.openjdk.jmh.annotations.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 3)
@Measurement(iterations = 10)
public class ReferenceEvaluator {

    private ReferenceDatabaseQueries databaseQueries = new ReferenceDatabaseQueries();
    private Logger logger = Logger.getLogger(this.getClass());

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public Map<String, Integer> getNumberOfDogsPerRace() {
        logger.log(Level.INFO,"getNumberOfDogsPerRace()");
        return databaseQueries.getNumberOfDogsPerRace();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public float getAverageNumberOfDogsPerOwner() {
        logger.log(Level.INFO, "getAverageNumberOfDogsPerOwner()");
        return databaseQueries.getAverageNumberOfDogsPerOwner();
    }



}
