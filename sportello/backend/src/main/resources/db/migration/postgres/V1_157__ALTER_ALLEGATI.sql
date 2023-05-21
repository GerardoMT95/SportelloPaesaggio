ALTER TABLE presentazione_istanza.allegati ADD COLUMN is_signed boolean NULL DEFAULT NULL;

comment ON COLUMN allegati.is_signed IS 'Indica se il documento deve essere firmato digitalmente ';