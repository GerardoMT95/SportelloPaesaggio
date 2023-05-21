CREATE TABLE presentazione_istanza.subentro (
	id uuid NULL,
	id_pratica uuid NULL,
	stato varchar(1) NOT NULL,
	cognome varchar(100) NULL,
	nome varchar(100) NULL,
	codice_fiscale varchar(16) NULL,
	sesso varchar(1) NULL,
	data_nascita date NULL,
	id_nazione_nascita int4 NULL,
	nazione_nascita varchar(400) NULL,
	id_comune_nascita int4 NULL,
	comune_nascita varchar(400) NULL,
	comune_nascita_estero varchar(400) NULL,
	id_nazione_residenza int4 NULL,
	nazione_residenza varchar(400) NULL,
	id_comune_residenza int4 NULL,
	comune_residenza varchar(400) NULL,
	comune_residenza_estero varchar(400) NULL,
	indirizzo_residenza varchar(400) NULL,
	civico_residenza varchar(100) NULL,
	cap_residenza varchar(5) NULL,
	pec varchar(100) NULL,
	mail varchar(100) NULL,
	date_create timestamp NOT NULL DEFAULT now(),
	date_update timestamp NULL,
	cmis_id_modulo varchar(40) NULL,
	file_name_modulo varchar(400) NULL,
	cmis_id_sollevamento varchar(40) NULL,
	file_name_sollevamento varchar(400) NULL,
	hash_modulo varchar(400) NULL,
	hash_sollevamento varchar(400) NULL,
	CONSTRAINT subentro_pkey PRIMARY KEY (id),
	CONSTRAINT subentro_fk_1 FOREIGN KEY (id_pratica) REFERENCES presentazione_istanza.pratica(id)
);

COMMENT ON COLUMN presentazione_istanza.subentro.stato IS 'B=bozza C=concluso';

CREATE TABLE presentazione_istanza.pratica_owner_history (
	id uuid NOT NULL,
	username varchar(100) NOT NULL,
	codice_segreto_utilizzato varchar(36) NOT NULL,
	create_date timestamp NOT NULL DEFAULT now(),
	cmis_id_delega varchar(40) NOT NULL,
	file_name_delega varchar(400) NOT NULL,
	cmis_id_sollevamento_incarico varchar(40) NULL,
	file_name_sollevamento_incarico varchar(400) NULL,
	CONSTRAINT pratica_owner_history_pkey PRIMARY KEY (id, username, codice_segreto_utilizzato),
	CONSTRAINT pratica_owner_trasmissione_fk_1 FOREIGN KEY (id) REFERENCES presentazione_istanza.pratica(id)
);

COMMENT ON TABLE presentazione_istanza.pratica_owner_history IS 'Utenti che hanno fatto change ownership ';
