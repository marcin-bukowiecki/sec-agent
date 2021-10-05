package io.sec.dto;

import org.jetbrains.annotations.Nullable;

/**
 * @author Marcin Bukowiecki
 */
public class CustomPattern {

    private String patternName;

    private String pattern;

    public @Nullable String getPatternName() {
        return patternName;
    }

    public void setPatternName(@Nullable String patternName) {
        this.patternName = patternName;
    }

    public @Nullable String getPattern() {
        return pattern;
    }

    public void setPattern(@Nullable String pattern) {
        this.pattern = pattern;
    }
}
