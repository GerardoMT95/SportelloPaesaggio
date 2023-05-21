ALTER TABLE presentazione_istanza.allegati ADD id_diogene_scheduler varchar(36) NULL;
COMMENT ON COLUMN presentazione_istanza.allegati.id_diogene_scheduler IS 'chiave nella tabella common.diogene_scheduler, è lo stesso uuid dell''allegato';

