-- Aggiunta colonne per settare periodo di validità JIRA ETI6-67
ALTER TABLE tipo_procedimento
    ADD COLUMN inizio_validita date,
    ADD COLUMN fine_validita date;
    
COMMENT ON COLUMN tipo_procedimento.inizio_validita
    IS ' data inizio validità tipo procedimento';
COMMENT ON COLUMN tipo_procedimento.fine_validita
    IS ' data fine validità tipo procedimento';
    
    
-- Set dei valori iniziali
UPDATE tipo_procedimento
 SET inizio_validita= '1970-01-01', fine_validita='9999-12-31'