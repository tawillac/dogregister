package performance.happy.output;

import com.google.gson.Gson;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

@State(Scope.Benchmark)
public class ReferenceJsonFileWriter {

    private Gson gson = new Gson();
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public void writeObjectToJsonFile(Object output, File file)  {
        logger.log(Level.INFO, "writeFile(" + file.getName() + ")");
        try {
            Writer writer = new FileWriter(file);
            gson.toJson(output, writer);
            writer.close();
        } catch (IOException ex) {
            logger.log(Level.WARNING, "writeFile() - IOException: " + ex.getMessage());
        }
    }
}
