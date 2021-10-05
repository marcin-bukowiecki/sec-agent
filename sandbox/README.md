### Startup

Run the `sec-sandbox-1.0.0.jar` with the following command:

```
java -jar -javaagent:sec-agent-1.0.jar -Dsec.base.package=sandbox.controller -Dsec.base.notify.dir=output sec-sandbox-1.0.0.jar 
```

Properties:

- `sec.base.package` - base package of classes to be transformed by agent
- `sec.base.notify.dir` - output folder of used sensitive data

Sandbox provides single `HTTP GET /` endpoint at 8080 port.
