package asw.goodbooks.recensioniseguite.domain;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import asw.goodbooks.common.api.event.DomainEvent;
import asw.goodbooks.common.api.event.RecensioneCreatedEvent;
import asw.goodbooks.common.api.event.ConnessioneConAutoreCreatedEvent;
import asw.goodbooks.common.api.event.ConnessioneConRecensoreCreatedEvent;

import java.util.*; 
import java.util.stream.*; 

@Service 
public class RecensioniSeguiteService {

	@Autowired
    private RecensioneRepository recensioneRepository;

    @Autowired
    private ConnessioneConAutoreRepository connessioneConAutoreRepository;

    @Autowired
    private ConnessioneConRecensoreRepository connessioneConRecensoreRepository;

    @Autowired
    private RecensioneSeguitaRepository recensioneSeguitaRepository ;

    public void onEvent(DomainEvent event) {
        if (event.getClass().equals(RecensioneCreatedEvent.class)) {
            RecensioneCreatedEvent recensioneCreatedEvent = (RecensioneCreatedEvent) event;
            this.onRecensioneCreated(recensioneCreatedEvent);
        } else if (event.getClass().equals(ConnessioneConAutoreCreatedEvent.class)) {
            ConnessioneConAutoreCreatedEvent connessioneConAutoreCreatedEvent = (ConnessioneConAutoreCreatedEvent) event;
            this.onConnessioneConAutoreCreated(connessioneConAutoreCreatedEvent);
        } else if (event.getClass().equals(ConnessioneConRecensoreCreatedEvent.class)) {
            ConnessioneConRecensoreCreatedEvent connessioneConRecensoreCreatedEvent = (ConnessioneConRecensoreCreatedEvent) event;
            this.onConnessioneConRecensoreCreated(connessioneConRecensoreCreatedEvent);
        }
    }

    private void onRecensioneCreated(RecensioneCreatedEvent event) {
        recensioneRepository.save(new Recensione(event.getId(), event.getRecensore(), event.getTitoloLibro(), event.getAutoreLibro(), event.getTestoRecensione()));

        Collection<RecensioneSeguita> recensioniSeguite = new TreeSet<>();
        Collection<ConnessioneConAutore> connessioniConAutore = connessioneConAutoreRepository.findAllByAutore(event.getAutoreLibro());
        Collection<ConnessioneConRecensore> connessioniConRecensore = connessioneConRecensoreRepository.findAllByRecensore(event.getRecensore());

        Collection<String> utenti = new TreeSet<>();

        for (ConnessioneConAutore connessioneConAutore : connessioniConAutore) {
            utenti.add(connessioneConAutore.getUtente());
        }
        for (ConnessioneConRecensore connessioneConRecensore: connessioniConRecensore) {
            utenti.add(connessioneConRecensore.getUtente());
        }

        for (String utente : utenti) {
            ChiaveCompostaRecensioneSeguita chiaveCompostaRecensioneSeguita = new ChiaveCompostaRecensioneSeguita(utente, event.getId());
            RecensioneSeguita recensioneSeguita = new RecensioneSeguita(chiaveCompostaRecensioneSeguita, event.getRecensore(), event.getTitoloLibro(), event.getAutoreLibro(), event.getTestoRecensione());
            recensioniSeguite.add(recensioneSeguita);
        }

        recensioneSeguitaRepository.saveAll(recensioniSeguite);
    }
    
    private void onConnessioneConAutoreCreated(ConnessioneConAutoreCreatedEvent event) {
        connessioneConAutoreRepository.save(new ConnessioneConAutore(event.getId(), event.getUtente(), event.getAutore()));

        Collection<RecensioneSeguita> recensioniSeguite = new TreeSet<>();
        Collection<Recensione> recensioniDaAutoreLibro = recensioneRepository.findAllByAutoreLibro(event.getAutore());

        for (Recensione recensioneDaAutoreLibro : recensioniDaAutoreLibro) {
            ChiaveCompostaRecensioneSeguita chiaveCompostaRecensioneSeguita = new ChiaveCompostaRecensioneSeguita(event.getUtente(), recensioneDaAutoreLibro.getId());
            RecensioneSeguita recensioneSeguita = new RecensioneSeguita(chiaveCompostaRecensioneSeguita, recensioneDaAutoreLibro.getRecensore(), recensioneDaAutoreLibro.getTitoloLibro(), recensioneDaAutoreLibro.getAutoreLibro(), recensioneDaAutoreLibro.getTestoRecensione());
            recensioniSeguite.add(recensioneSeguita);
        }

        recensioneSeguitaRepository.saveAll(recensioniSeguite);
    }

    private void onConnessioneConRecensoreCreated(ConnessioneConRecensoreCreatedEvent event) {
        connessioneConRecensoreRepository.save(new ConnessioneConRecensore(event.getId(), event.getUtente(), event.getRecensore()));

        Collection<RecensioneSeguita> recensioniSeguite = new TreeSet<>();
        Collection<Recensione> recensioniDaRecensore = recensioneRepository.findAllByRecensore(event.getRecensore());

        for (Recensione recensioneDaRecensore : recensioniDaRecensore) {
            ChiaveCompostaRecensioneSeguita chiaveCompostaRecensioneSeguita = new ChiaveCompostaRecensioneSeguita(event.getUtente(), recensioneDaRecensore.getId());
            RecensioneSeguita recensioneSeguita = new RecensioneSeguita(chiaveCompostaRecensioneSeguita, recensioneDaRecensore.getRecensore(), recensioneDaRecensore.getTitoloLibro(), recensioneDaRecensore.getAutoreLibro(), recensioneDaRecensore.getTestoRecensione());
            recensioniSeguite.add(recensioneSeguita);
        }

        recensioneSeguitaRepository.saveAll(recensioniSeguite);

    }

	/* Trova le recensioni seguite da un utente. */ 
	public Collection<Recensione> getRecensioniSeguite(String utente) {

        Collection<RecensioneSeguita> recensioniSeguite = recensioneSeguitaRepository.findAllByIdUtente(utente);
        Collection<Recensione> recensioni =  new TreeSet<>();

        for (RecensioneSeguita recensioneSeguita : recensioniSeguite) {
            Recensione recensione = new Recensione(recensioneSeguita.getId().getIdRecensione(), recensioneSeguita.getRecensore(), recensioneSeguita.getTitoloLibro(), recensioneSeguita.getAutoreLibro(), recensioneSeguita.getTestoRecensione());
            recensioni.add(recensione);
        }

		return recensioni;

	}

}
