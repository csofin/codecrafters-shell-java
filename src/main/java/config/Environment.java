package config;

import util.Regex;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Environment {

    private static final class LazyHolder {
        private static final Environment INSTANCE = new Environment();
    }

    private List<Path> paths;

    private Environment() {
    }

    public static Environment getInstance() {
        return LazyHolder.INSTANCE;
    }

    public List<Path> getPaths() {
        return Objects.isNull(paths) ? List.of() : paths;
    }

    public void loadEnvironment(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            return;
        }
        Pattern pattern = Regex.PATH.get();
        Matcher matcher = pattern.matcher(value.strip());
        if (matcher.find()) {
            this.paths = Arrays.stream(matcher.group(1).split(":"))
                    .filter(Predicate.not(String::isBlank))
                    .map(Path::of).toList();
        }
    }

}
