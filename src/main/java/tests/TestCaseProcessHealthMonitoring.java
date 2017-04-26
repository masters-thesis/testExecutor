package tests;

import domain.Container;
import util.Log;
import util.Request;

import java.time.LocalDateTime;

/**
 * Created by KURZRO on 29.03.2017.
 */
public class TestCaseProcessHealthMonitoring extends Test{

    public TestCaseProcessHealthMonitoring(Request request) {
        super(request);
    }

    @Override
    public void run(){
        Log.appendMessage( "Starting Process Failure test" );
        killProcess();
        waitForContainersToBeAvailable( getNumberOfContainers() );
        setStandardResultMessage();
    }

}
