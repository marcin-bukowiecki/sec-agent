### Sec

Sec is an JVM agent which checks used `java.lang.String` values for sensitive data such as IBAN, Card Number etc. This is done by
instrumenting the bytecode and capturing passed Strings.

Capturing is done for `ldc` instruction, method calls: `invokevirtual`, `invokestatic`, `invokespecial`, `invokeinterface` and field instructions: 
`putstatic`, `getstatic`, `putfield`, `getfield`.

In case of methods the check is done according to the returned String value.

### Requirements

- `Java` (minimum version 11 e.g. `openjdk 11.0.11 2021-04-20 LTS`)

### Building

To build the project just execute following command:

```
mvn package
```

This will produce a jar in `target` directory (sec-agent-1.0.jar) which is just a typical JVM agent.

To test the agent you will need to build the `sandbox` project. The `sandbox` project is a simple Spring Boot application.

From the `sandbox` root directory execute following command: `mvn clean install`.

After that `cd` to the `target` directory, copy the `sec-agent-1.0.jar` from previous build and execute following command:

```
java -jar -javaagent:sec-agent-1.0.jar -Dsec.base.package=sandbox.controller -Dsec.base.notify.dir=output sec-sandbox-1.0.0.jar 
```

This command will run the Spring Boot application with `Sec Agent`.

Passed arguments are responsible for:

- `sec.base.package` - base package of classes to be instrumented by `Sec Agent`
- `sec.base.notify.dir` - output folder of used sensitive data

Sandbox provides single `HTTP GET /` endpoint at 8080 port.

### Testing

To test the agent just invoke the `HTTP GET /` endpoint e.g. `localhsot:8080`. Endpoint will return and IBAN number 
`PL75109024026978617931837585` but in the Spring Boot application logs you should 
see the following entry: ` io.sec.checkers.IBANChecker              : Got sensitive data type: IBAN` 
and also in the `output` folder you will see json files which contains stack frames where the sensitive data was localized.

