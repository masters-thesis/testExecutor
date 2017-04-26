package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tests.*;
import util.Log;
import util.Request;
import util.Result;

import java.util.logging.Logger;

/**
 * Created by KURZRO on 09.03.2017.
 */
@org.springframework.stereotype.Controller
@EnableAutoConfiguration
public class Controller {

    private TestRunner testRunner = new TestRunner();

    @RequestMapping("/testHttpEndpoint")
    @ResponseBody
    Result testHttpEndpointHealthMonitoring( @RequestParam(value="serviceIp", defaultValue="0.0.0.0") String serviceIp ){
        return testRunner.runTest( new TestCaseHttpEndpointHealthMonitoring( new Request(serviceIp) ) );
    }

    @RequestMapping("/testProcessFailure")
    @ResponseBody
    Result testProcessFailure( @RequestParam(value="serviceIp", defaultValue="0.0.0.0") String serviceIp ){
        return testRunner.runTest( new TestCaseProcessHealthMonitoring( new Request(serviceIp) ) );
    }

    @RequestMapping("/testNodeFailure")
    @ResponseBody
    Result testNodeFailure(
            @RequestParam(value="serviceIp", defaultValue="0.0.0.0") String serviceIp,
            @RequestParam(value="nodeIp", defaultValue="0.0.0.0") String nodeIp,
            @RequestParam(value="user", defaultValue="root") String user,
            @RequestParam(value ="command", defaultValue = " echo c > /proc/sysrq-trigger") String command){
        return testRunner.runTest( new TestCaseNodeFailure( new Request(serviceIp), nodeIp, user, command) );
    }

    @RequestMapping("/testLoadBalancing")
    @ResponseBody
    Result testLoadBalancing(@RequestParam(value="serviceIp", defaultValue="0.0.0.0") String serviceIp){
        return testRunner.runTest( new TestCaseLoadBalancing( new Request(serviceIp)) );
    }

    @RequestMapping("/testAutoScaling")
    @ResponseBody
    Result testAutoScaling(@RequestParam(value="serviceIp", defaultValue="0.0.0.0") String serviceIp){
        return testRunner.runTest( new TestCaseAutoScaling( new Request(serviceIp)) );
    }

    @RequestMapping("/testThrowRuntimeException")
    @ResponseBody
    Result testThrowRuntimeException(@RequestParam(value="serviceIp", defaultValue="0.0.0.0") String serviceIp){
        return testRunner.runTest( new TestCaseThrowRuntimeException( new Request(serviceIp)) );
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Controller.class, args);
        }

  }
