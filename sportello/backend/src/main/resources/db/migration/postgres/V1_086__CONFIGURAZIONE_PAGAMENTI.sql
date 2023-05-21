CREATE TABLE presentazione_istanza.pagamenti_mypay (
	iud varchar(35) NOT NULL,
	pratica_id uuid NOT NULL,
	ente_id varchar(20) NOT NULL,
	causale varchar(1024) NULL,
	cod_ipa_ente varchar(50) NULL,
	cf_pagatore varchar(20) NULL,
	email_pagatore varchar(255) NULL,
	ret_url varchar(1000) NULL, -- url di ritorno su FE alla pagina dei pagamenti
	sogg_paga varchar(70) NULL,
	tipologia varchar(64) NULL,
	tipo_riscossione varchar(140) NULL,
	stato varchar(20) NULL,
	data_inserimento timestamp NULL DEFAULT now(),
	data_modifica timestamp NULL DEFAULT now(),
	importo numeric(16,2) NULL,
	idsession varchar(255) NULL,
	esito varchar(255) NULL,
	error varchar(255) NULL,
	url_to_pay varchar(1000) NULL, -- url restituita da client inviaDovuti per redirigere l'utente alla cassa e pagare
	cfg_endpoint_mypay varchar(1000) NULL,
	cfg_password_mypay varchar(255) NULL,
	CONSTRAINT pagamenti_mypay_pkey PRIMARY KEY (iud)
);
CREATE UNIQUE INDEX pagamenti_mypay_pratica_id_idx ON presentazione_istanza.pagamenti_mypay USING btree (pratica_id, iud);

-- Column comments

COMMENT ON COLUMN presentazione_istanza.pagamenti_mypay.ret_url IS 'url di ritorno su FE alla pagina dei pagamenti';
COMMENT ON COLUMN presentazione_istanza.pagamenti_mypay.url_to_pay IS 'url restituita da client inviaDovuti per redirigere l''utente alla cassa e pagare';

-- Permissions

ALTER TABLE presentazione_istanza.pagamenti_mypay OWNER TO presentazione_istanza;
GRANT ALL ON TABLE presentazione_istanza.pagamenti_mypay TO presentazione_istanza;

COMMENT ON COLUMN presentazione_istanza.configurazioni_ente.pagamento_intestatario IS 'corrisponde a tipoRiscossione su bean mypay es. 9/ONERI_ATTIVITA_ESTRATTIVE';
COMMENT ON COLUMN presentazione_istanza.configurazioni_ente.pagamento_iban IS 'corrisponde a codEnte ovvero codice ipa ente es: R_PUGLIA';
COMMENT ON COLUMN presentazione_istanza.configurazioni_ente.pagamento_causale IS 'corrisponde a tipologia su bean mypay es.: ONERI_ATTIVITA_ESTRATTIVE';

ALTER TABLE presentazione_istanza.configurazioni_ente ADD pagamento_password varchar(255) NULL;
ALTER TABLE presentazione_istanza.configurazioni_ente ADD pagamento_endpoint varchar(1000) NULL;


