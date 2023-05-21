ALTER TABLE allegato_fascicolo DROP CONSTRAINT allegato_fascicolo_pk;

ALTER TABLE allegato_fascicolo
    ADD CONSTRAINT allegato_fascicolo_pk PRIMARY KEY (id_allegato, type, id_fascicolo);