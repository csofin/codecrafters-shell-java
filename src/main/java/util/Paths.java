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

    public static Path workingDirectory() {
        return java.nio.file.Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    }

    public static boolean changeDirectory(String directory) {
        Path path = switch (directory) {
            case String d when Regex.ABSOLUTE_PATH.get().matcher(d).find() -> java.nio.file.Paths.get(directory);
            case String d when Regex.RELATIVE_PATH.get().matcher(d).find() -> {
                Path current = workingDirectory();
                int count = Strings.occurrences(directory, "../");
                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        current = current.getParent();
                    }
                }
                String target = Strings.afterLast(directory, "./");
                yield target.isBlank() ? current : current.resolve(target);
            }
            case "~" -> java.nio.file.Paths.get(System.getenv("HOME"));
            case null, default -> workingDirectory();
        };

        if (!Files.exists(path)) {
            return false;
        }

        System.setProperty("user.dir", path.toAbsolutePath().toString());

        return true;
    }

}
