ALTER TABLE template_destinatario DROP CONSTRAINT template_destinatario_codice_template_fkey;

ALTER TABLE template_destinatario
    ADD CONSTRAINT template_destinatario_codice_template_fkey FOREIGN KEY (id_organizzazione, codice_template)
    REFERENCES template_email (id_organizzazione, codice) MATCH SIMPLE
    ON UPDATE CASCADE
    ON DELETE CASCADE
    NOT VALID;