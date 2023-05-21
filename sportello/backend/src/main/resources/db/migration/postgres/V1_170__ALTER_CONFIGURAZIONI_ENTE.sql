ALTER TABLE presentazione_istanza.configurazioni_ente ADD COLUMN can_create_conferenza boolean DEFAULT false;

comment ON COLUMN configurazioni_ente.can_create_conferenza IS 'Indica se è possibile creare una conferenza';