package asw.goodbooks.connessioni.messagepublisher.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import asw.goodbooks.common.api.event.DomainEvent;
import asw.goodbooks.connessioni.domain.MessagePublisherPort;

@Component
public class MessageKafkaPublisher implements MessagePublisherPort{
    
    @Value("${spring.kafka.channel.out}")
    private String channel;

    @Autowired
    private KafkaTemplate<String, DomainEvent> template;

    @Override
    public void publish(DomainEvent message){
        template.send(channel, message);
    }
}
