ALTER TABLE configurazione_pec ALTER COLUMN pec_indirizzo TYPE varchar(255) USING pec_indirizzo::varchar;
ALTER TABLE configurazione_pec ALTER COLUMN pec_username TYPE varchar(255) USING pec_username::varchar;
ALTER TABLE configurazione_pec ALTER COLUMN pec_host_in TYPE varchar(255) USING pec_host_in::varchar;
ALTER TABLE configurazione_pec ALTER COLUMN pec_host_out TYPE varchar(255) USING pec_host_out::varchar;
ALTER TABLE configurazione_casella_postale ALTER COLUMN configurazione TYPE varchar(600) USING configurazione::varchar;

