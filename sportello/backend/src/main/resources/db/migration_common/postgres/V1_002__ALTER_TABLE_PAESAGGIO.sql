ALTER TABLE common.paesaggio_organizzazione_competenze DROP CONSTRAINT paesaggio_organizzazione_fkey;

ALTER TABLE common.paesaggio_organizzazione_competenze
    ADD CONSTRAINT paesaggio_organizzazione_fkey FOREIGN KEY (paeseaggio_organizzazione_id)
    REFERENCES common.paesaggio_organizzazione (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

COMMENT ON COLUMN common.paesaggio_organizzazione.tipo_organizzazione IS 'tipi organizzazione previsti per paesaggio (trasmissioni, presentazione istanza, istruttoria)
Valori ammessi: 
REG(Regione) 
ED(ente delegato) 
ETI(ente territorialmente interessato)
CL(commissione locale)
SBAP(soprintendenza)
';	    