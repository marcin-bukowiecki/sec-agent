package io.sec.dto;

import org.jetbrains.annotations.NotNull;

/**
 * @author Marcin Bukowiecki
 */
public class DefaultNotifyOutput {

    private String checkerName;

    private byte[] encoded;

    private StackTraceElement[] stackFrame;

    public DefaultNotifyOutput() {
    }

    public DefaultNotifyOutput(@NotNull String checkerName, byte[] encoded, @NotNull StackTraceElement[] stackFrame) {
        this.checkerName = checkerName;
        this.encoded = encoded;
        this.stackFrame = stackFrame;
    }

    public void setCheckerName(@NotNull String checkerName) {
        this.checkerName = checkerName;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public byte[] getEncoded() {
        return encoded;
    }

    public void setEncoded(byte[] encoded) {
        this.encoded = encoded;
    }

    public @NotNull StackTraceElement[] getStackFrame() {
        return stackFrame;
    }

    public void setStackFrame(@NotNull StackTraceElement[] stackFrame) {
        this.stackFrame = stackFrame;
    }
}
