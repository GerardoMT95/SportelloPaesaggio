ALTER TABLE presentazione_istanza.privacy ALTER COLUMN data_inizio TYPE timestamp USING data_inizio::timestamp;
ALTER TABLE presentazione_istanza.privacy ALTER COLUMN data_inizio SET DEFAULT current_timestamp;
ALTER TABLE presentazione_istanza.privacy ALTER COLUMN data_fine TYPE timestamp USING data_fine::timestamp;
ALTER TABLE presentazione_istanza.privacy ALTER COLUMN data_fine DROP NOT NULL;
ALTER TABLE presentazione_istanza.privacy ADD user_inserted varchar(100) NULL;
ALTER TABLE presentazione_istanza.privacy ADD user_updated varchar(100) NULL;
UPDATE presentazione_istanza.privacy set user_inserted='INITVALUE';
ALTER TABLE presentazione_istanza.privacy ALTER COLUMN user_inserted SET NOT NULL;
UPDATE presentazione_istanza.privacy set data_inizio='2000-01-01 00:00:00';
UPDATE presentazione_istanza.privacy set data_fine=null;

