package io.sec.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sec.checkers.Checker;
import io.sec.checkers.CustomChecker;
import io.sec.dto.CustomPattern;
import io.sec.dto.CustomPatterns;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marcin Bukowiecki
 */
public enum PatternLoader {

    instance;

    private static final Logger log = Logger.getLogger(PatternLoader.class.getCanonicalName());

    public @Nullable CustomPatterns loadPatterns(@Nullable String path) {
        if (path == null || path.isEmpty()) {
            log.log(Level.SEVERE, "Custom patterns path is empty");
            return null;
        }

        log.log(Level.INFO, "Loading custom patterns from: " + path);

        try {
            final String content = Files.readString(Paths.get(path));
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(content, CustomPatterns.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull List<Checker> createCheckers(@NotNull List<CustomPattern> customPatternList) {
        final List<Checker> checkers = new ArrayList<>();
        for (CustomPattern customPattern : customPatternList) {
            final String patternName = customPattern.getPatternName();
            final String pattern = customPattern.getPattern();
            if (patternName == null || patternName.isEmpty()) {
                log.log(Level.SEVERE, "patternName is empty");
                continue;
            }
            if (pattern == null || pattern.isEmpty()) {
                log.log(Level.SEVERE, "pattern is empty");
                continue;
            }
            checkers.add(new CustomChecker(patternName, pattern));
        }
        return checkers;
    }

    public static PatternLoader getInstance() {
        return instance;
    }
}
