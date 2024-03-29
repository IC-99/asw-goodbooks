# GOODBOOKS

Progetto svolto da Ivan Carlini per il corso di Architettura dei Sistemi Software per l'anno accademico 2022-2023. 

## Descrizione di questo progetto 

Questo progetto contiene il il codice di *GoodBooks*, 
un semplice social network per la condivisione di recensioni di libri.  
Gli utenti del sistema possono pubblicare delle recensioni. 
Possono poi seguire le recensioni di specifici recensori e di specifici autori di libri.  
Quando un utente accede alla pagina delle recensioni che segue, gli vengono mostrate le recensioni dei recensori e degli autori di libri che segue. 

L'applicazione *GoodBooks* è composta dai seguenti microservizi: 

* Il servizio *recensioni* gestisce le recensioni. 
  Ogni recensione ha un recensore, il titolo del libro e l'autore del libro a cui si riferisce, nonché il testo della recensione. 
  
  Un esempio di recensione: 
  * recensore: Woody
  * titolo libro: Guerra e Pace 
  * autore libro: Tolstoj
  * testo recensione: Parla della Russia
  
  Operazioni: 
  * `POST /recensioni` aggiunge una nuova recensione (dati recensore, titolo libro, autore libro e testo recensione)
  * `GET /recensioni/{id}` trova una recensione, dato l'id 
  * `GET /recensioni` trova tutte le recensioni
  * `GET /cercarecensioni/recensore/{recensore}` trova tutte le recensioni di un certo recensore
  * `GET /cercarecensioni/recensori/{insieme-di-recensori}` trova tutte le recensioni di un insieme di recensori 
  * `GET /cercarecensioni/titolo/{titolo}` trova tutte le recensioni di un certo libro
  * `GET /cercarecensioni/autore/{autore}` trova tutte le recensioni di un certo autore di libri
  * `GET /cercarecensioni/autori/{insieme-di-autori}` trova tutte le recensioni di un insieme di autori di libri 
* Il servizio *connessioni* gestisce le connessioni degli utenti con gli autori di libri e con i recensori che essi seguono. 
  Le connessioni sono delle coppie *utente-recensore* oppure *utente-autore*. 
  Operazioni: 
  * `POST /connessioniautore` aggiunge una nuova connessione utente-autore (dati utente e autore)
  * `GET /connessioniautore` trova tutte le connessioni utente-autore
  * `GET /connessioniautore/{utente}` trova tutte le connessioni utente-autore di un certo utente
  * `POST /connessionirecensore` aggiunge una nuova connessione utente-recensore (dati utente e recensore)
  * `GET /connessionirecensore` trova tutte le connessioni utente-recensore
  * `GET /connessionirecensore/{utente}` trova tutte le connessioni utente-recensore di un certo utente
* Il servizio *recensioni-seguite* consente a un utente di trovare le recensioni di tutti gli autori di libri e i recensori che segue. 
  Operazioni: 
  * `GET /recensioniseguite/{utente}` trova tutti le recensioni seguite da un certo utente, ovvero le renesioni di autori di libri e di recensori seguiti da quell'utente
* Il servizio *api-gateway* (esposto sulla porta *8080*) è l'API gateway dell'applicazione che: 
  * espone il servizio *recensioni* sul path `/recensioni` - ad esempio, `GET /recensioni/recensioni`
  * espone il servizio *connessioni* sul path `/connessioni` - ad esempio, `GET /connessioni/connessioniautore/{utente}`
  * espone il servizio *recensioni-seguite* sul path `/recensioni-seguite` - ad esempio, `GET /recensioni-seguite/recensioniseguite/{utente}`

## Descrizione delle attività svolte

* Nei servizi *recensioni* e *connessioni* è stata utilizzata una base di dati MySQL al posto di HSQLDB (ciascuna base di dati è eseguita in un container Docker separato).
* Quando viene aggiunta una nuova recensione, il servizio *recensioni* notifica un evento **RecensioneCreatedEvent** su un canale Kafka (eseguito su un container Docker separato).
* Quando viene aggiunta una nuova connessione utente-autore oppure una nuova connessione utente-recensore, il servizio *connessioni* notifica un evento **ConnessioneConAutoreCreated** oppure **ConnessioneConRecensoreCreated** su un canale Kafka.
* La logica del servizio *recensioni-seguite* è stata modificata come segue:
  * il servizio *recensioni-seguite* gestisce una propria base di dati MySQL in un container Docker separato, con una tabella per le recensioni, una per le connnessioni utente-autore, una per le connessioni utente-recensore e una per le recensioni seguite
  * ogni volta che il servizio *recensioni-seguite* riceve un evento **RecensioneCreatedEvent**, aggiorna la propria tabella delle recensioni e anche la tabella delle recensioni seguite
  * ogni volta che il servizio *recensioni-seguite* riceve un evento **ConnessioneConAutoreCreatedEvent** oppure **ConnessioneConRecensoreCreatedEvent**, aggiorna le proprie tabelle relative alle connessioni e anche la tabella delle recensioni seguite
  * il servizio *recensioni-seguite* risponde alle richieste GET /recensioniseguite/{utente} accedendo solo alla propria tabella delle recensioni seguite
* I diversi servizi, all'avvio dell'applicazione, sono eseguiti in diversi container Docker attraverso l'utilizzo di Docker Compose.

## Esecuzione 

Per eseguire questo progetto:  

* per avviare l'applicazione *GoodBooks*, eseguire lo script `run-goodbooks.sh`
* per inizializzare le basi di dati con dei dati di esempio, eseguire gli script `do-init-recensioni.sh` e `do-init-connessioni.sh` 

Sono anche forniti alcuni script di esempio: 

* lo script `run-curl-client.sh` esegue un insieme di interrogazioni di esempio
* lo script `do-get-recensioni.sh` trova tutte le recensioni
* lo script `do-get-recensione.sh` trova una recensione
* lo script `do-get-recensioni-per-autore.sh` trova tutte le recensioni di un certo autore
* lo script `do-get-recensioni-per-autori.sh` trova tutte le recensioni di un insieme di autori
* lo script `do-get-recensioni-per-titolo.sh` trova tutte le recensioni di un certo libro
* lo script `do-get-recensioni-per-recensore.sh` trova tutte le recensioni di un certo recensore
* lo script `do-get-recensioni-per-recensori.sh` trova tutte le recensioni di un insieme di recensori
* lo script `do-get-connessioni.sh` trova tutte le connessioni
* lo script `do-get-recensioni-seguite.sh` trova tutte le recensioni seguite da un certo utente

Alla fine, l'applicazione può essere arrestata usando lo script `stop-goodbooks.sh`. 
