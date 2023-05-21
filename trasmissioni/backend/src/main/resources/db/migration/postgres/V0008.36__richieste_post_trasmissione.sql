CREATE TABLE richieste_post_trasmissione
(
    id bigserial,
    id_fascicolo bigint NOT NULL,
    motivazione text NOT NULL,
    stato character varying(100) NOT NULL,
    tipo character varying(50) NOT NULL,
    date_created timestamp NOT null default now(),
    user_created character varying(100) NOT NULL,
    date_updated timestamp,
    user_updated character varying(100),
    id_corrispondenza bigint,
    PRIMARY KEY (id),
    CONSTRAINT fascicolo_fkey FOREIGN KEY (id_fascicolo)
        REFERENCES fascicolo (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO action,
    CONSTRAINT corrispondenza_fkey FOREIGN KEY (id_corrispondenza)
        REFERENCES corrispondenza (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION    
);

COMMENT ON TABLE richieste_post_trasmissione
    IS 'richieste di modifica o di cancellazione post trasmissione con relativo stato';

COMMENT ON COLUMN richieste_post_trasmissione.tipo
    IS 'Modifica o  Cancellazione';
COMMENT ON COLUMN richieste_post_trasmissione.stato
    IS 'Bozza o  Trasmessa';    
    
   
ALTER TABLE allegato
    ADD COLUMN id_richiesta_post_trasmissione bigint;
ALTER TABLE allegato
    ADD CONSTRAINT richieste_post_trasmissione_fkey FOREIGN KEY (id_richiesta_post_trasmissione)
    REFERENCES richieste_post_trasmissione (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;
COMMENT ON COLUMN allegato.id_richiesta_post_trasmissione
    IS 'riferimento ad eventuale richiesta post trasmissione';    