package tests;

import util.Log;
import util.Request;

/**
 * Created by KURZRO on 29.03.2017.
 */
public class TestCaseAutoScaling extends Test {

    public TestCaseAutoScaling(Request request) {
        super(request);
    }

    @Override
    public void run() {
        Log.appendMessage( "Starting AutoScaling test" );
        generateWorkload();
        waitForContainersToBeAvailable( getNumberOfContainers() + 1 );
        setStandardResultMessage();
    }
}
