# Trasmissioni provvedimenti paesaggio

Servizio di trasmissione autorizzazioni paesaggistiche da parte degli enti delegati e Regione Puglia ai sensi del PPTR o PUTT\p 

Il Repository include il codice sorgente dell'applicativo Trasmissioni provvedimenti paesaggio utilizzato nell'ambito del progetto Ambiente e Territorio della Regione Puglia
Il software si compone di:

una componente frontend realizzata in angular
una componente backend basata su spring boot.

La compilazione avviene con Maven e la parte backend si esegue con openjdk 11.
L'autenticazione avviene tramite protocollo openid oauth2  e sfrutta il sistema Login Regione Puglia per consentire accesso tramite SPID, CIE o CNS.
La lista dei software utilizzate è

Redis per cache distribuita
Alfresco CE come repository documentale
Wso2 IS per gestione autenticazione
PostgreSQL come db server
ActiveMq Artemis
Kafka

Come dipendenze sono utilizzati i microservizi:

common
mail
cms
anagrafica
ireport
funzioni-trasversali
user-manager
protocollo
profile
firma remota
mypay
webmail
diogene
diogene-client


recuperabili all'interno del repository https://oros-git.regione.puglia.it/regione-puglia/aet/dipendenze
il microservizio:

profile manager

recuperabile all'interno del repository https://oros-git.regione.puglia.it/regione-puglia/aet/profile-manager
il microservizio

paesaggio-presentazione-istanza

recupreabile all'interno del repository https://oros-git.regione.puglia.it/regione-puglia/aet/sportello-paesaggio
e il progetto GIO (nel caso si vogliano notifiche con App IO): recuperabile all'interno del repository https://oros-git.regione.puglia.it/regione-puglia/gio
Sono previsti 4 profili di compilazione:

sviluppo
collaudo_interno
collaudo_cliente
produzione

Il software prevede il deploy in architettura a microservizi
All'interno dei progetti vanno configurati i file:

environment.ts
environment.collaudo_interno.ts
environment.collaudo_cliente.ts
environment.produzione.ts

sviluppo.properties
collaudo_interno.properties
collaudo_cliente.properties
produzione.properties

Con le proprietà reali quali ad esempio url e puntamenti a database o chiavi.

Il microservizio di BACKEND è deployabile per 3 diverse tipologie di trasmissioni:
- Provvedimenti PPTR
- Provvedimenti PUTT\p
- Pareri di compatibilità paesaggistica

a seconda del valore di una properties.
