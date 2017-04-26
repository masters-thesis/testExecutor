package domain;

import java.time.LocalDateTime;

/**
 * Created by KURZRO on 07.04.2017.
 */
public class Container {

    private final String id;
    private LocalDateTime lastContact;
    private String ip;
    private long responsesFromContainer;

    public Container(String id, LocalDateTime lastContact, String ip, long responsesFromContainer) {
        this.id = id;
        this.lastContact = lastContact;
        this.ip = ip;
        this.responsesFromContainer = responsesFromContainer;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getLastContact() {
        return lastContact;
    }

    public void setLastContact(LocalDateTime lastContact) {
        this.lastContact = lastContact;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void addResponse(){
        responsesFromContainer ++;
    }

    public long getResponsesFromContainer() {
        return responsesFromContainer;
    }

    public void setResponsesFromContainer(long responsesFromContainer) {
        this.responsesFromContainer = responsesFromContainer;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj == null ){
            return false;
        }
        if ( !Container.class.isAssignableFrom( obj.getClass() ) ){
            return false;
        }
        final Container container = (Container) obj;
        if( !id.equals(container.id) ){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Container{" +
                "id='" + id + '\'' +
                ", lastContact=" + lastContact +
                ", ip='" + ip + '\'' +
                ", responsesFromContainer=" + responsesFromContainer +
                '}';
    }
}
