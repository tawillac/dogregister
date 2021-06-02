package hunderegistrierung.optimized.report;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import hunderegistrierung.optimized.output.ReferenceJsonFileWriter;
import shared.domain.DogReport;

import java.io.File;


public class ReferenceReportCreator {

    private Logger logger = Logger.getLogger(this.getClass());
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
