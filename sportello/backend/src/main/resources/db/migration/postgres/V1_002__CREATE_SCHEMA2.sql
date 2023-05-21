DROP TABLE presentazione_istanza.disclaimer_pratica;

CREATE TABLE presentazione_istanza.disclaimer_pratica
(
    disclaimer_id integer NOT NULL,
    flag character varying(1) COLLATE pg_catalog."default",
    pratica_id uuid NOT NULL,
    CONSTRAINT disclaimer_pratica_pkey PRIMARY KEY (pratica_id,disclaimer_id),
    CONSTRAINT disclaimer_fkey FOREIGN KEY (disclaimer_id)
        REFERENCES presentazione_istanza.disclaimer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id)
        REFERENCES presentazione_istanza.pratica (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE presentazione_istanza.disclaimer_pratica
    OWNER to presentazione_istanza;