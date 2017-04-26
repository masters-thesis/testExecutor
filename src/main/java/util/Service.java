package util;

import domain.Container;
import repository.ContainerRepository;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Created by KURZRO on 29.03.2017.
 */
public class Service {

    private ContainerRepository containerRepository = new ContainerRepository();

    private Stack<Response> responses = new Stack<>();

    public void addResponse( Response response ){
        responses.add(response);
        containerRepository.addResponseAsContainer(response);
    }

    public Response getLastResponse(){
        return responses.peek();
    }

    public long numberOfContainers(){
        return responses.stream().map(Response::getId).distinct().count();
    }

    public long numberOfHealthyContainers(){
        return responses
                .stream()
                .filter( response -> response.getMessage().equals("healthy") )
                .map( response -> response.getId() )
                .distinct()
                .count();
    }

    public long numberOfTotalRequests(){
        return (long) responses.size();
    }

    public List<Container> getContainers(){
        return containerRepository.getContainers().values().stream().collect(Collectors.toList());
    }

    public long getNumberOfResponsesFromSingleContainer( String id ){
        return responses.stream().filter( response -> response.getId().equals(id) ).count();
    }

    public String getIpAddressOfContainer( String id ){
        return containerRepository.getContainer(id).getIp();
    }

    public void reset(){
        responses = new Stack<>();
        containerRepository.reset();
    }

}
