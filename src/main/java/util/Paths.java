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

}
