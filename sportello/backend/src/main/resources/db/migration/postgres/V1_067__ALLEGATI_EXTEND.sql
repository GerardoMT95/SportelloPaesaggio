ALTER TABLE allegati
    ALTER COLUMN descrizione TYPE text  COLLATE pg_catalog."default";

ALTER TABLE allegati
    ADD COLUMN titolo character varying(255);

ALTER TABLE allegati
    ADD COLUMN id_allegato_pre_protocollazione uuid;

COMMENT ON COLUMN allegati.id_allegato_pre_protocollazione
    IS 'allegato originale utilizzato per la protocollazione e poi modificato con inserimento del protocollo';
ALTER TABLE allegati
    ADD CONSTRAINT allegato_pre_proto FOREIGN KEY (id_allegato_pre_protocollazione)
    REFERENCES allegati (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;