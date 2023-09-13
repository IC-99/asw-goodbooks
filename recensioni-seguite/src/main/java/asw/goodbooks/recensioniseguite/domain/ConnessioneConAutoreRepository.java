package asw.goodbooks.recensioniseguite.domain;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;

public interface ConnessioneConAutoreRepository extends CrudRepository<ConnessioneConAutore, Long> {
    
    public Collection<ConnessioneConAutore> findAllByUtente(String utente);

    public Collection<ConnessioneConAutore> findAllByAutore(String autore);

}
