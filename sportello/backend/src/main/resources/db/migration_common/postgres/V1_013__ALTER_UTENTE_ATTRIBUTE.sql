ALTER TABLE common.utente_attribute
    ADD COLUMN last_richiesta_abilitazione bigint DEFAULT null;
ALTER TABLE common.utente_attribute
    ADD COLUMN create_time timestamp NULL DEFAULT now(); -- Timestamp richiesta    
UPDATE common.utente_attribute
    SET create_time = now(); -- Timestamp richiesta    
