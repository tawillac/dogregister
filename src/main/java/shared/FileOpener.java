package shared;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileOpener {

    public static File getFile(String... path) {
        Path resourceDirectory = Paths.get(path[0], Arrays.copyOfRange(path, 1, path.length));
        File file = resourceDirectory.toFile();
        return file;
    }

}
