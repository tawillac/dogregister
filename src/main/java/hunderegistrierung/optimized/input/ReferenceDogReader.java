package hunderegistrierung.optimized.input;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import shared.domain.Dog;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReferenceDogReader {

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private ReferenceCSVFileReader csvFileReader = new ReferenceCSVFileReader();

    public List<Dog> readDogsFromFile(File file) {
        logger.log(Level.INFO, "readDogsFromFile(" + file.getName() + ")");
        List<String[]> csvFileContent = csvFileReader.readLinesFromCSVFile(file);
        List<Dog> results = csvFileContent.stream().map(line -> mapLineToDog(line)).collect(Collectors.toList());
        return results;
    }

    private Dog mapLineToDog(String[] inputLine) {
        logger.log(Level.INFO, "mapLineToDog(" + inputLine[0] + ")");
        Dog dog = Dog.builder()
                .name((inputLine[0]))
                .race(inputLine[1])
                .owner(inputLine[2])
                .birthday(LocalDate.parse(inputLine[3]))
                .build();
        return dog;
    }

}
