package util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import domain.Container;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by KURZRO on 29.03.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    public final Status status;
    public final String message;
    public final LocalDateTime testStartTime;
    public final LocalDateTime testEndTime;
    public final long testDurationInMilliseconds;
    public final LocalDateTime waitForDesiredStateStart;
    public final LocalDateTime waitForDesiredStateEnd;
    public final long waitForStateDurationInMilliseconds;
    public final long numberOfContainers;
    public final List<Container> containerInformation;
    public final long numberOfSuccessfulRequests;
    public final long numberOfFailedRequests;
    public final List<String> logs;

    private final String newline = System.getProperty("line.separator");

    public Result(
            Status status,
            String message,
            LocalDateTime startTime,
            LocalDateTime endTime,
            long testDurationInMilliseconds,
            LocalDateTime waitForDesiredStateStart,
            LocalDateTime waitForDesiredStateEnd,
            long waitForStateDurationInMilliseconds,
            long numberOfContainers,
            List<Container> containerInformation,
            long numberOfTotalRequests,
            long numberOfFailedRequests,
            List<String> logs) {
        this.status = status;
        this.message = message;
        this.testStartTime = startTime;
        this.testEndTime = endTime;
        this.testDurationInMilliseconds = testDurationInMilliseconds;
        this.waitForDesiredStateStart = waitForDesiredStateStart;
        this.waitForDesiredStateEnd = waitForDesiredStateEnd;
        this.waitForStateDurationInMilliseconds = waitForStateDurationInMilliseconds;
        this.numberOfContainers = numberOfContainers;
        this.containerInformation = containerInformation;
        this.numberOfSuccessfulRequests = numberOfTotalRequests;
        this.numberOfFailedRequests = numberOfFailedRequests;
        this.logs = logs;
    }

    public Result(LocalDateTime startTime, LocalDateTime endTime, long duration, Result previousResult) {
        this(
                previousResult.status,
                previousResult.message,
                startTime,
                endTime,
                duration,
                previousResult.waitForDesiredStateStart,
                previousResult.waitForDesiredStateEnd,
                previousResult.waitForStateDurationInMilliseconds,
                previousResult.numberOfContainers,
                previousResult.containerInformation,
                previousResult.numberOfSuccessfulRequests,
                previousResult.numberOfFailedRequests,
                previousResult.logs
        );
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", testStartTime=" + testStartTime +
                ", testEndTime=" + testEndTime +
                ", testDurationInMilliseconds=" + testDurationInMilliseconds +
                ", waitForDesiredStateStart=" + waitForDesiredStateStart +
                ", waitForDesiredStateEnd=" + waitForDesiredStateEnd +
                ", waitForStateDurationInMilliseconds=" + waitForStateDurationInMilliseconds +
                ", numberOfContainers=" + numberOfContainers +
                ", containerInformation='" + containerInformation + '\'' +
                ", numberOfSuccessfulRequests=" + numberOfSuccessfulRequests +
                ", numberOfFailedRequests=" + numberOfFailedRequests +
                ", logs=" + logs +
                '}';
    }
}
