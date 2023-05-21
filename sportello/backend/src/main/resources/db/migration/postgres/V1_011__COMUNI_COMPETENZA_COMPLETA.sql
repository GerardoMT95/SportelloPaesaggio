ALTER TABLE presentazione_istanza.comuni_competenza
    ADD COLUMN descrizione character varying(255);

ALTER TABLE presentazione_istanza.comuni_competenza
    ADD COLUMN cod_cat character varying(4);

ALTER TABLE presentazione_istanza.comuni_competenza
    ADD COLUMN cod_istat character varying(10);            