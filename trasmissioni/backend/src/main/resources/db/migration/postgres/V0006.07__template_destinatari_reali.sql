DELETE FROM template_destinatario_default;
DELETE FROM template_destinatario;

INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('INVIO_TRASMISSIONE'				, 'test_trasmissioni_regione_puglia@sendSpamHere.com'	  , 'Regione Puglia'	 , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('ESITO_CAMPIONAMENTO'			, 'test_trasmissioni_regione_puglia@sendSpamHere.com'	  , 'Regione Puglia'	 , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE' , 'test_trasmissioni_regione_puglia@sendSpamHere.com'	  , 'Regione Puglia'	 , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'test_trasmissioni_amministratore@sendSpamHere.com'	  , 'Amministratore'	 , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'test_trasmissioni_operatore_regionale@sendSpamHere.com', 'Operatore Regionale', 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'		 	, 'test_trasmissioni_amministratore@sendSpamHere.com'	  , 'Amministratore'	 , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'		 	, 'test_trasmissioni_operatore_regionale@sendSpamHere.com', 'Operatore Regionale', 'TO');

INSERT INTO template_destinatario SELECT * FROM template_destinatario_default;
