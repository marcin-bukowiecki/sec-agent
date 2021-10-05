package io.sec.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sec.SecContext;
import io.sec.checkers.Checker;
import io.sec.dto.DefaultNotifyOutput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default notifier which saves sensitive data to a file. Data is encoded in base64.
 *
 * @author Marcin Bukowiecki
 */
public class DefaultNotifier implements Notifier {

    private static final Logger log = Logger.getLogger(DefaultNotifier.class.getCanonicalName());

    private final ObjectMapper mapper = new ObjectMapper();

    private final SecContext secContext;

    public DefaultNotifier(@NotNull SecContext secContext) {
        this.secContext = secContext;
    }

    @Override
    public void notify(@NotNull Checker checker,
                       @NotNull String sensitiveData,
                       StackTraceElement[] stackTraceElements) {

        final byte[] encoded = Base64.getEncoder().encode((checker.getCheckerName() + ":" + sensitiveData).getBytes());
        final String notifyDirOutput = secContext.getNotifyDirOutput();
        final Path path = Paths.get(notifyDirOutput);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        final String fileName = UUID.randomUUID() + ".json";
        final Path pathToWrite = Path.of(path.toAbsolutePath().toString(), fileName);

        final DefaultNotifyOutput notifyOutput = new DefaultNotifyOutput(checker.getCheckerName(), encoded, stackTraceElements);

        try {
            final byte[] bytes = mapper.writeValueAsBytes(notifyOutput);
            Files.write(pathToWrite, bytes);
        } catch (IOException e) {
            log.log(Level.SEVERE, " Couldn't write data to file notifier");
            throw new RuntimeException(e);
        }
    }
}
