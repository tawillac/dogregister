
import hunderegistrierung.optimized.register.ReferenceDogRegister;
import shared.DatabasePreparer;
import shared.FileOpener;
import hunderegistrierung.legacy.register.DogRegister;

import java.io.File;
import java.util.Date;
import java.util.Locale;

public class Main {
    public final static String[] REPORT_PATH = new String[]{"src", "main", "resources", "report.json"};
    public final static String[] REPORT_PATH_UNHAPPY = new String[]{"src", "main", "resources", "report_unhappy.json"};
    public final static String[] INPUT_PATH = new String[]{"src", "main", "resources", "input_small.csv"};

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        org.apache.log4j.BasicConfigurator.configure();

        prepareDatabase();

        Date startDate1 = new Date();
        performUnhappily();
        Date stopDate1 = new Date();
        Date startDate2 = new Date();
        performHappily();
        Date stopDate2 = new Date();

        System.err.println("BAD => " + startDate1 + " | " + stopDate1);
        System.err.println("GOOD => " + startDate2 + " | " + stopDate2);
    }

    public static void performUnhappily() {
        Date startDate = new Date();
        System.out.println("performUnhappily() START (" + new Date() + ")");
        File inputFile = FileOpener.getFile(INPUT_PATH);
        File reportFile = FileOpener.getFile(REPORT_PATH_UNHAPPY);
        DogRegister dogRegister = new DogRegister(inputFile, reportFile);
        dogRegister.register();
        System.out.println("performUnhappily() START (" + startDate + ") -> STOP (" + new Date() + ")");
    }

    public static void performHappily() {
        Date startDate = new Date();
        System.out.println("performHappily() START (" + new Date() + ")");
        File inputFile = FileOpener.getFile(INPUT_PATH);
        File reportFile = FileOpener.getFile(REPORT_PATH);
        ReferenceDogRegister dogRegister = new ReferenceDogRegister(inputFile, reportFile);
        dogRegister.register();
        System.out.println("performHappily() START (" + startDate + ") -> STOP (" + new Date() + ")");
    }

    public static void prepareDatabase() {
        DatabasePreparer.setUpDatabase();
    }


}
