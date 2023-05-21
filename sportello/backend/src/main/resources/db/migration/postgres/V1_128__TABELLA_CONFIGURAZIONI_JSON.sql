CREATE TABLE presentazione_istanza.configurazione (
	id bigserial NOT NULL,
	chiave varchar(400) NOT NULL,
	valore text NOT NULL,
	start_date timestamp NOT NULL DEFAULT now(),
	end_date timestamp NULL,
	user_create varchar(100) NULL,
	user_update varchar(100) NULL,
	CONSTRAINT configurazione_pkey PRIMARY KEY (id)
);
CREATE INDEX configurazione_chiave_idx ON presentazione_istanza.configurazione USING btree (chiave, start_date);

COMMENT ON TABLE presentazione_istanza.configurazione IS 'elenco oggetti di configurazione con relativa validità storica';
COMMENT ON COLUMN presentazione_istanza.configurazione.chiave IS 'chiave logica della configurazione corrispondente alla classe del bean';
COMMENT ON COLUMN presentazione_istanza.configurazione.valore IS 'json del bean di configurazione';
