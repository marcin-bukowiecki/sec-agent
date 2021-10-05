package io.sec;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Marcin Bukowiecki
 */
public class SecContext {

    private final String basePackage;

    private final String basePackageInternal;

    private final String notifyDirOutput;

    private final String customPatternsPath;

    private final boolean blocking;

    public SecContext(@NotNull String basePackage,
                      @NotNull String notifyDirOutput,
                      @Nullable String customPatternsPath,
                      boolean blocking) {
        this.basePackage = basePackage;
        this.basePackageInternal = basePackage.replace('.', '/');
        this.notifyDirOutput = notifyDirOutput;
        this.customPatternsPath = customPatternsPath;
        this.blocking = blocking;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public boolean loadCustomPatterns() {
        return customPatternsPath != null && !customPatternsPath.isEmpty();
    }

    public @Nullable String getCustomPatternsPath() {
        return customPatternsPath;
    }

    public @NotNull String getBasePackage() {
        return basePackage;
    }

    public @NotNull String getBasePackageInternal() {
        return basePackageInternal;
    }

    public @NotNull String getNotifyDirOutput() {
        return notifyDirOutput;
    }

    public static class Builder {

        private String basePackage = "";

        private String notifyDirOutput = "";

        private String customPatternsPath = "";

        private boolean blocking = false;

        public @NotNull Builder setBasePackage(@NotNull String basePackage) {
            this.basePackage = basePackage;
            return this;
        }

        public @NotNull Builder setNotifyDirOutput(@NotNull String notifyDirOutput) {
            this.notifyDirOutput = notifyDirOutput;
            return this;
        }

        public @NotNull Builder setCustomPatternsPath(@NotNull String customPatternsPath) {
            this.customPatternsPath = customPatternsPath;
            return this;
        }

        public @NotNull Builder isBlocking(boolean blocking) {
            this.blocking = blocking;
            return this;
        }

        public @NotNull SecContext build() {
            if (this.basePackage.isEmpty()) {
                throw new IllegalStateException("Expected sec.base.package program argument");
            }
            if (this.notifyDirOutput.isEmpty()) {
                throw new IllegalStateException("Expected sec.base.notify.dir program argument");
            }
            return new SecContext(basePackage, notifyDirOutput, customPatternsPath, blocking);
        }
    }
}
