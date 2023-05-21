DROP TABLE presentazione_istanza.allegati_tipo_contenuto;

DROP TABLE presentazione_istanza.tipo_contenuto;

CREATE TABLE presentazione_istanza.tipo_contenuto
(
    id integer NOT NULL,
    descrizione character varying(400) COLLATE pg_catalog."default" NOT NULL,
    descrizione_estesa text COLLATE pg_catalog."default" NOT NULL,
    sezione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tipi_allegati_pkey PRIMARY KEY (id)
)TABLESPACE pg_default;

ALTER TABLE presentazione_istanza.tipo_contenuto
    OWNER to presentazione_istanza;
COMMENT ON TABLE presentazione_istanza.tipo_contenuto
    IS 'tipi di contenuto previsto per l''istanza';
COMMENT ON COLUMN presentazione_istanza.tipo_contenuto.descrizione_estesa
    IS 'testo HTML con ul e li visibile in legenda';

COMMENT ON COLUMN presentazione_istanza.tipo_contenuto.sezione
    IS 'DOC_AMMINISTRATIVA_D DOC_AMMINISTRATIVA_E DOC_TECNICA ASSENSO_ALTRI_TIT';

CREATE TABLE presentazione_istanza.allegati_tipo_contenuto
(
    allegati_id uuid NOT NULL,
    tipo_contenuto_id integer NOT NULL,
    CONSTRAINT allegati_tipo_contenuto_pkey PRIMARY KEY (allegati_id, tipo_contenuto_id),
    CONSTRAINT allegati_fkey FOREIGN KEY (allegati_id)
        REFERENCES presentazione_istanza.allegati (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT tipo_contenuto_fkey FOREIGN KEY (tipo_contenuto_id)
        REFERENCES presentazione_istanza.tipo_contenuto (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)TABLESPACE pg_default;

ALTER TABLE presentazione_istanza.allegati_tipo_contenuto
    OWNER to presentazione_istanza;
COMMENT ON TABLE presentazione_istanza.allegati_tipo_contenuto
    IS 'relazione n-m tra allegati e tipo_contenuto';
    

    
CREATE TABLE presentazione_istanza.procedimento_contenuto
(
    tipo_procedimento integer NOT NULL,
    tipo_contenuto_id integer NOT NULL,
    CONSTRAINT procedimento_contenuto_pkey PRIMARY KEY (tipo_procedimento,tipo_contenuto_id),
    CONSTRAINT tipo_procedimento_fkey FOREIGN KEY (tipo_procedimento)
        REFERENCES presentazione_istanza.tipo_procedimento (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT tipo_contenuto_fkey FOREIGN KEY (tipo_contenuto_id)
        REFERENCES presentazione_istanza.tipo_contenuto (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)TABLESPACE pg_default;

ALTER TABLE presentazione_istanza.procedimento_contenuto
    OWNER to presentazione_istanza;
COMMENT ON TABLE presentazione_istanza.procedimento_contenuto
    IS 'contiene la relazione tra tipo_procedimento e tipo contenuto ammesso';

     