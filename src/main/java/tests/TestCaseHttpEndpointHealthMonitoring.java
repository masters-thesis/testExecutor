package tests;

import util.Log;
import util.Request;

import java.time.LocalDateTime;

/**
 * Created by KURZRO on 29.03.2017.
 */
public class TestCaseHttpEndpointHealthMonitoring extends Test {

    public TestCaseHttpEndpointHealthMonitoring(Request request) {
        super(request);
    }

    @Override
    public void run(){
        Log.appendMessage( "Starting Http Endpoint Monitoring test" );
        killHealth();
        waitForContainersToBeAvailable( getNumberOfContainers() );
        setStandardResultMessage();
    }

}
