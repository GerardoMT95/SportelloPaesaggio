CREATE TABLE presentazione_istanza.privacy
(
    id serial NOT NULL,
    testo text NOT NULL,
    data_inizio date NOT NULL,
    data_fine date NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE presentazione_istanza.privacy
    OWNER to presentazione_istanza;
COMMENT ON TABLE presentazione_istanza.privacy
    IS 'dichiarazioni di privacy';

COMMENT ON COLUMN presentazione_istanza.privacy.testo
    IS 'html della privacy';
ALTER TABLE presentazione_istanza.pratica
    ADD COLUMN privacy_id integer;
ALTER TABLE presentazione_istanza.pratica
    ADD CONSTRAINT privacy_id FOREIGN KEY (privacy_id)
    REFERENCES presentazione_istanza.privacy (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;    