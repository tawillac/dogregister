import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import hunderegistrierung.optimized.register.ReferenceDogRegister;
import hunderegistrierung.legacy.register.DogRegister;

import java.util.Locale;

public class Benchmarker {

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        org.apache.log4j.BasicConfigurator.configure();

        Options opt = new OptionsBuilder()
                .include(DogRegister.class.getSimpleName())
                .include(ReferenceDogRegister.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
