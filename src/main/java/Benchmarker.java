import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import performance.happy.register.ReferenceDogRegister;
import performance.unhappy.register.DogRegister;

import java.util.Locale;

public class Benchmarker {

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.ENGLISH);

        Options opt = new OptionsBuilder()
                .include(DogRegister.class.getSimpleName())
                .include(ReferenceDogRegister.class.getSimpleName())
                //.include(Evaluator.class.getSimpleName())
                //.include(ReferenceEvaluator.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
