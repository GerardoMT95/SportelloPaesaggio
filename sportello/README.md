# Sportello Paesaggio

Il Repository include il codice sorgente dell'applicativo Sportello per la presentazione delle istanze di Autorizzazione e Accertamento di compatibilità paesaggistica ai sensi del PPTR utilizzato nell'ambito del progetto Servizi Digitali per Ambiente e Territorio della Regione Puglia
Il software si compone di due componente frontend (backoffice e frontoffice) realizzate in angular e una componente backend basata su spring boot.
La compilazione avviene con Maven e la parte backend si esegue con openjdk 11.
L'autenticazione avviene tramite protocollo openid oauth2  e sfrutta il sistema Login Regione Puglia per consentire accesso tramite SPID, CIE o CNS.
La lista dei software utilizzate è

- Redis per cache distribuita
- Alfresco CE come repository documentale
- Wso2 IS per gestione autenticazione
- PostgreSQL come db server
- jasper per generazione report
- ActiveMq Artemis
- Kafka

Come dipendenze sono utilizzati i microservizi:

- common
- mail
- cms
- anagrafica
- ireport
- funzioni-trasversali
- user-manager
- protocollo
- profile
- firma remota
- mypay
- profile manager
- autpae
- meetpad-puglia

recuperabili all'interno del repository https://oros-git.regione.puglia.it/regione-puglia/aet/dipendenze

Il software si interfaccia anche con lo sportello unico delle autorizzazioni ambientali presente al link https://oros-git.regione.puglia.it/regione-puglia/aet/sportello-ambiente

Sono previsti 4 profili di compilazione:

- sviluppo
- collaudo_interno
- collaudo_cliente
- produzione

Il software prevede il deploy in architettura a microservizi

All'interno dei progetti vanno configurati i file:

- environment.ts
- environment.collaudo_interno.ts
- environment.collaudo_cliente.ts
- environment.produzione.ts
- sviluppo.properties
- collaudo_interno.properties
- collaudo_cliente.properties
- produzione.properties


Con le proprietà reali quali ad esempio url e puntamenti a database o chiavi