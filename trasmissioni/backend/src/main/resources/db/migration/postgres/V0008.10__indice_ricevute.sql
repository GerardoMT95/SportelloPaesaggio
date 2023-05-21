ALTER TABLE ricevuta
    ADD CONSTRAINT index_corrispondenza UNIQUE (id_corrispondenza, id);