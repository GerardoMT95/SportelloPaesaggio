ALTER TABLE presentazione_istanza.referenti 
    ALTER COLUMN cap_residenza TYPE character varying (5) COLLATE pg_catalog."default";

ALTER TABLE presentazione_istanza.referenti
    ALTER COLUMN codice_fiscale TYPE character varying (16) COLLATE pg_catalog."default";
	
ALTER TABLE presentazione_istanza.referenti ALTER COLUMN 
tecnico_studio_cap TYPE character varying (5) COLLATE pg_catalog."default";