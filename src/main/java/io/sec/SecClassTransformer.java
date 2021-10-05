package io.sec;

import io.sec.asm.SecClassWriter;
import io.sec.asm.SecStringTrackingTransformer;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marcin Bukowiecki
 */
public class SecClassTransformer implements ClassFileTransformer {

    private static final Logger log = Logger.getLogger(SecClassTransformer.class.getName());

    private final SecContext secContext;

    public SecClassTransformer(@NotNull SecContext secContext) {
        this.secContext = secContext;
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        if (className == null) {
            return classfileBuffer;
        }

        if (className.startsWith("io/sec/")) {
            return classfileBuffer;
        }

        if (!className.startsWith(secContext.getBasePackageInternal())) {
            return classfileBuffer;
        }

        var cr = new ClassReader(classfileBuffer);
        var cw = new SecClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        var cv = new SecStringTrackingTransformer(Opcodes.ASM7, cw);

        try {
            cr.accept(cv, 0);
            return cw.toByteArray();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error while transforming class: " + className, e);
            return classfileBuffer;
        }
    }
}
