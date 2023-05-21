PROGETTO TRASMISSIONI AUTORIZZAZIONI PAESAGGISTICHE PPTR/PUTT/Pareri BACKEND 
===

Il progetto è compilato con Maven e necessità di OpenJDK versione 11

La compilazione avviene con il comando:

- mvn clean install

Aggiungendo opzione -P #profilo# compila utilizzando il profilo maven per il deploy nell'ambiente di destinazione.

Il progetto è di tipo spring-boot e pertanto l'esecuzione avviene dal comando **java -jar autpae-be.jar** dove autpae-be.jar e l'artefatto creato dal comando di build all'interno della cartella target

Il progetto utilizza database postgres 10.

Questo microservizio puo' essere lanciato per 3 applicazioni differenti: autpae o pareri o putt.
Il discriminante è la properties spring.application.name (default a autpae).
Lo schema DB è direttamente legato al nome applicazione.



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



DB INIT
===
Generazione DATABASE:
	 
```
CREATE USER autpae with encrypted password '<<PASSWORD>>';
CREATE USER putt with encrypted password '<<PASSWORD>>';
CREATE USER pareri with encrypted password '<<PASSWORD>>';
CREATE SCHEMA autpae;
CREATE SCHEMA putt;
CREATE SCHEMA pareri;

--GRANT PER ACCESSO ALLE VISTE DI SPORTELLO UNICO per prelievo di provvedimenti paesaggistici emessi per paur

grant usage on schema procedimenti_ambientali to autpae;
grant usage on schema procedimenti_ambientali to putt;
grant usage on schema procedimenti_ambientali to pareri;

grant select  on table procedimenti_ambientali.v_paesaggio_provvedimenti to autpae;
grant select  on table procedimenti_ambientali.v_paesaggio_provvedimenti to pareri;
grant select  on table procedimenti_ambientali.v_paesaggio_provvedimenti to putt;

grant select  on table procedimenti_ambientali.v_paesaggio_tipo_carica_documento to autpae;
grant select  on table procedimenti_ambientali.v_paesaggio_tipo_carica_documento to pareri;
grant select  on table procedimenti_ambientali.v_paesaggio_tipo_carica_documento to putt;

grant select  on table procedimenti_ambientali.pratica_localizzazione to autpae;
grant select  on table procedimenti_ambientali.pratica_localizzazione to pareri;
grant select  on table procedimenti_ambientali.pratica_localizzazione to putt;

grant select  on table procedimenti_ambientali.pratica_localizzazione_comune to autpae;
grant select  on table procedimenti_ambientali.pratica_localizzazione_comune to pareri;
grant select  on table procedimenti_ambientali.pratica_localizzazione_comune to putt;

grant select  on table procedimenti_ambientali.pratica_localizzazione_particelle to autpae;
grant select  on table procedimenti_ambientali.pratica_localizzazione_particelle to pareri;
grant select  on table procedimenti_ambientali.pratica_localizzazione_particelle to putt;

grant select  on table procedimenti_ambientali.shape to autpae;
grant select  on table procedimenti_ambientali.shape to pareri;
grant select  on table procedimenti_ambientali.shape to putt;

grant select  on table procedimenti_ambientali.pratica to autpae;
grant select  on table procedimenti_ambientali.pratica to pareri;
grant select  on table procedimenti_ambientali.pratica to putt;

grant select  on table procedimenti_ambientali.pratica_documentazione_progettuale to autpae;
grant select  on table procedimenti_ambientali.pratica_documentazione_progettuale to pareri;
grant select  on table procedimenti_ambientali.pratica_documentazione_progettuale to putt;

grant select  on table procedimenti_ambientali.tipo_documentazione_progettuale to autpae;
grant select  on table procedimenti_ambientali.tipo_documentazione_progettuale to pareri;
grant select  on table procedimenti_ambientali.tipo_documentazione_progettuale to putt;


grant select  on table procedimenti_ambientali.layer_geo to autpae;
grant select  on table procedimenti_ambientali.layer_geo to pareri;
grant select  on table procedimenti_ambientali.layer_geo to putt;

grant usage on schema presentazione_istanza to autpae;
grant usage on schema presentazione_istanza to putt;
grant usage on schema presentazione_istanza to pareri;

grant select  on table presentazione_istanza.pratica to autpae;
grant select  on table presentazione_istanza.pratica to putt;
grant select  on table presentazione_istanza.pratica to pareri;

grant select  on table presentazione_istanza.v_fascicolo_provvedimento to autpae;
grant select  on table presentazione_istanza.v_fascicolo_provvedimento to putt;
grant select  on table presentazione_istanza.v_fascicolo_provvedimento to pareri;

grant select  on table presentazione_istanza.layer_geo to autpae;
grant select  on table presentazione_istanza.layer_geo to putt;
grant select  on table presentazione_istanza.layer_geo to pareri;

grant select  on table presentazione_istanza.v_fascicolo_localizzazione_intervento to autpae;
grant select  on table presentazione_istanza.v_fascicolo_localizzazione_intervento to putt;
grant select  on table presentazione_istanza.v_fascicolo_localizzazione_intervento to pareri;

grant select  on table presentazione_istanza.v_fascicolo_tipi_e_qualificazioni to autpae;
grant select  on table presentazione_istanza.v_fascicolo_tipi_e_qualificazioni to putt;
grant select  on table presentazione_istanza.v_fascicolo_tipi_e_qualificazioni to pareri;

grant select  on table presentazione_istanza.v_fascicolo_tipo_procedimento to autpae;
grant select  on table presentazione_istanza.v_fascicolo_tipo_procedimento to putt;
grant select  on table presentazione_istanza.v_fascicolo_tipo_procedimento to pareri;

```


GESTIONE ABILITAZIONI
=== 
Per la gestione delle richieste di abilitazione in capo agli amministratori dell'organizzazione (ETI ED etc...) sono necessari i seguenti:
- nominare il responsabile dei tre gruppi dell'organizzazione (normalmente è un utente associato al gruppo degli amministratori XXX_NN_A)
- associarlo sul PM come responsabile dei tre gruppi
- dare il ruolo di PM_USER al suddetto responsabile


BATCH TASK
====
Per i task batch (es campionamento, etc..) viene utilizzato un utente ad hoc da inserire tra gli utenti (batchuser) dovrebbe essere già presente perchè utilizzato da altre applicazioni


AVVIO AMBIENTE
====
-  controllare la configurazione delle caselle di posta per ogni possibile configurazione (profilo / applicazione ), ricordarsi che le password nei file json di configurazione sono criptate 
-  resettare il template documentazione per lettera trasmissione in modo da far caricare il logo default in alfresco
-  caricare il manuale applicazione su alfresco e aggiornare la tupla in common.sit\_puglia\_configuration alla chiave:
-  'MANUALE-CMIS-ID','nomeapplicazione' (pareri o autpae o putt)  inserendo al value l'id cms es.: 'a305f773-66af-402c-a3fc-ff2f56e05190'
-  'MANUALE-CMIS-ID-ADMIN','nomeapplicazione' (pareri o autpae o putt)  inserendo al value l'id cms 
-  'URL\_DOWNLOAD\_PREFIX' va inserito l'url per il download degli allegati alle mail sotto autenticazione es:
    https://puglialab.eng.it/autpae

SWAGGER OPENAPI V3
===
l'url è in ../swagger-ui-openapi.html  
va compilato il file openapi.properties
Il documento v3 apidocs è in:
http://localhost:8081/autpae/v3/api-docs
http://localhost:8081/autpae/swagger-ui/index.html?configUrl=/autpae/v3/api-docs/swagger-config



ATTIVAZIONE DIOGENE
===
Per l'attivazione del componente che invia i documenti al microservizio di inoltro a Diogene:

	- verificare che la properties jms.enable=true
	- verificare che il broker jms sia attivo e raggiungibile all'url in properties jms.url
	- verificare la diogene.send.cron.expression che contiene la schedulazione del task di invio messaggi dei documenti da inoltrare a Diogene
	- caricare configurazione account diogene per l'applicazione in questione (autpae) tramite funzione di amministrazione ad hoc in front-end
	
```
diogene:
   protocolloADG: 165
   tipo: false
   limit: 10
   enviroment: SVILUPPO
   gateway:
      username: ggggg
      password: zxxxxxx
      conumerKey: 89eqAGGOotCEl7Fx6T07PHI6krIa
      consumerSecret: zQzBDRKfYD0J7fGJffhQQ0OdBH4a
   username: kkkkkk
   password: pswkkkkk
   conumerKey: zzzzzzzz
   consumerSecret: ffffffffff
   clientIp: 0.0.0.0
   idCartellaRoot: 1583494291121183
   nomeCartellaRoot: Area TEST apigwbandi
   idTitolario: 12
   idVoceTitolario: 32
   idAoo: 116
```
Per l'alberatura utilizzata si è fatto riferimento a:

	-AUTPAE_ROOT_FOLDER
		-SUBAPPLICATION_CODE[AUTPAE,PARERI,PUTT]
			-CODICECIVILIAENTE - Descrizione ente
				-ANNO-NN [ANNO del codice pratica, NN=NUMERO_NEL_CODICE_PRATICA/1000]
					-CODICE_PRATICA
						-TIPOLOGIA_DOCUMENTO [Provvedimento,Allegati,RicevutaTrasmissione]
							-Filexyz.ext
				   
DOWNLOAD DOCUMENTI SPORTELLO UNICO AMBIENTE
===
Per l'accesso in lettura ai documenti dello sportello, va dato accesso in lettura su alfresco 
al repository di sportello ambiente AUT-AMB all'utente utilizzato sulle tre trasmissioni: autpae

