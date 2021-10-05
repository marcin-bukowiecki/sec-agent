package io.sec.asm;

import io.sec.checkers.SecRuntimeChecker;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Marcin Bukowiecki
 */
public class SecStringTrackingTransformer extends ClassVisitor {

    private final String stringInternalName = Type.getInternalName(String.class);

    private final Type stringType = Type.getType(String.class);

    public SecStringTrackingTransformer(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                if (!stringType.equals(Type.getType(descriptor))) {
                    super.visitFieldInsn(opcode, owner, name, descriptor);
                    return;
                }

                if (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) {
                    super.visitMethodInsn(
                            Opcodes.INVOKESTATIC,
                            SecRuntimeChecker.class.getCanonicalName().replace('.', '/'),
                            "isSensitivePushAsync",
                            "(Ljava/lang/String;)Ljava/lang/String;",
                            false
                    );
                }

                super.visitFieldInsn(opcode, owner, name, descriptor);

                if (opcode == Opcodes.GETFIELD || opcode == Opcodes.GETSTATIC) {
                    super.visitMethodInsn(
                            Opcodes.INVOKESTATIC,
                            SecRuntimeChecker.class.getCanonicalName().replace('.', '/'),
                            "isSensitivePushAsync",
                            "(Ljava/lang/String;)Ljava/lang/String;",
                            false
                    );
                }
            }

            @Override
            public void visitLdcInsn(Object value) {
                super.visitLdcInsn(value);

                if (value instanceof String) {
                    super.visitMethodInsn(
                        Opcodes.INVOKESTATIC,
                        SecRuntimeChecker.class.getCanonicalName().replace('.', '/'),
                        "isSensitive",
                        "(Ljava/lang/String;)Ljava/lang/String;",
                        false
                    );
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

                if ((stringInternalName.equals(owner) && "<init>".equals(name))
                        || stringType.equals(Type.getReturnType(descriptor))) {

                    super.visitMethodInsn(
                            Opcodes.INVOKESTATIC,
                            SecRuntimeChecker.class.getCanonicalName().replace('.', '/'),
                            "isSensitivePushAsync",
                            "(Ljava/lang/String;)Ljava/lang/String;",
                            false
                    );
                }
            }
        };
    }
}
