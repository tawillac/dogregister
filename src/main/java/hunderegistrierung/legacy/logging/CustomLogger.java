package hunderegistrierung.legacy.logging;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class CustomLogger {

    public void log(String message) {
        System.out.println("LOG: " + message);
    }

    public void warn(String message) {
        System.err.println("WARN: " + message);
    }
}
