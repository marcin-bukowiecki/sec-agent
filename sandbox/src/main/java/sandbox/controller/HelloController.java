package sandbox.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author Marcin Bukowiecki
 */
@RestController
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @GetMapping
    public String hello() {
        final String sensitiveData = getSensitiveData();
        log.info( "Got sensitive data: {}", sensitiveData);
        return sensitiveData;
    }

    private String getSensitiveData() {
        return "PL75109024026978617931837585";
    }
}
