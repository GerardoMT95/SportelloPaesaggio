ALTER TABLE presentazione_istanza.configurazioni_ente ADD COLUMN bilancio varchar;

comment ON COLUMN configurazioni_ente.bilancio IS 'Configurazione capitoli bilancio';