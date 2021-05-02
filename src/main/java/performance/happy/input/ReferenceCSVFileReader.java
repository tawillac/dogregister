package performance.happy.input;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReferenceCSVFileReader {

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public List<String[]> readLinesFromCSVFile(File file) {
        logger.log(Level.INFO, "readLinesFromCSVFile(" + file.getName() + ")");
        List<String[]> inputLines = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(file));
            inputLines = csvReader.readAll();
        } catch (IOException | CsvException e) {
            logger.log(Level.WARNING, "readInputFromFile(" + file.getName() + ") Exception:" + e.getMessage());
        }
        return inputLines;
    }
}
