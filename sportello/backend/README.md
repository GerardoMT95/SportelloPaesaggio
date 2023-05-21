Progetto Backend per sportello paesaggio e gestione iter istruttorio
===

Il progetto è compilato con Maven e necessità di OpenJDK versione 11

La compilazione avviene con il comando:

- mvn clean install

Aggiungendo opzione -P #profilo# compila utilizzando il profilo maven per il deploy nell'ambiente di destinazione.

Il progetto è di tipo spring-boot e pertanto l'esecuzione avviene dal comando **java -jar paesaggio-presentazione-istanza-be.jar** dove paesaggio-presentazione-istanza-be.jar e l'artefatto creato dal comando di build all'interno della cartella target

Il progetto utilizza database postgres 10.


MICROSERVIZI INVOCATI
===
- protocollo
- webmail client
- cms
- user_manager
- profile_manager
- richiesta abilitazione


SERVIZI INFRASTRUTTURALI
===
- kafka
- Postgres 10
- jms



COMMISSIONE LOCALE
===
L'utenza con ruolo amministratore applicazione deve avere i diritti di responsabile applicazione
**PAE_PRES_IST**
sul PM



GRANT VERSO ALTRI SCHEMA
===
va dato 
 grant di select su opendata per poter utilizzare accesso a tabella ipa enti
 grant di select su common per poter utilizzare accesso a ente
 grant di select su cdst

``` 
grant usage on schema opendata to presentazione_istanza;
grant select on table opendata.ipa_enti to presentazione_istanza;
grant usage on schema common to presentazione_istanza;
grant select on common.ente to presentazione_istanza;
GRANT SELECT ON common.utente TO presentazione_istanza;
GRANT SELECT ON common.utente_attribute TO presentazione_istanza;
GRANT SELECT ON common.applicazione TO presentazione_istanza;
GRANT SELECT ON common.paesaggio_organizzazione TO presentazione_istanza;
GRANT SELECT ON common.ente_attribute TO presentazione_istanza;
grant usage on schema anagrafica to presentazione_istanza;
grant select on anagrafica.comune to presentazione_istanza;
grant select on anagrafica.provincia to presentazione_istanza;
grant usage on schema cdst to presentazione_istanza;
grant select on cdst.tipologia_conferenza_specializzazione to presentazione_istanza;
grant select on cdst.azione to presentazione_istanza;
grant select on cdst.attivita to presentazione_istanza;
grant select on cdst.modalita to presentazione_istanza;
grant select on cdst.conferenza to presentazione_istanza;
grant select on cdst.stato to presentazione_istanza;
grant select on cdst.protocollo to presentazione_istanza;
grant select on cdst.tipologia_documento to presentazione_istanza;
grant select on cdst.categoria_documento to presentazione_istanza;
grant select on cdst.registro_documento to presentazione_istanza;
grant select on cdst.documento to presentazione_istanza;
grant select on table cdst.tipologia_pratica  to presentazione_istanza;
```
