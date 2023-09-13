package asw.goodbooks.recensioniseguite.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.*; 

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class ChiaveCompostaRecensioneSeguita implements Comparable<ChiaveCompostaRecensioneSeguita>, Serializable {

    private String utente;
    private Long idRecensione;

    @Override
    public int compareTo(ChiaveCompostaRecensioneSeguita other) {

        int utenteComparison = this.utente.compareTo(other.utente);
        if (utenteComparison != 0) {
            return utenteComparison;
        }
        return this.idRecensione.compareTo(other.idRecensione);
    }
}
