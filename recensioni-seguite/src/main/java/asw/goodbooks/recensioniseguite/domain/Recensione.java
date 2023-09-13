package asw.goodbooks.recensioniseguite.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*; 

/* Recensione. */ 
@Entity
@Data @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Recensione implements Comparable<Recensione> {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id; 
	private String recensore; 
	private String titoloLibro; 
	private String autoreLibro; 
	private String testoRecensione; 

	@Override
	public int compareTo(Recensione other) {
		return this.id.compareTo(other.id); 
	}
	
}
