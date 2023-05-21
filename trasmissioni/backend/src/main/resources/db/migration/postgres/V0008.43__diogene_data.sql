ALTER TABLE allegato
    ADD COLUMN id_diogene character varying(200);

ALTER TABLE allegato
    ADD COLUMN last_send_diogene timestamp;
