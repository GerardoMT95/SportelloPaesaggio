ALTER TABLE allegato
    ADD COLUMN path_diogene character varying(1000);

ALTER TABLE allegato
    ADD COLUMN path_reale_diogene character varying(2000);

ALTER TABLE allegato
    ADD COLUMN last_update_diogene timestamp;
    
COMMENT ON COLUMN allegato.id_diogene IS 'id assegnato al file su diogene';
COMMENT ON COLUMN allegato.path_diogene IS 'path sorgente per il file su diogene';
COMMENT ON COLUMN allegato.path_reale_diogene IS 'path su diogene comprensivo dei prefissi per i subpath di tipo folder';
COMMENT ON COLUMN allegato.last_update_diogene IS 'istante di aggiornamento corrispondente alla ricezione del messaggio di notifica su coda dal microservizio di inoltro';