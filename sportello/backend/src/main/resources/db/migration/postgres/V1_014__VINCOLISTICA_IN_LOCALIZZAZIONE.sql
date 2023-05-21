CREATE TABLE presentazione_istanza.parchi_paesaggi_immobili
(
    pratica_id uuid NOT NULL,
    comune_id bigint NOT NULL,
    tipo_vincolo "char" NOT NULL,
    codice character varying(40) NOT NULL,
    descrizione character varying(255),
    selezionato character varying(1),
    info character varying(255),
    data_inserimento date,
    PRIMARY KEY (pratica_id, comune_id, tipo_vincolo, codice),
    CONSTRAINT localizzazione_intervento_fkey FOREIGN KEY (pratica_id, comune_id)
        REFERENCES presentazione_istanza.localizzazione_intervento (pratica_id, comune_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

ALTER TABLE presentazione_istanza.parchi_paesaggi_immobili
    OWNER to presentazione_istanza;
COMMENT ON TABLE presentazione_istanza.parchi_paesaggi_immobili
    IS 'contiene le selezioni effettuate e le selezioni ammesse nel pannello localizzazione in ulteriori informazioni.
Corrisponde alle tabelle in  PSITPIANI (DB AS-IS) TNO_INTERESSE_PUBBLICO_ASS 
TNO_PAESAGGIRURALI_ASS
TNO_ENTEPARCO_ASS';

COMMENT ON COLUMN presentazione_istanza.parchi_paesaggi_immobili.tipo_vincolo
    IS 'P=BP parchi e riserve 
R= UCP paesaggi rurali
I= BP  Immobili interesse pubblico
';

COMMENT ON COLUMN presentazione_istanza.parchi_paesaggi_immobili.selezionato
    IS 'S=Si ';
    
COMMENT ON COLUMN presentazione_istanza.parchi_paesaggi_immobili.info
    IS 'eventuale indirizzo mail legato al vincolo';    
    

