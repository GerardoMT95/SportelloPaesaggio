CREATE TABLE "responsabile"
(
	"id" bigserial primary key,
	"nome" character varying(255) ,
	"cognome" character varying(255) ,
	"in_qualita_di" character varying(255),
	"servizio_settore_ufficio" character varying(255),
	"pec" character varying(255) ,
	"mail" character varying(255),
	"telefono" character varying(20),
	"riconoscimento_tipo" character varying(255) ,
	"riconoscimento_numero" character varying(100) ,
	"riconoscimento_data_rilascio" date ,
	"riconoscimento_data_scadenza" date ,
	"riconoscimento_rilasciato_da" character varying(255) ,
	"id_fascicolo" bigint not null references "fascicolo"
);

INSERT INTO tipo_allegato ("type", "descrizione") VALUES ('DOCUMENTO_RICONOSCIMENTO', 'Documento di riconoscimento del responsabile');

ALTER TABLE "fascicolo"
	ADD COLUMN "data_delibera" date,
	ADD COLUMN "delibera_num" character varying(100),
	ADD COLUMN "oggetto_delibera" character varying(1023),
	ADD COLUMN "info_delibere_precedenti" character varying(1023),
	ADD COLUMN "descrizione_intervento" character varying(1023);
