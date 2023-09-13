package asw.goodbooks.recensioniseguite.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*; 

@Entity
@Data @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class RecensioneSeguita implements Comparable<RecensioneSeguita> {

    @EqualsAndHashCode.Include
    @EmbeddedId
    private ChiaveCompostaRecensioneSeguita id;
    
	private String recensore; 
	private String titoloLibro; 
	private String autoreLibro; 
	private String testoRecensione; 

	@Override
	public int compareTo(RecensioneSeguita other) {
		return this.id.compareTo(other.id); 
	}
	
}
