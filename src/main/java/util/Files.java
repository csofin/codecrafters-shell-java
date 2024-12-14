package util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

public final class Files {

    public static Optional<String> contents(String file) {
        try {
            String content = java.nio.file.Files.readString(Paths.get(file));
            return Optional.of(content);
        } catch (IOException ioe) {
            return Optional.empty();
        }
    }

}
