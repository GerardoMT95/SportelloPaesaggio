CREATE TABLE presentazione_istanza.ruolo_pratica (
	id bpchar(2) NOT NULL,
	descrizione varchar(400) NOT NULL,
	delegato bool NULL DEFAULT false,
	CONSTRAINT ruolo_pratica_pkey PRIMARY KEY (id)
);

COMMENT ON COLUMN presentazione_istanza.ruolo_pratica.id IS 'Codice del ruolo'; 
COMMENT ON COLUMN presentazione_istanza.ruolo_pratica.descrizione IS 'Descrizione del ruolo';
COMMENT ON COLUMN presentazione_istanza.ruolo_pratica.delegato IS 'indica se è il ruolo di delegato';

INSERT INTO presentazione_istanza.ruolo_pratica (id, descrizione, delegato) VALUES('PR', 'Proponente', false);
INSERT INTO presentazione_istanza.ruolo_pratica (id, descrizione, delegato) VALUES('DE', 'Delegato', true);

ALTER TABLE presentazione_istanza.pratica ADD ruolo_pratica bpchar(2) NULL;
ALTER TABLE presentazione_istanza.pratica ADD CONSTRAINT pratica_fk_ruolo FOREIGN KEY (ruolo_pratica) REFERENCES presentazione_istanza.ruolo_pratica(id);
-- setto tutte a delegato
UPDATE presentazione_istanza.pratica SET ruolo_pratica = 'DE';
ALTER TABLE presentazione_istanza.pratica ALTER COLUMN ruolo_pratica SET NOT NULL;
COMMENT ON COLUMN presentazione_istanza.pratica.ruolo_pratica IS 'Ruolo assunto durente creazione pratica';

ALTER TABLE presentazione_istanza.pratica ADD codice_segreto varchar(36) NULL;
ALTER TABLE presentazione_istanza.pratica ADD owner varchar(100) NULL;
COMMENT ON COLUMN presentazione_istanza.pratica.owner IS 'Username dell''owner del procedimento';
UPDATE presentazione_istanza.pratica SET owner = user_id;
ALTER TABLE presentazione_istanza.pratica ALTER COLUMN owner SET NOT NULL;
ALTER TABLE presentazione_istanza.pratica ADD user_updated varchar(100) NULL;
COMMENT ON COLUMN presentazione_istanza.pratica.owner IS 'Username che ha modificato il record';


