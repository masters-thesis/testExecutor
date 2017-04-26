package repository;

import domain.Container;
import util.Response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KURZRO on 07.04.2017.
 */
public class ContainerRepository {

    Map<String,Container> containers = new HashMap<>();

    public void addResponseAsContainer(Response response){

        if( containers.containsKey(response.getId()) ){
            Container update = containers.get( response.getId() );
            update.setIp( response.getIpAddress() );
            update.setLastContact( LocalDateTime.now() );
            update.addResponse();
        }
        else
        {
            containers.put(
                    response.getId(),
                    new Container(
                            response.getId(),
                            LocalDateTime.now(),
                            response.getIpAddress(),
                            1
                    )
            );
        }
    }

    public void addContainer( Container container ){
        containers.put( container.getId(), container );
    }

    public Map<String, Container> getContainers(){
        return containers;
    }

    public Container getContainer( String id ){
        return containers.get(id);
    }

    public void reset(){
        containers.clear();
    }

}
