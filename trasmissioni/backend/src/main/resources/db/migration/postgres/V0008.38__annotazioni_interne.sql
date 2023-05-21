CREATE TABLE annotazioni_interne
(
    id bigserial,
    id_fascicolo bigint NOT NULL,
    id_organizzazione integer NOT NULL,
    esito_controllo character varying(50) NOT NULL,
    note text NOT NULL,
    date_created timestamp NOT null default now(),
    user_created character varying(100) NOT NULL,
    date_updated timestamp,
    user_updated character varying(100),
    PRIMARY KEY (id),
    CONSTRAINT fascicolo_fkey FOREIGN KEY (id_fascicolo)
        REFERENCES fascicolo (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO action
);

CREATE UNIQUE INDEX indice_organizzazione_fascicolo
ON annotazioni_interne(id_fascicolo, id_organizzazione);

COMMENT ON TABLE annotazioni_interne
    IS 'Annotazioni interne inerenti ad uno specifico fascicolo';
    
ALTER TABLE allegato
    ADD COLUMN id_annotazione_interna bigint;
ALTER TABLE allegato
    ADD CONSTRAINT annotazioni_interne_fkey FOREIGN KEY (id_annotazione_interna)
    REFERENCES annotazioni_interne (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;
COMMENT ON COLUMN allegato.id_annotazione_interna
    IS 'riferimento ad eventuale annotazione interna';    