CREATE TABLE common.paesaggio_organizzazione
(
    id serial NOT NULL,
    ente_id integer NOT NULL,
    tipo_organizzazione character varying(100) NOT NULL,
    tipo_ente_delegato character varying(100),
    codice_civilia character varying(20),
    riferimento_organizzazione integer,
    data_creazione date NOT NULL,
    data_termine date NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ente_fkey FOREIGN KEY (ente_id)
        REFERENCES common.ente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE common.paesaggio_organizzazione
    OWNER to aet_user;
COMMENT ON TABLE common.paesaggio_organizzazione
    IS 'contiene le organizzazioni di paesaggio';

COMMENT ON COLUMN common.paesaggio_organizzazione.tipo_organizzazione
    IS 'tipi organizzazione previsti per paesaggio (trasmissioni, presentazione istanza, istruttoria)
Valori ammessi: 
ED(ente delegato) 
ETI(ente territorialmente interessato)
CL(commissione locale)
SBAP(soprintendenza)
';

COMMENT ON COLUMN common.paesaggio_organizzazione.tipo_ente_delegato
    IS 'Regione  (codice civilia 0)
Provincia (codice civilia 1)
Unione di comuni (codice civilia 2)
Associazione di comuni (codice civilia 3)
Comune (codice civilia 4)';

COMMENT ON COLUMN common.paesaggio_organizzazione.codice_civilia
    IS 'riferimento ad eventuale tabella civilia  TNO_PPTR_ABILITAZ_ENTEDELEG colonna codice, normalmente è il codice istat, per le unioni ed associazioni sembra un codice interno';

COMMENT ON COLUMN common.paesaggio_organizzazione.riferimento_organizzazione
    IS 'fa riferimento su questa stessa tabella, ed è popolato solo in caso di Commissione Locale, contiene l''ente delegato a cui fa riferimento';

COMMENT ON CONSTRAINT ente_fkey ON common.paesaggio_organizzazione
    IS 'riferimento alla tabella dell''anagrafica enti ';
	
	
ALTER TABLE common.paesaggio_organizzazione
    ADD CONSTRAINT riferimento_organizzazione_fkey FOREIGN KEY (riferimento_organizzazione)
    REFERENCES common.paesaggio_organizzazione (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

CREATE TABLE common.paesaggio_organizzazione_competenze
(
    id serial NOT NULL,
    paeseaggio_organizzazione_id integer NOT NULL,
    ente_id integer NOT NULL,
    data_inizio_delega date NOT NULL,
    data_fine_delega date NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT paesaggio_organizzazione_fkey FOREIGN KEY (id)
        REFERENCES common.paesaggio_organizzazione (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT ente_fkey FOREIGN KEY (ente_id)
        REFERENCES common.ente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE common.paesaggio_organizzazione_competenze
    OWNER to aet_user;
COMMENT ON TABLE common.paesaggio_organizzazione_competenze
    IS 'contiene la relazione temporale con gli enti su cui ha delega in merito ad autorizzazioni paesaggistiche (Province/COmuni)';	
	
CREATE TABLE common.paesaggio_organizzazione_attributi
(
    id serial NOT NULL,
    applicazione_id integer NOT NULL,
    paesaggio_organizzazione_id integer NOT NULL,
    data_creazione date NOT NULL,
    data_termine date NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT applicazione_fkey FOREIGN KEY (applicazione_id)
        REFERENCES common.applicazione (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT paesaggio_organizzazione_fkey FOREIGN KEY (paesaggio_organizzazione_id)
        REFERENCES common.paesaggio_organizzazione (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE common.paesaggio_organizzazione_attributi
    OWNER to aet_user;
COMMENT ON TABLE common.paesaggio_organizzazione_attributi
    IS 'attributi legati al codice applicazione';	    