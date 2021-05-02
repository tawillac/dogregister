package performance.unhappy.report;

import shared.domain.DogReport;
import performance.unhappy.input.JsonFileReader;
import performance.unhappy.logging.CustomLogger;
import performance.unhappy.output.JsonFileWriter;

import java.io.File;

public class ReportCreator {

    private CustomLogger logger = new CustomLogger();
    private JsonFileReader jsonFileReader = new JsonFileReader();
    private JsonFileWriter jsonFileWriter = new JsonFileWriter();

    private File reportFile;

    public ReportCreator(File reportFile) {
        this.reportFile = reportFile;
    }

    public DogReport createReport() {
        logger.log("createReport(" + reportFile.getName() + ")");
        DogReport dogReport = jsonFileReader.openJsonFileAsObject(reportFile, DogReport.class);
        if (dogReport == null) {
            dogReport = new DogReport();
        }
        return dogReport;
    }

    public void writeReport(DogReport dogReport) {
        logger.log("writeReport(" + reportFile.getName() + ")");
        jsonFileWriter.writeObjectToJsonFile(dogReport, reportFile);
    }
}
