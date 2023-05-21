CREATE TABLE "assegnamento_fascicolo"
(
    "id_fascicolo" 				  UUID 				 	 NOT NULL references "pratica",
    "id_organizzazione" 		  int 				  	 NOT NULL,
    "fase" 						  character varying(100) NOT NULL,
    "rup"  						  boolean				 NOT NULL,
    "username_utente" 		  	  character varying(100),
    "denominazione_utente"   	  character varying(100),
	"num_assegnazioni" 			  smallint,
	"data_operazione" 			  timestamp,
	CONSTRAINT assegnamento_fascicolo_pkey PRIMARY KEY (id_fascicolo, id_organizzazione, fase, rup)
);

CREATE TABLE "storico_assegnamento" 
(
    "id" 						  bigserial 			 NOT NULL primary key,
    "id_fascicolo" 				  UUID	 				 NOT NULL references "pratica",
    "id_organizzazione" 		  int 				   	 NOT NULL,
    "fase" 						  character varying(100) NOT NULL,
    "rup"  						  boolean				 NOT NULL,
    "username_utente" 		  	  character varying(100),
    "denominazione_utente"   	  character varying(100),
	"data_operazione" 			  timestamp,
	"tipo_operazione"			  character varying(100)
);
COMMENT ON COLUMN "storico_assegnamento"."tipo_operazione" IS 'ASSEGNAZIONE | DISASSEGNAZIONE | RIASSEGNAZIONE';

CREATE TABLE "configurazione_assegnamento" 
(
    "id_organizzazione" 		  int 				   	 NOT NULL,
    "fase" 						  character varying(100) NOT NULL,
    "rup"  						  boolean				 NOT NULL,
    "criterio_assegnamento" 	  character varying(100) 		 , --NOT NULL,
    CONSTRAINT configurazione_assegnamento_pkey PRIMARY KEY (id_organizzazione, fase, rup)
);
COMMENT ON COLUMN "configurazione_assegnamento"."criterio_assegnamento" IS 'MANUALE | LOCALIZZAZIONE | TIPO_PROCEDIMENTO';

CREATE TABLE "valori_assegnamento" 
(
    "id_organizzazione" 		  int 				   	 NOT NULL,
    "fase" 						  character varying(100) NOT NULL,
    "id_comune_tipo_procedimento" int					 NOT NULL,
    "rup"  						  boolean				 NOT NULL,
    "username_utente" 		  	  character varying(100) NOT NULL,
    "denominazione_utente"   	  character varying(100) NOT NULL,
    CONSTRAINT valori_assegnamento_pkey PRIMARY KEY (id_organizzazione, fase, id_comune_tipo_procedimento, rup) -- username_utente)
);
ALTER TABLE "valori_assegnamento" ADD CONSTRAINT valori_assegnamento_fkey FOREIGN KEY (id_organizzazione, fase, rup) REFERENCES configurazione_assegnamento(id_organizzazione, fase, rup);
