## Synopsis

This program has a number of defined tests to run on a test container with the appropriate API.
The test container used in the thesis tests can be found under https://hub.docker.com/r/rkurz/test-container/.
The source files can be found under https://github.com/robinkurz/TestContainer.
There is also a UI for the Test Executor, found under https://github.com/robinkurz/TestSuite.

## Code Example

Start the executor:
```
java -jar target/TestExecutor-1.0-SNAPSHOT.jar
```

Call a service in command line:
```
curl localhost:8080/testLoadBalancing?serviceIp=http://<ip>
```

Call a service in a browser:
```
http://localhost:8080/testLoadBalancing?serviceIp=http://<ip>
```
**Note**: http:// needs to be the prefix of the serviceIp parameter.

## API

/testLoadBalancing?serviceIp=\<ip\>

/testProcessFailure?serviceIp=\<ip\>

/testHttpFailure?serviceIp=\<ip\>

/testAutoScaling?serviceIp=\<ip\>

/testNodeFailure?serviceIp=\<ip\>&nodeIp=\<nodeToCrash\>&user=\<ssh-user\>&command=\<commandToExecute\>

**NOTE**: If no command to execute is supplied, the command *echo c > /proc/sysrq-trigger* is used
