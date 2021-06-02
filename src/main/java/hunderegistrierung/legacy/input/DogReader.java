package hunderegistrierung.legacy.input;

import shared.domain.Dog;
import hunderegistrierung.legacy.logging.CustomLogger;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DogReader {

    private CustomLogger logger = new CustomLogger();
    private CSVFileReader csvFileReader = new CSVFileReader();

    public List<Dog> readDogsFromFile(File file) {
        logger.log("readDogsFromFile(" + file.getName() + ")");
        List<String[]> csvFileContent = csvFileReader.readLinesFromCSVFile(file);
        List<Dog> results = new ArrayList<>();
        for (String[] line : csvFileContent) {
            Dog result = mapLineToDog(line);
            results.add(result);
        }
        return results;
    }

    private Dog mapLineToDog(String[] inputLine) {
        logger.log("mapLineToInputItem(" + inputLine[0] + ")");
        Dog dog = Dog.builder()
                .name((inputLine[0]))
                .race(inputLine[1])
                .owner(inputLine[2])
                .birthday(LocalDate.parse(inputLine[3]))
                .build();
        return dog;
    }

}
