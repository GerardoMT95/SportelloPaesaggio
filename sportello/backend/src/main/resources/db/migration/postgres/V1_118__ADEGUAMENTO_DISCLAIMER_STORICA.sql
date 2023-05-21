ALTER TABLE presentazione_istanza.disclaimer ADD data_inizio timestamp NOT NULL DEFAULT current_timestamp;
ALTER TABLE presentazione_istanza.disclaimer ADD data_fine timestamp NULL;
ALTER TABLE presentazione_istanza.disclaimer ADD user_inserted varchar(100) NULL;
ALTER TABLE presentazione_istanza.disclaimer ADD user_updated varchar(100) NULL;
UPDATE presentazione_istanza.disclaimer set user_inserted='INITVALUE';
ALTER TABLE presentazione_istanza.disclaimer ALTER COLUMN user_inserted SET NOT NULL;
CREATE SEQUENCE presentazione_istanza.disclaimer_id_seq start with 24;
ALTER TABLE presentazione_istanza.disclaimer ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.disclaimer_id_seq');
UPDATE presentazione_istanza.disclaimer set data_inizio='2000-01-01 00:00:00';
