package tests;

import org.springframework.beans.factory.annotation.Autowired;
import util.Log;
import util.Result;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by KURZRO on 29.03.2017.
 */
public class TestRunner {

    public Result runTest(Test test){
        Log.resetLog();

        Log.appendMessage("Started test");
        LocalDateTime startTime = LocalDateTime.now();
        test.run();
        LocalDateTime endTime = LocalDateTime.now();
        Log.appendMessage("Ended test");
        Log.print();
        return new Result(startTime, endTime, startTime.until(endTime, ChronoUnit.MILLIS ), test.getResult() );
    }

}
