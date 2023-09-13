package asw.goodbooks.connessioni.domain;

import asw.goodbooks.common.api.event.DomainEvent;

public interface MessagePublisherPort {

    public void publish(DomainEvent message);
    
}