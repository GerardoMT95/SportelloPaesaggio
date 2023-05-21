SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET default_with_oids = false;

CREATE TABLE "modifiche_tab" 
(
    "hashcode" smallint NOT NULL,
    "tab"  character varying(20) NOT NULL,
    "info" text NOT NULL,
   	PRIMARY KEY ("hashcode", "tab")
);

CREATE TABLE "allegato" 
(
    "id" bigserial primary key,
    "nome" character varying NOT NULL,
    "descrizione" character varying,
    "mime_type" character varying,
    "data_caricamento" timestamp NOT NULL DEFAULT now(),
    "contenuto" character varying NOT NULL,
    "checksum" character varying NOT NULL,
    "deleted" boolean NOT NULL,
    "dimensione" integer NOT NULL,
    "titolo" character varying(100),
    "numero_protocollo_in" character varying,
    "numero_protocollo_out" character varying,
    "data_protocollo_in" timestamp,
    "data_protocollo_out" timestamp,
    "utente_inserimento" character varying(100) NOT NULL,
    "username_inserimento" character varying(100) NOT NULL
);

COMMENT ON COLUMN "allegato"."nome" 
    IS 'usually the file name with extension, which is sent as response to FE, while the file can be stored under different id - see checksum';
COMMENT ON COLUMN "allegato"."contenuto" 
    IS 'used to store address and/or specific node id of the file''s content';
COMMENT ON COLUMN "allegato"."checksum" 
    IS 'MD5 checksum of the file, which is used as the file name on the file server';

CREATE TABLE "corrispondenza" 
(
    "id" bigserial primary key NOT NULL,
    "id_eml_cmis" character varying,
    "message_id" character varying,
    "mittente_email" character varying NOT NULL,
    "mittente_denominazione" character varying NOT NULL,
    "mittente_username" character varying NOT NULL,
    "mittente_ente" character varying NOT NULL,
    "data_invio" timestamp NOT NULL DEFAULT now(),
    "oggetto" character varying NOT NULL,
    "stato" boolean NOT NULL,
    "comunicazione" boolean NOT NULL,
    "bozza" boolean NOT NULL,
    "text" text
);

CREATE TABLE "allegato_corrispondenza" 
(
    "id_allegato" bigint references "allegato",
    "id_corrispondenza" bigint references "corrispondenza",
    constraint "allegato_corrispondenza_pk" primary key("id_allegato","id_corrispondenza")
);

create table "tipo_procedimento"
(
	"codice" character varying(50) primary key,
	"descrizione" character varying(1000),
	"invia_email" boolean NOT NULL default false,
	"sanatoria" boolean NOT NULL default false
);

CREATE TABLE "tipo_allegato"
(
    "type" character varying(255) primary key,
    "descrizione" character varying(255)
);

CREATE TABLE "tipi_e_qualificazioni"
(
    "id" bigserial primary key NOT NULL,
    "stile" character varying NOT NULL,
    "raggruppamento" character(1) NOT NULL,
    "zona" integer NOT NULL,
    "label" character varying(1000) NOT NULL,
    "ordine" integer NOT NULL
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

create table "fascicolo" 
(
	"id" bigserial primary key,
	"ufficio" character varying(50) not null,
	"org_creazione" integer not null,
	"stato" character varying(50),
	"stato_precedente" character varying(50),
	
	"tipo_procedimento" character varying(50) references "tipo_procedimento" not null,
	"sanatoria" boolean,
	"oggetto_intervento" character varying(5000) not null,
	"codice_interno_ente" character varying(255),
	"numero_interno_ente" character varying(255),
	"protocollo" character varying(255),
	"data_protocollo" date,
	"note" character varying(5000),
	"codice" character varying(255) unique,
	
	"intervento_dettaglio" character varying, 
  	"intervento_specifica" character varying,  
	
  	"numero_provvedimento" character varying(100),
	"data_rilascio_autorizzazione" date,
	"rup" character varying(255),
  	"esito" character varying(50),
  	
  	"esito_numero_provvedimento" character varying(100),
	"esito_data_rilascio_autorizzazione" date,
	"esito_verifica" character varying(255),
	"esito_verifica_precedente" character varying(255),
	
	"vers_fascicolo"      smallint NOT NULL,	--references "modifiche_tab"(hashcode)
	"vers_richiedente"    smallint NOT NULL,	--references "modifiche_tab"(hashcode)
	"vers_intervento"     smallint NOT NULL,	--references "modifiche_tab"(hashcode)
	"vers_localizzazione" smallint NOT NULL,	--references "modifiche_tab"(hashcode)
	"vers_allegati"       smallint NOT NULL,	--references "modifiche_tab"(hashcode)
	"vers_provvedimento"  smallint NOT NULL,	--references "modifiche_tab"(hashcode)
	
	"data_creazione" timestamp not null default now(),
	"data_ultima_modifica" timestamp,
	"data_trasmissione" timestamp,
	"data_campionamento" timestamp,
	"data_verifica" timestamp,
	"username_utente_creazione" character varying(255),
	"username_utente_ultima_modifica" character varying(255),
	"username_utente_trasmissione" character varying(255),
	"denominazione_utente_trasmissione" character varying(255),
	"email_utente_trasmissione" character varying(255),	
	
	"info_complete" jsonb,
	"deleted" boolean NOT NULL DEFAULT false,
	"modificabile_fino" date,
	"tipo_selezione_localizzazione" character varying(20),
	"has_shape" boolean 
);

COMMENT ON COLUMN "fascicolo"."tipo_selezione_localizzazione"
    IS 'valori ammessi: PTC=selezione particelle SHPD=Shape editing SHPF shape file';
    
create table "richiedente"
(
	"id" bigserial primary key,
	"nome" character varying(255),
	"cognome" character varying(255),
	"codice_fiscale" character varying(16),
	"sesso" char,
	"data_nascita" date,
	"stato_nascita" character varying(50),
	"provincia_nascita" character varying(30),
	"comune_nascita" character varying(40),
	"stato_residenza" character varying(50),
	"provincia_residenza" character varying(30),
	"comune_residenza" character varying(40),
	"via_residenza" character varying(255),
	"numero_residenza" character varying(50),
	"cap" character varying(5),
	"pec" character varying(200),
	"email" character varying(200),
	"telefono" character varying(20),
	"ditta_societa" character varying(255),
	"ditta_in_qualita_di" character varying(255),
	"ditta_qualita_altro" character varying(255),
	"ditta_codice_fiscale" character varying(16),
	"ditta_partita_iva" character varying(11),
	"id_fascicolo" bigint not null references "fascicolo"
);

CREATE TABLE "procedimento_qualificazioni" 
(
    "codice_tipo_procedimento" character varying NOT NULL references "tipo_procedimento",
    "id_tipi_qualificazioni" bigint NOT NULL references "tipi_e_qualificazioni",
	constraint "procedimento_qualificazioni_pk" primary key("codice_tipo_procedimento", "id_tipi_qualificazioni")
);

CREATE TABLE "fascicolo_intervento"
(
    "id_fascicolo" bigint NOT NULL REFERENCES "fascicolo",
    "id_tipi_qualificazioni" bigint NOT NULL REFERENCES "tipi_e_qualificazioni",
    CONSTRAINT "fascicolo_intervento_pk" PRIMARY KEY (id_fascicolo, id_tipi_qualificazioni)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE "allegato_fascicolo" 
(
    "id_allegato" bigint references "allegato" NOT NULL,
    "type" character varying(255) NOT NULL references "tipo_allegato",
    "id_fascicolo" bigint NOT NULL references "fascicolo",
    CONSTRAINT "allegato_fascicolo_pk" PRIMARY KEY (id_allegato, type)
);


CREATE TABLE "localizzazione_intervento" 
(
	"pratica_id" 				 int8 		   NOT NULL,
	"comune_id" 				 int8		   NOT NULL,
	"indirizzo"				   	 varchar(400),
	"num_civico"				 varchar( 30),
	"piano" 					 varchar( 30),
	"interno" 				   	 varchar( 50),
	"dest_uso_att" 			   	 varchar(400),
	"dest_uso_prog" 			 varchar(400),
	"comune" 					 varchar(100),
	"sigla_provincia" 		   	 varchar(  5),
	"data_riferimento_catastale" date,
	"strade" 					 boolean,
	CONSTRAINT "localizzazione_intervento_pkey" PRIMARY KEY (pratica_id, comune_id)
);
ALTER TABLE localizzazione_intervento ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES fascicolo(id) ON DELETE CASCADE;



CREATE TABLE "parchi_paesaggi_immobili" 
(
	"pratica_id" 	   int8 		NOT NULL,
	"comune_id" 	   int8 		NOT NULL,
	"tipo_vincolo" 	      char(  1) NOT NULL,
	"codice" 		   varchar( 40) NOT NULL,
	"descrizione" 	   varchar(255),
	"selezionato"      varchar(  1),
	"info" 			   varchar(255),
	"data_inserimento" date,
	CONSTRAINT "parchi_paesaggi_immobili_pkey" PRIMARY KEY (pratica_id, comune_id, tipo_vincolo, codice)
);
ALTER TABLE parchi_paesaggi_immobili ADD CONSTRAINT localizzazione_intervento_fkey FOREIGN KEY (pratica_id, comune_id) REFERENCES localizzazione_intervento(pratica_id, comune_id) ON DELETE CASCADE;
ALTER TABLE parchi_paesaggi_immobili ADD CONSTRAINT pratica_fkey 				  FOREIGN KEY (pratica_id) REFERENCES fascicolo(id) ON DELETE CASCADE;

CREATE TABLE "particelle_catastali" 
(
	"pratica_id" int8		  NOT NULL,
	"comune_id"  int8 		  NOT NULL,
	"id" 		 int4 		  NOT NULL,
	"livello" 	 varchar(100),
	"sezione" 	 varchar(100),
	"foglio" 	 varchar(100),
	"particella" varchar(100),
	"sub" 		 varchar(100),
	"cod_cat" 	 varchar( 10),
	"oid" 	 int8,
	"note"	 varchar(255),
	CONSTRAINT "particelle_catastali_pkey" PRIMARY KEY (pratica_id, comune_id, id)
);
ALTER TABLE particelle_catastali ADD CONSTRAINT localizzazione_intervento_fkey FOREIGN KEY (pratica_id, comune_id) REFERENCES localizzazione_intervento(pratica_id, comune_id) ON DELETE CASCADE;
ALTER TABLE particelle_catastali ADD CONSTRAINT pratica_fkey 				  FOREIGN KEY (pratica_id) REFERENCES fascicolo(id) ON DELETE CASCADE;


create table "configurazione_campionamento"
(
	"id" bigserial primary key,
	"campionamento_attivo" boolean not null,
    "intervallo_campionamento" smallint not null default 28,
	"tipo_campionamento_successivo" character varying(100) not null default 'PREDEFINITO',
	"percentuale_istanze" smallint not null,
	"giorni_notifica_campionamento" character varying(100),
	"giorni_notifica_verifica" character varying(100),
	"intervallo_verifica" smallint,
	"esito_pubblico" boolean not null,
	"applica_in_corso" boolean not null default false
);
COMMENT ON COLUMN "configurazione_campionamento"."tipo_campionamento_successivo" 
    IS 'La stringa "PREDEFINITO"  indica che il campionamento verrà effettuato a partire dalla data di ultimo campionamento effettuato;
		La stringa "TRASMISSIONE" indica che il campionamento verrà effettuato a partire dalla data di trasmissione della prima pratica rispetto all''ultimo campionamento';

create table "configurazione_permessi"
(
	"codice_ente" character varying(255) primary key,
	"permesso_documentazione" 	   boolean not null,
	"permesso_osservazione"   	   boolean not null,
	"permesso_comunicazione"  	   boolean not null,
	"stato_registrazione_pubblico" boolean,
	"esito_verifica_pubblico" 	   boolean
);

create table "template_email"
(
	"codice" character varying(255) primary key,
	"nome" character varying(255),
	"descrizione" character varying(1000) not null,
	"oggetto" character varying(255) not null,
	"testo" text not null
);

create table "template_email_default"
(
	"codice" character varying(255) primary key,
	"nome" character varying(255),
	"descrizione" character varying(1000) not null,
	"oggetto" character varying(255) not null,
	"testo" text not null
);

create table "template_destinatario"
(
	"id" bigserial primary key,
    "codice_template" character varying(255) not null references "template_email",
	"email" character varying(250),
	"pec" character varying(250),
	"denominazione" character varying(255) not null,
	"tipo" character varying(10) not null
);


create table "template_destinatario_default"
(
	"id" bigserial primary key,
    "codice_template" character varying(255) not null references "template_email_default",
	"email" character varying(250),
	"pec" character varying(250),
	"denominazione" character varying(255) not null,
	"tipo" character varying(10) not null
);


CREATE TABLE "destinatario" 
(
    "id" bigserial primary key,
    "type" character varying(10) NOT NULL,
    "email" character varying(255) NOT NULL,
    "stato" character varying(20),
    "pec" boolean,
    "denominazione" character varying(100) NOT NULL,
	"data_ricezione" timestamp,
    "id_corrispondenza" bigint NOT NULL references "corrispondenza"
);

COMMENT ON COLUMN "destinatario"."type" IS 'can be ''TO'' or ''CC''.';


CREATE TABLE "ricevuta" 
(
    "id" bigserial primary key,
    "id_corrispondenza" bigint NOT NULL references "corrispondenza",
    "id_destinatario" bigint references "destinatario",
    "tipo_ricevuta" character varying(100),
    "errore" character varying(100),
    "descrizione_errore" character varying,
    "id_cms_eml" character varying,
    "id_cms_dati_cert" character varying,
	"id_cms_smime" character varying,
	"data" timestamp
);


CREATE TABLE "fascicolo_corrispondenza" 
(
	"id" bigserial primary key NOT NULL,
    "id_corrispondenza" bigint NOT NULL references "corrispondenza",
    "id_fascicolo" bigint NOT NULL references "fascicolo"
);


CREATE TABLE "comuni_competenza" 
(
	"pratica_id" 	   int8 		NOT NULL,
	"ente_id" 		   int4 		NOT NULL,
	"descrizione" 	   varchar(255),
	"cod_cat" 		   varchar(  4),
	"cod_istat" 	   varchar( 10),
	"data_inserimento" timestamp 	NOT NULL DEFAULT now(),
	"creazione"		   boolean		NOT NULL,
	"effettivo"		   boolean		NOT NULL DEFAULT false,
	CONSTRAINT comuni_competenza_pkey PRIMARY KEY (pratica_id, ente_id, creazione)
);
ALTER TABLE comuni_competenza ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES fascicolo(id) ON DELETE CASCADE;






CREATE TABLE "assegnamento_fascicolo"
(
    "id_fascicolo" 				  bigint 				 NOT NULL references "fascicolo",
    "id_organizzazione" 		  int 				  	 NOT NULL,
    "fase" 						  character varying(100) NOT NULL,
    "username_funzionario" 		  character varying(100),
    "denominazione_funzionario"   character varying(100),
	"num_assegnazioni" 			  smallint,
	"data_operazione" 			  timestamp,
	CONSTRAINT assegnamento_fascicolo_pkey PRIMARY KEY (id_fascicolo, id_organizzazione, fase)
);

CREATE TABLE "storico_assegnamento" 
(
    "id" 						  bigserial 			 NOT NULL primary key,
    "id_fascicolo" 				  bigint 				 NOT NULL references "fascicolo",
    "id_organizzazione" 		  int 				   	 NOT NULL,
    "fase" 						  character varying(100) NOT NULL,
    "username_funzionario" 		  character varying(100),
    "denominazione_funzionario"   character varying(100),
	"data_operazione" 			  timestamp,
	"tipo_operazione"			  character varying(100)
);
COMMENT ON COLUMN "storico_assegnamento"."tipo_operazione" IS 'ASSEGNAZIONE | DISASSEGNAZIONE | RIASSEGNAZIONE';

CREATE TABLE "configurazione_assegnamento" 
(
    "id_organizzazione" 		  int 				   	 NOT NULL,
    "fase" 						  character varying(100) NOT NULL,
    "criterio_assegnamento" 	  character varying(100) NOT NULL,
    CONSTRAINT configurazione_assegnamento_pkey PRIMARY KEY (id_organizzazione, fase)
);
COMMENT ON COLUMN "configurazione_assegnamento"."criterio_assegnamento" IS 'MANUALE | LOCALIZZAZIONE | TIPO_PROCEDIMENTO';

CREATE TABLE "valori_assegnamento" 
(
    "id_organizzazione" 		  int 				   	 NOT NULL,
    "fase" 						  character varying(100) NOT NULL,
    "id_comune_tipo_procedimento" character varying(100) NOT NULL,
    "username_funzionario" 		  character varying(100) NOT NULL,
    "denominazione_funzionario"   character varying(100) NOT NULL,
    CONSTRAINT valori_assegnamento_pkey PRIMARY KEY (id_organizzazione, fase, id_comune_tipo_procedimento, username_funzionario)
);
ALTER TABLE "valori_assegnamento" ADD CONSTRAINT valori_assegnamento_fkey FOREIGN KEY (id_organizzazione, fase) REFERENCES configurazione_assegnamento(id_organizzazione, fase);






CREATE TABLE "campionamento" 
(
    "id" bigserial primary key NOT NULL,
    "attivo" boolean NOT NULL,
    "intervallo_camp" smallint NOT NULL,
    "tipo_successivo" character varying(100) NOT NULL,
    "percentuale" smallint NOT NULL,
    "notifica_camp" character varying(100),
	"notifica_ver" character varying(100),
	"intervallo_ver" smallint,
    "data_inizio" date NOT NULL,
    "data_campionatura" date NOT NULL,
    "customized" boolean NOT NULL default FALSE,
    "esito_pubb" boolean
);
COMMENT ON COLUMN "campionamento"."tipo_successivo" 
    IS 'La stringa "PREDEFINITO"  indica che il campionamento verrà effettuato a partire dalla data di ultimo campionamento effettuato;
		La stringa "TRASMISSIONE" indica che il campionamento verrà effettuato a partire dalla data di trasmissione della prima pratica rispetto all''ultimo campionamento';

CREATE TABLE "fascicolo_campionamento" 
(
    "id_campionamento" bigint NOT NULL references "campionamento",
    "id_fascicolo" bigint NOT NULL references "fascicolo",
    CONSTRAINT "fascicolo_campionamento_pkey" PRIMARY KEY ("id_campionamento", "id_fascicolo")
);


CREATE TABLE "allegato_obbligatorio"
(
    "type" character varying(255) references "tipo_allegato" NOT NULL,
    "codice_tipo_procedimento" character varying(255) references "tipo_procedimento" NOT NULL,
    CONSTRAINT "allegato_obbligatorio_pkey" PRIMARY KEY ("type", "codice_tipo_procedimento")
);

CREATE TABLE "allegato_ente"
(
    "id_allegato" bigint references "allegato" NOT NULL,
    "codice" character varying(20) NOT NULL,
    "descrizione" character varying(255),
    CONSTRAINT "allegato_ente_pkey" PRIMARY KEY ("id_allegato", "codice")
);

CREATE TABLE "codice_fascicolo"
(
    "codice_ente" character varying(20) NOT NULL,
    "anno" integer NOT NULL,
    "seriale" integer NOT NULL,
     CONSTRAINT "codice_fascicolo_pkey" PRIMARY KEY ("codice_ente", "anno")
);

create table "configurazione_casella_postale" (
	"email" character varying(255) primary key,
	"configurazione" character varying(255) not null
);
