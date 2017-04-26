package util;

import domain.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
 * Created by KURZRO on 28.03.2017.
 */
public class Request {

    private final RestTemplate request = new RestTemplate();
    private final String url;
    private final String healthEndpoint;
    private final String autoscaleEndpoint;
    private final String killProcessEndpoint;
    private final String timeoutHealthServiceEndpoint;
    private final String killHealthServiceEndpoint;
    private final String runtimeExceptionEndpoint;
    private int callCounter;

    public Request(String url) {
        setUp();

        this.url = url;
        healthEndpoint = "/healthz";
        autoscaleEndpoint = "/generateWorkload";
        killProcessEndpoint = "/killProcess";
        killHealthServiceEndpoint = "/killHealth";
        timeoutHealthServiceEndpoint = "/suspendHealth";
        runtimeExceptionEndpoint = "/throwRuntimeException";
    }

    public Request(RestTemplate request,
                   String url,
                   String healthEndpoint,
                   String loadBalanceEndpoint,
                   String autoscaleEndpoint,
                   String killProcessEndpoint,
                   String killHealthServiceEndpoint,
                   String timeoutHealthServiceEndpoint,
                   String runtimeExceptionEndpoint) {
        setUp();

        this.url = url;
        this.healthEndpoint = healthEndpoint;
        this.autoscaleEndpoint = autoscaleEndpoint;
        this.killProcessEndpoint = killProcessEndpoint;
        this.killHealthServiceEndpoint = killHealthServiceEndpoint;
        this.timeoutHealthServiceEndpoint = timeoutHealthServiceEndpoint;
        this.runtimeExceptionEndpoint = runtimeExceptionEndpoint;
    }

    public Response healthEndpoint(){
        LocalDateTime callStart = LocalDateTime.now();
        Log.appendMessage( "Checking health at " + url + healthEndpoint );
        Response response = request.getForObject(url + healthEndpoint,Response.class);
        LocalDateTime callEnd = LocalDateTime.now();
        Log.appendMessage( "Container " + response.getId() + "at ip + " + response.getIpAddress() + " answered in " +  callStart.until(callEnd, ChronoUnit.MILLIS) + " ms" );
        return response;
    }

    public Response killProcess(){
        incrementCallCounter();
        Log.appendMessage( "Killing process" );
        return request.getForObject(url + killProcessEndpoint,Response.class);
    }

    public Response killHealth(){
        incrementCallCounter();
        System.out.println("Kill health Endpoint.Endpoint");
        return request.getForObject(url + killHealthServiceEndpoint,Response.class);
    }

    public Response suspendHealth(){
        incrementCallCounter();
        System.out.println("Suspend health Endpoint.Endpoint");
        return request.getForObject(url + timeoutHealthServiceEndpoint,Response.class);
    }

    public Response generateWorkload(){
        incrementCallCounter();
        System.out.println("Generating high workload");
        return request.getForObject(url + autoscaleEndpoint,Response.class);
    }

    public Response throwRuntimeException(){
        incrementCallCounter();
        System.out.println("Creating runtime exception");
        return request.getForObject(url + runtimeExceptionEndpoint, Response.class);
    }

    public int getTotalCalls(){
        return callCounter;
    }

    private void incrementCallCounter(){
        callCounter++;
    }

    private void setUp(){
        ((SimpleClientHttpRequestFactory)request.getRequestFactory()).setReadTimeout(1500);
    }
}
