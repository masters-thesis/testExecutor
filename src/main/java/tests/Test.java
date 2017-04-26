package tests;

import domain.Container;
import repository.ContainerRepository;
import util.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by KURZRO on 29.03.2017.
 */
public class Test {

    private final long TIMEOUT = 300000;
    private Request request;
    private Service responses = new Service();
    private List<Exception> failedConnections = new ArrayList<>();
    private LocalDateTime waitForDesiredStateStart;
    private LocalDateTime waitForDesiredStateEnd;
    private Status resultStatus = Status.WAITING;
    private String resultMessage = "";

    public Test(Request request){
        this.request = request;
        System.out.println( Log.appendMessage( "Probing for available containers" ) );
        callService(100);
        while ( responses.getContainers().isEmpty() ){
            waitForContainersToBeAvailable(1);
        }
    }

    public void run(){
    }

    protected void callService(int numberOfTimes){
        for( int i = 0; i < numberOfTimes; i++){
            try{
                responses.addResponse( request.healthEndpoint() );
            }
            catch ( Exception e ){
                System.out.println( Log.appendMessage("Connection failed") );
                failedConnections.add(e);
            }
        }
    }

    protected void killProcess(){
        try{
            responses.addResponse( request.killProcess() );
        }
        catch ( Exception e ){
            failedConnections.add(e);
        }
    }

    protected void killHealth(){
        try{
            responses.addResponse( request.killHealth() );
        }
        catch ( Exception e ){
            failedConnections.add(e);
        }
    }

    protected void waitForContainersToBeAvailable(long numberOfDesiredContainers){
        waitForDesiredStateStart = LocalDateTime.now();
        responses.reset();
        failedConnections.clear();
        while ( numberOfDesiredContainers != responses.numberOfHealthyContainers() ){
            if( waitForDesiredStateStart.until( LocalDateTime.now(), ChronoUnit.MILLIS ) > TIMEOUT ){
                resultStatus = Status.FAILURE;
                setResultMessage( Log.appendMessage( "Aborting because of timeout of " + TIMEOUT + " milliseconds" ) );
                System.out.println( resultMessage );
                waitForDesiredStateEnd = null;
                break;
            }
            System.out.println( Log.appendMessage(" Waiting for " + numberOfDesiredContainers + " container to be available") );
            callService( 1 );
        }
        waitForDesiredStateEnd = LocalDateTime.now();
        resultStatus = Status.SUCCESS;
    }

    protected void waitForNumbersOfCalls(int numberOfCalls){
        waitForDesiredStateStart = LocalDateTime.now();
        responses.reset();
        failedConnections.clear();
        int counter = 0 ;
        while ( numberOfCalls > counter ){
            if( waitForDesiredStateStart.until( LocalDateTime.now(), ChronoUnit.MILLIS ) > TIMEOUT ){
                resultStatus = Status.FAILURE;
                setResultMessage( Log.appendMessage( "Aborting because of timeout of " + TIMEOUT + " milliseconds" ) );
                System.out.println( resultMessage );
                waitForDesiredStateEnd = null;
                break;
            }
            callService( 1 );

            counter++;
        }
        waitForDesiredStateEnd = LocalDateTime.now();
        resultStatus = Status.SUCCESS;
    }

    protected void generateWorkload(){
        try{
            responses.addResponse( request.generateWorkload() );
        }
        catch ( Exception e ){
            failedConnections.add(e);
        }
    }

    protected void killNode(String ip, String user, String command){

        if( ip.equals("0.0.0.0") ){
            ip = responses.getContainers().get(0).getIp();
        }

        StringBuffer output = new StringBuffer();

        try{
            System.out.println( Log.appendMessage( "Killing node at ip " + ip ));
            Runtime.getRuntime().exec( "ssh -oStrictHostKeyChecking=no -oUserKnownHostsFile=/dev/null -i id_rsa " + user + "@" + ip + command );
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println( Log.appendMessage( "Message from Node kill command : " + output ) );
        System.out.println( Log.appendMessage( "Waiting 5 seconds to give Node time to crash"));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void throwRuntimeException(){
        request.throwRuntimeException();
    }

    public Result getResult(){
        return new Result(
                resultStatus,
                resultMessage,
                null,
                null,
                -1,
                waitForDesiredStateStart,
                waitForDesiredStateEnd,
                getWaitForDesiredStateDuration(),
                getNumberOfContainers(),
                getContainerInformation(),
                getNumberOfSuccessfulRequests(),
                getNumberOfFailedRequests(),
                Log.getLogs()
        );
    }

    protected long getNumberOfContainers(){
        return responses.numberOfContainers() ;
    }

    protected long getNumberOfSuccessfulRequests(){
        return responses.numberOfTotalRequests();
    }

    protected long getNumberOfFailedRequests(){
        return failedConnections.size();
    }

    protected long getWaitForDesiredStateDuration(){
        if( waitForDesiredStateStart == null || waitForDesiredStateEnd == null )
            return 0;
        return waitForDesiredStateStart.until(waitForDesiredStateEnd, ChronoUnit.MILLIS);
    }

    protected long getNumberOfResponsesForContainer(String id){
        return responses.getNumberOfResponsesFromSingleContainer(id);
    }

    protected List<Container> getContainerInformation(){
        return responses.getContainers();
    }

    protected void setResultMessage(String message){
        if( resultMessage.equals("") ){
            this.resultMessage = message;
        }
    }

    protected void setStandardResultMessage(){
        String result = "Found containers: ";
        for( Container container : getContainerInformation() ){
            result += "Id: " + container.getId() + " Ip: " + container.getIp() + " Number of Responses: " + getNumberOfResponsesForContainer( container.getId() );
        }
        result += "Successful number of requests: " + getResult().numberOfSuccessfulRequests + " Number of failed requests: " + getResult().numberOfFailedRequests;
        result += "Test completed in " + getResult().waitForStateDurationInMilliseconds + " milliseconds.";
        setResultMessage(result);
    }

}
