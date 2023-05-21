ALTER TABLE common.utente_attribute
    ADD COLUMN IF NOT EXISTS descr_struttura varchar(255) DEFAULT null;
COMMENT ON COLUMN common.utente_attribute.descr_struttura IS 'descrizione della eventuale struttura a cui appartiene utente (utilizzata in PEV es. Settore Urbanistica e LLPP, Attività Produttive)';    
