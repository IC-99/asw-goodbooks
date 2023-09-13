package asw.goodbooks.common.api.event;

import asw.goodbooks.common.api.event.DomainEvent;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnessioneConAutoreCreatedEvent implements DomainEvent {
    private Long id;
    private String utente;
    private String autore;
}
