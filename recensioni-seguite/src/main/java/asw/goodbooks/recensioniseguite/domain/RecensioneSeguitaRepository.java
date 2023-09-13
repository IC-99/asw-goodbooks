package asw.goodbooks.recensioniseguite.domain;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface RecensioneSeguitaRepository extends CrudRepository<RecensioneSeguita, Long> {

    public Collection<RecensioneSeguita> findAllByIdUtente(String utente);
    
}
