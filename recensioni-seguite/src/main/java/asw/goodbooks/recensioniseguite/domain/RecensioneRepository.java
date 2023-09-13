package asw.goodbooks.recensioniseguite.domain;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {
    
    public Collection<Recensione> findAllByAutoreLibro(String autoreLibro);

    public Collection<Recensione> findAllByRecensore(String recensore);

}
