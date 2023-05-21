ALTER TABLE particelle_catastali ADD COLUMN elaborato bpchar(1) DEFAULT 'I';
ALTER TABLE particelle_catastali ADD COLUMN last_start_time timestamp NULL;

COMMENT ON COLUMN particelle_catastali.elaborato IS 'STATE BATCH: I (Inserito),A (Avviato),C (Completato),E (Errore)';
COMMENT ON COLUMN particelle_catastali.last_start_time IS 'last start time';



UPDATE particelle_catastali set elaborato='C' WHERE oid is null;