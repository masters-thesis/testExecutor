package tests;

import util.Log;
import util.Request;

/**
 * Created by KURZRO on 29.03.2017.
 */
public class TestCaseThrowRuntimeException extends Test {

    public TestCaseThrowRuntimeException(Request request) {
        super(request);
    }

    @Override
    public void run() {
        Log.appendMessage( "Starting Throw Runtime Exception test" );
        throwRuntimeException();
        waitForContainersToBeAvailable( getNumberOfContainers() );
        setStandardResultMessage();
    }
}
