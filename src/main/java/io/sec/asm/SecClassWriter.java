package io.sec.asm;

import org.objectweb.asm.ClassWriter;

/**
 * @author Marcin Bukowiecki
 */
public class SecClassWriter extends ClassWriter {

    public SecClassWriter(int flags) {
        super(flags);
    }
}
