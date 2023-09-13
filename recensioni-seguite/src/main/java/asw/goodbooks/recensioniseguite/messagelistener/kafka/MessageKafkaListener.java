package asw.goodbooks.recensioniseguite.messagelistener.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import asw.goodbooks.common.api.event.DomainEvent;
import asw.goodbooks.common.api.event.RecensioneCreatedEvent;
import asw.goodbooks.common.api.event.ConnessioneConAutoreCreatedEvent;
import asw.goodbooks.common.api.event.ConnessioneConRecensoreCreatedEvent;
import asw.goodbooks.recensioniseguite.domain.RecensioniSeguiteService;

import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.logging.Logger;

@Component
public class MessageKafkaListener {

    private final Logger logger = Logger.getLogger(this.getClass().toString());
    
    @Autowired
    private RecensioniSeguiteService consumer;

    @KafkaListener(topics = {"${spring.kafka.channel.in1}", "${spring.kafka.channel.in2}"})
    public void listen(ConsumerRecord<String, DomainEvent> record) throws Exception {
        logger.info("################################# EVENT LISTENER: " + record.toString());
        DomainEvent event = record.value();
        consumer.onEvent(event);
    }
}
