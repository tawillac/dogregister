package hunderegistrierung.legacy.report;

import shared.domain.DogReport;
import hunderegistrierung.legacy.input.JsonFileReader;
import hunderegistrierung.legacy.logging.CustomLogger;
import hunderegistrierung.legacy.output.JsonFileWriter;

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
