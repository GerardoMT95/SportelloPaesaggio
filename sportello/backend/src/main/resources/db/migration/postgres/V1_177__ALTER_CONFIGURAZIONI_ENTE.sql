ALTER TABLE presentazione_istanza.configurazioni_ente ADD COLUMN indirizzi_mail_default varchar(1000) ;

comment ON COLUMN configurazioni_ente.indirizzi_mail_default IS 'Indirizzi mail di default';