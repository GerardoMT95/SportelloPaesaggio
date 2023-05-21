-- Aggiunta colonna per identificare a quale applicativo appartiene il tipo procedimento JIRA ETI6-67
ALTER TABLE tipo_procedimento
    ADD COLUMN applicativo character varying(10);
    
COMMENT ON COLUMN tipo_procedimento.applicativo
    IS 'Applicativo di appartanenza (AUTPAE, PUTT, PARERI)';

    
    
-- Set dei valori iniziali
UPDATE tipo_procedimento
 SET applicativo= 'AUTPAE'
WHERE substr(codice,1,1) like 'A%';

UPDATE tipo_procedimento
 SET applicativo= 'PUTT'
WHERE substr(codice,1,4) like 'PUTT%';

UPDATE tipo_procedimento
 SET applicativo= 'PARERI'
WHERE substr(codice,1,6) like 'PARERE%';