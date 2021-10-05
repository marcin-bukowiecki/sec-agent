package io.sec;

import net.bytebuddy.agent.ByteBuddyAgent;
import org.junit.jupiter.api.BeforeAll;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author Marcin Bukowiecki
 */
public class AbstractTestCase {

    private static Instrumentation instrumentation;

    protected static SecContext secContext;

    protected static SecClassTransformer secClassTransformer;

    @BeforeAll
    public static void init() {
        ByteBuddyAgent.install();
        instrumentation = ByteBuddyAgent.getInstrumentation();
        secContext = new SecContext.Builder()
                .setBasePackage("sandbox")
                .setNotifyDirOutput( "src/test/resources/output")
                .isBlocking(true)
                .build();
        secClassTransformer = new SecClassTransformer(secContext);
        SecAgent.installAgent(secContext, instrumentation, secClassTransformer);
    }

    public void retransformClass(Class<?> clazz) {
        try {
            instrumentation.retransformClasses(clazz);
        } catch (UnmodifiableClassException e) {
            throw new RuntimeException(e);
        }
    }
}
