package hunderegistrierung.legacy.output;

import com.google.gson.Gson;
import org.openjdk.jmh.annotations.*;
import hunderegistrierung.legacy.logging.CustomLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@State(Scope.Benchmark)
public class JsonFileWriter {

    private Gson gson = new Gson();
    private CustomLogger logger = new CustomLogger();

    public void writeObjectToJsonFile(Object output, File file)  {
        logger.log("writeFile(" + file.getName() + ")");
        try {
            Writer writer = new FileWriter(file);
            gson.toJson(output, writer);
            writer.close();
        } catch (IOException ex) {
            logger.warn("writeFile() - IOException: " + ex.getMessage());
        }
    }
}
