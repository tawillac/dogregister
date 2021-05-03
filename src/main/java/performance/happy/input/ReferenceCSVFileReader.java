package performance.happy.input;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReferenceCSVFileReader {

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public List<String[]> readLinesFromCSVFile(File file) {
        logger.log(Level.INFO, "readLinesFromCSVFile(" + file.getName() + ")");
        List<String[]> inputLines = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(file));
            inputLines = csvReader.readAll();
        } catch (IOException | CsvException e) {
            logger.log(Level.WARN, "readInputFromFile(" + file.getName() + ") Exception:" + e.getMessage());
        }
        return inputLines;
    }
}
