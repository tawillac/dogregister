
import performance.happy.register.ReferenceDogRegister;
import shared.FileOpener;
import performance.unhappy.register.DogRegister;

import java.io.File;
import java.util.Locale;

public class Main {
    public final static String[] REPORT_PATH = new String[]{"src", "main", "resources", "report.json"};
    public final static String[] REPORT_PATH_UNHAPPY = new String[]{"src", "main", "resources", "report_unhappy.json"};
    public final static String[] INPUT_PATH = new String[]{"src", "main", "resources", "input.csv"};

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        org.apache.log4j.BasicConfigurator.configure();
        performUnhappily();
        performHappily();
    }

    public static void performUnhappily() {
        File inputFile = FileOpener.getFile(INPUT_PATH);
        File reportFile = FileOpener.getFile(REPORT_PATH_UNHAPPY);
        DogRegister dogRegister = new DogRegister(inputFile, reportFile);
        dogRegister.register();
    }

    public static void performHappily() {
        File inputFile = FileOpener.getFile(INPUT_PATH);
        File reportFile = FileOpener.getFile(REPORT_PATH);
        ReferenceDogRegister dogRegister = new ReferenceDogRegister(inputFile, reportFile);
        dogRegister.register();
    }


}
