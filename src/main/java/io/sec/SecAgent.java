package io.sec;

import io.sec.checkers.CardNumberChecker;
import io.sec.checkers.ExpirationDateChecker;
import io.sec.checkers.IBANChecker;
import io.sec.checkers.SecRuntimeChecker;
import io.sec.dto.CustomPattern;
import io.sec.dto.CustomPatterns;
import io.sec.helpers.DefaultNotifier;
import io.sec.utils.PatternLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.List;

/**
 * @author Marcin Bukowiecki
 */
public class SecAgent {

    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws IOException {
        premain(agentArgs, instrumentation);
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        final SecContext secContext = new SecContext.Builder()
                .setBasePackage(System.getProperty("sec.base.package", ""))
                .setNotifyDirOutput(System.getProperty("sec.base.notify.dir", ""))
                .build();
        installAgent(secContext, inst, new SecClassTransformer(secContext));
    }

    public static void installAgent(@NotNull SecContext secContext,
                                    @NotNull Instrumentation inst,
                                    @NotNull SecClassTransformer secClassTransformer) {
        final SecRuntimeChecker runtimeChecker = SecRuntimeChecker.getInstance();
        runtimeChecker.registerNotifier(new DefaultNotifier(secContext));
        runtimeChecker.registerCheckers(
                new IBANChecker(),
                new CardNumberChecker(),
                new ExpirationDateChecker()
        );
        runtimeChecker.init(secContext);
        if (secContext.loadCustomPatterns()) {
            final PatternLoader patternLoader = PatternLoader.getInstance();
            final CustomPatterns customPatterns = patternLoader.loadPatterns(secContext.getCustomPatternsPath());
            if (customPatterns != null) {
                final List<CustomPattern> customPatternList = customPatterns.getCustomPatternList();
                if (customPatternList != null) {
                    runtimeChecker.registerCheckers(patternLoader.createCheckers(customPatternList));
                }
            }
        }
        inst.addTransformer(secClassTransformer);
    }
}
