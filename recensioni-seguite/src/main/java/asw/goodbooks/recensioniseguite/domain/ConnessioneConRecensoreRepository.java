package asw.goodbooks.recensioniseguite.domain;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;

public interface ConnessioneConRecensoreRepository extends CrudRepository<ConnessioneConRecensore, Long> {
    
    public Collection<ConnessioneConRecensore> findAllByUtente(String utente);

    public Collection<ConnessioneConRecensore> findAllByRecensore(String recensore);

}
