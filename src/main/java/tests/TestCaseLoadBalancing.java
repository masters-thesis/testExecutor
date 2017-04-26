package tests;

import domain.Container;
import util.Request;

import java.util.stream.Collectors;

/**
 * Created by KURZRO on 29.03.2017.
 */
public class TestCaseLoadBalancing extends Test {

    public TestCaseLoadBalancing(Request request) {
        super(request);
    }

    @Override
    public void run(){
        waitForNumbersOfCalls(100);
        setStandardResultMessage();
    }

}
