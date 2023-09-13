package asw.goodbooks.recensioniseguite.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*; 

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
public class ConnessioneConRecensore {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id; 
	private String utente; 
	private String recensore; 
	
}
