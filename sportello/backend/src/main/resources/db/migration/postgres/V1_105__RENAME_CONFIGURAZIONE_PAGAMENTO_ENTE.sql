ALTER TABLE presentazione_istanza.configurazioni_ente RENAME COLUMN pagamento_intestatario TO pagamento_tipo_riscossione;
ALTER TABLE presentazione_istanza.configurazioni_ente RENAME COLUMN pagamento_iban TO pagamento_cod_ente;
ALTER TABLE presentazione_istanza.configurazioni_ente RENAME COLUMN pagamento_causale TO pagamento_tipologia;

ALTER TABLE presentazione_istanza.configurazioni_ente ADD pagamento_commissione float8 NOT NULL DEFAULT 0;
ALTER TABLE presentazione_istanza.configurazioni_ente ADD pagamento_regex_iud varchar(400);
    