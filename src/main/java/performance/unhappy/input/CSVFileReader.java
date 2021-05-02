package performance.unhappy.input;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import performance.unhappy.logging.CustomLogger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReader {

    private CustomLogger logger = new CustomLogger();

    public List<String[]> readLinesFromCSVFile(File file) {
        logger.log("readLinesFromCSVFile(" + file.getName() + ")");
        List<String[]> inputLines = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(file));
            inputLines = csvReader.readAll();
        } catch (IOException | CsvException e) {
            logger.warn("readInputFromFile(" + file.getName() + ") Exception:" + e.getMessage());
        }
        return inputLines;
    }
}
