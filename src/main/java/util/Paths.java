package util;

import config.Environment;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public final class Paths {

    public static Optional<Path> resolveApplication(String application) {
        return Environment.getInstance().getPaths()
                .stream()
                .map(p -> p.resolve(application))
                .filter(Files::exists)
                .findFirst();
    }

    public static Path pwd() {
        return java.nio.file.Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    }

    public static boolean cd(String directory) {
        Path path = java.nio.file.Paths.get(directory);
        if (!Files.exists(path)) {
            return false;
        }
        System.setProperty("user.dir", path.toAbsolutePath().toString());
        return true;
    }

}
