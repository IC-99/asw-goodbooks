package asw.goodbooks.common.api.event;

import asw.goodbooks.common.api.event.DomainEvent;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecensioneCreatedEvent implements DomainEvent {
    
    private Long id;
	private String recensore;
	private String titoloLibro;
	private String autoreLibro;
	private String testoRecensione;

}
