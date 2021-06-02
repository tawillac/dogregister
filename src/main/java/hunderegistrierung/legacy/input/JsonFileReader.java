package hunderegistrierung.legacy.input;

import com.google.gson.Gson;
import hunderegistrierung.legacy.logging.CustomLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileReader {

    private CustomLogger logger = new CustomLogger();
    private Gson gson = new Gson();

    public <K> K openJsonFileAsObject(File reportFile, Class<K> objectType) {
        logger.log("openJsonFileAsObject(" + reportFile.getName() +  "/" + objectType + ")");
        K output = null;
        String fileContent = getFileContentAsString(reportFile);
        if (fileContent != null && !fileContent.isEmpty()) {
            output = gson.fromJson(fileContent, objectType);
        }
        return output;
    }

    private String getFileContentAsString(File file) {
        logger.log("getFileContentAsString(" + file.getName() + ")");

        String content = null;
        if (file != null && file.isFile()) {
            try {
                content = new String(Files.readAllBytes(Paths.get(String.valueOf(file.toPath()))));

            } catch (IOException e) {
                logger.warn("openReport() Exception: " +e.getMessage());
            }
        }
        return content;
    }
}
