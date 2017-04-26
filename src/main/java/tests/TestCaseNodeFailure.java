package tests;

import util.Log;
import util.Request;

/**
 * Created by KURZRO on 05.04.2017.
 */
public class TestCaseNodeFailure extends Test {

    private final String nodeIp;
    private final String user;
    private final String command;

    public TestCaseNodeFailure(Request request, String nodeIp, String user, String command) {
        super(request);
        this.nodeIp = nodeIp;
        this.user = user;
        this.command = command;
    }

    @Override
    public void run() {
        Log.appendMessage( "Starting Node Failure test" );
        killNode(nodeIp, user, command);
        waitForContainersToBeAvailable( getNumberOfContainers() );
        setStandardResultMessage();
    }

}
