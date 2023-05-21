ALTER TABLE fascicolo
    ADD COLUMN codice_pratica_appptr character varying(50);

COMMENT ON COLUMN fascicolo.codice_pratica_appptr
    IS 'derivante anche da civilia';

ALTER TABLE fascicolo
    ADD COLUMN t_pratica_id bigint;  
COMMENT ON COLUMN fascicolo.t_pratica_id
    IS 'derivante da civilia T_PRATICA_ID';

    
ALTER TABLE localizzazione_intervento
    ALTER COLUMN sigla_provincia TYPE character varying (200);
    
CREATE INDEX t_pratica_id
    ON fascicolo USING btree
    (t_pratica_id ASC NULLS LAST)
    TABLESPACE pg_default;

COMMENT ON INDEX t_pratica_id
    IS 'indice su pratica';    
	 	   