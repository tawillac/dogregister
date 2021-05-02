package performance.happy.report;

import performance.happy.output.ReferenceJsonFileWriter;
import shared.domain.DogReport;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReferenceReportCreator {

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private ReferenceJsonFileWriter jsonFileWriter = new ReferenceJsonFileWriter();

    private File reportFile;

    public ReferenceReportCreator(File reportFile) {
        this.reportFile = reportFile;
    }

    public void writeReport(DogReport dogReport) {
        logger.log(Level.INFO, "writeReport(" + reportFile.getName() + ")");
        jsonFileWriter.writeObjectToJsonFile(dogReport, reportFile);
    }
}
