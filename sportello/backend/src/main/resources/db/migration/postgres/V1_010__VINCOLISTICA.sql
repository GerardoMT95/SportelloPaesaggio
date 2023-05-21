CREATE TABLE presentazione_istanza.vinc_arch
(
    pratica_id uuid NOT NULL,
    vinc_arc_no_tutela character varying(1),
    vinc_arc_monum_diretto character varying(1),
    vinc_arc_monum_indiretto character varying(1),
    vinc_arc_archeo_diretto character varying(1),
    vinc_arc_archeo_indiretto character varying(1),
    altri_vincoli character varying(4000),
    PRIMARY KEY (pratica_id),
    CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id)
        REFERENCES presentazione_istanza.pratica (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

ALTER TABLE presentazione_istanza.vinc_arch
    OWNER to presentazione_istanza;
COMMENT ON TABLE presentazione_istanza.vinc_arch
    IS 'pannello Vincolistica in scheda tecnica
TABLE CIVILIA_CS.TNO_PPTR_VINC_ARCH_ARCH 
';

COMMENT ON COLUMN presentazione_istanza.vinc_arch.vinc_arc_no_tutela
    IS 'Si No';

ALTER TABLE presentazione_istanza.leggittimita_provvedimenti
    ALTER COLUMN id DROP DEFAULT;

ALTER TABLE presentazione_istanza.leggittimita_titoli
    ALTER COLUMN id DROP DEFAULT;
        
DROP SEQUENCE presentazione_istanza.leggittimita_titoli_id_seq;

DROP TABLE presentazione_istanza.particelle_catastali;

DROP TABLE presentazione_istanza.destinazione_urbanistica_intervento;

ALTER TABLE presentazione_istanza.localizzazione_intervento DROP COLUMN id;
--ALTER TABLE presentazione_istanza.localizzazione_intervento DROP CONSTRAINT localizzazione_intervento_pkey;

ALTER TABLE presentazione_istanza.localizzazione_intervento
    ADD CONSTRAINT localizzazione_intervento_pkey PRIMARY KEY (pratica_id, comune_id);
    
--DROP SEQUENCE presentazione_istanza.localizzazione_intervento_id_seq;


CREATE TABLE presentazione_istanza.destinazione_urbanistica_intervento
(
	pratica_id uuid NOT NULL,
    comune_id bigint NOT NULL ,
    id integer NOT NULL,
    strum_urb_approvato character varying(3) COLLATE pg_catalog."default",
    strum_urb_approvato_data date,
    strum_urb_approvato_atto character varying(4000) COLLATE pg_catalog."default",
    destin_area_str_vig character varying(4000) COLLATE pg_catalog."default",
    strum_approv_ult_tutele character varying(4000) COLLATE pg_catalog."default",
    strum_urb_adottato character varying(3) COLLATE pg_catalog."default",
    strum_urb_adottato_data date,
    strum_urb_adottato_atto character varying(4000) COLLATE pg_catalog."default",
    destin_area_str_adott character varying(4000) COLLATE pg_catalog."default",
    strum_adott_ult_tutele character varying(4000) COLLATE pg_catalog."default",
    conforme_discipl_urb_vigente character varying(1) COLLATE pg_catalog."default",
    check_presa_visione character varying(1) COLLATE pg_catalog."default",
    id_strum_urban_art38 bigint,
    id_strum_urban_art100 bigint,
    CONSTRAINT destinazione_urbanistica_intervento_pkey PRIMARY KEY (pratica_id,comune_id,id),
    CONSTRAINT id_strum_urban_art100_fkey FOREIGN KEY (id_strum_urban_art100)
        REFERENCES presentazione_istanza.tno_pptr_strumenti_urbanistici (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT id_strum_urban_art38_fkey FOREIGN KEY (id_strum_urban_art38)
        REFERENCES presentazione_istanza.tno_pptr_strumenti_urbanistici (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT localizzazione_intervento_fkey FOREIGN KEY (pratica_id,comune_id)
        REFERENCES presentazione_istanza.localizzazione_intervento (pratica_id,comune_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id)
        REFERENCES presentazione_istanza.pratica (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

ALTER TABLE presentazione_istanza.destinazione_urbanistica_intervento
    OWNER to presentazione_istanza;
COMMENT ON TABLE presentazione_istanza.destinazione_urbanistica_intervento
    IS 'simile TNO_PPTR_DEST_URB_INTERV di CIVILIA_CS un record per ogni record di localizzazione_intervento
';

COMMENT ON COLUMN presentazione_istanza.destinazione_urbanistica_intervento.strum_urb_approvato
    IS 'PRG PUG Pdf';

COMMENT ON COLUMN presentazione_istanza.destinazione_urbanistica_intervento.strum_urb_adottato
    IS 'PUG VAR NO
';

COMMENT ON COLUMN presentazione_istanza.destinazione_urbanistica_intervento.check_presa_visione
    IS 'Si No';




CREATE TABLE presentazione_istanza.particelle_catastali
(
    pratica_id uuid NOT NULL,
    comune_id bigint NOT NULL ,
    id integer NOT NULL,
    livello character varying(100) ,
    sezione character varying(100) ,
    foglio character varying(100) ,
    particella character varying(100) ,
    sub character varying(100)  ,
    cod_cat character varying(10) ,
    CONSTRAINT particelle_catastali_pkey PRIMARY KEY (pratica_id,comune_id,id),
    CONSTRAINT localizzazione_intervento_fkey FOREIGN KEY (pratica_id,comune_id)
        REFERENCES presentazione_istanza.localizzazione_intervento (pratica_id,comune_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id)
        REFERENCES presentazione_istanza.pratica (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

ALTER TABLE presentazione_istanza.particelle_catastali
    OWNER to presentazione_istanza;

COMMENT ON COLUMN presentazione_istanza.particelle_catastali.cod_cat
    IS 'codice catastale';


            