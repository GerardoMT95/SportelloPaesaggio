CREATE TABLE "common"."paesaggio_organizzazione_rubrica" (
	id bigserial NOT NULL,
	paesaggio_organizzazione_id int4 NOT NULL,
	codice_applicazione varchar(80) NOT NULL,
	nome varchar(100) NOT NULL,
	pec varchar(100) NULL,
	mail varchar(100) NULL,
	data_creazione timestamp NOT NULL,
	data_modifica timestamp NULL,
	username_creazione varchar(80) NOT NULL,
	username_modifica varchar(80) NULL,
	CONSTRAINT paesaggio_organizzazione_rubrica_pk PRIMARY KEY (id, paesaggio_organizzazione_id, codice_applicazione),
	CONSTRAINT paesaggio_organizzazione_rubri_paesaggio_organizzazione_id_fkey FOREIGN KEY (paesaggio_organizzazione_id) REFERENCES common.paesaggio_organizzazione(id),
	CONSTRAINT paesaggio_organizzazione_rubrica_codice_applicazione_fkey FOREIGN KEY (codice_applicazione) REFERENCES common.applicazione(codice)
);


ALTER TABLE "common"."paesaggio_organizzazione_rubrica" OWNER TO aet_user;