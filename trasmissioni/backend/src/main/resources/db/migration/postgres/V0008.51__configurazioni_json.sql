CREATE TABLE configurazione (
	id bigserial NOT NULL,
	chiave varchar(400) NOT NULL,
	valore text NOT NULL,
	start_date timestamp NOT NULL DEFAULT now(),
	end_date timestamp NULL,
	user_create varchar(100) NULL,
	user_update varchar(100) NULL,
	CONSTRAINT configurazione_pkey PRIMARY KEY (id)
);
CREATE INDEX configurazione_chiave_idx ON configurazione USING btree (chiave, start_date);

COMMENT ON TABLE configurazione IS 'elenco oggetti di configurazione con relativa validità storica';
COMMENT ON COLUMN configurazione.chiave IS 'chiave logica della configurazione corrispondente alla classe del bean';
COMMENT ON COLUMN configurazione.valore IS 'json del bean di configurazione';


--inserimento primo record
INSERT INTO configurazione("chiave", "valore", "user_create")
VALUES ('it.eng.tz.puglia.autpae.dto.config.TipoDocumentoSportelloPaesaggioConfigDTO',
		'{"tipiSelezionati":[]}', 
		'INITVALUE')