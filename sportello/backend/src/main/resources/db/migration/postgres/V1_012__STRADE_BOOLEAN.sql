ALTER TABLE presentazione_istanza.localizzazione_intervento DROP COLUMN strade;

ALTER TABLE presentazione_istanza.localizzazione_intervento
    ADD COLUMN strade boolean;