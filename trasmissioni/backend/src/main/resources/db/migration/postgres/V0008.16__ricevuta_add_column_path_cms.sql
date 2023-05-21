ALTER TABLE ricevuta
    ADD COLUMN id_allegato_dati_cert bigint;
COMMENT ON COLUMN ricevuta.id_allegato_dati_cert
    IS ' aggiunta per la migrazione a 2 step prima i dati e poi i riferimenti su alfresco, e'' fk su allegato, non dichiarata.';