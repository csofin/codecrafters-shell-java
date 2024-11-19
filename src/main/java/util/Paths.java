package util;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Paths {

    public static boolean exists(Path path, String application) {
        return Files.exists(path.resolve(application));
    }

}
