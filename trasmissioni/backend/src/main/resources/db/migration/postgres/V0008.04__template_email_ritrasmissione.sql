INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('INVIO_RITRASMISSIONE'			   	  , 'INVIO_RITRASMISSIONE', 'Ritrasmissione Provvedimento',
	'Ritrasmissione autorizzazione paesaggistica {CODICE_FASCICOLO} - {COMUNE} ', 
	'<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato una nuova trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong> che aggiorna la precedente.</p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p><p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email		   (codice, nome, descrizione, oggetto, testo) VALUES ('INVIO_RITRASMISSIONE'			   	  , 'INVIO_RITRASMISSIONE', 'Ritrasmissione Provvedimento',
	'Ritrasmissione autorizzazione paesaggistica {CODICE_FASCICOLO} - {COMUNE} ', 
	'<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato una nuova trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong> che aggiorna la precedente.</p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p><p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);

update template_email 		  set nome = REPLACE(nome, '_', ' ');
update template_email_default set nome = REPLACE(nome, '_', ' ');

DELETE FROM template_destinatario;
DELETE FROM template_destinatario_default;

ALTER SEQUENCE template_destinatario_default_id_seq RESTART WITH 1;
ALTER SEQUENCE template_destinatario_id_seq			RESTART WITH 1;

INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('INVIO_TRASMISSIONE'				, 'test_trasmissioni_regione_puglia_trasmissioni@sendSpamHere.com' , 'Regione Puglia - Ufficio Trasmissioni' , 'CC');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('INVIO_RITRASMISSIONE'		    , 'test_trasmissioni_regione_puglia_trasmissioni@sendSpamHere.com' , 'Regione Puglia - Ufficio Trasmissioni' , 'CC');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE' , 'test_trasmissioni_regione_puglia_trasmissioni@sendSpamHere.com' , 'Regione Puglia - Ufficio Trasmissioni' , 'CC');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'test_trasmissioni_amministratore@sendSpamHere.com'	  		   , 'Amministratore'	 					 , 'CC');
INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'test_trasmissioni_operatore_regionale@sendSpamHere.com'		   , 'Operatore Regionale'					 , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'		 	, 'test_trasmissioni_amministratore@sendSpamHere.com'	  		   , 'Amministratore'	 					 , 'CC');
INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'		 	, 'test_trasmissioni_operatore_regionale@sendSpamHere.com'		   , 'Operatore Regionale'					 , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('ESITO_CAMPIONAMENTO'			, 'test_trasmissioni_regione_puglia_campionamenti@sendSpamHere.com', 'Regione Puglia - Ufficio Campionamenti', 'TO');

INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('INVIO_TRASMISSIONE'				, 'test_trasmissioni_regione_puglia_trasmissioni@sendSpamHere.com' , 'Regione Puglia - Ufficio Trasmissioni' , 'CC');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('INVIO_RITRASMISSIONE'		    , 'test_trasmissioni_regione_puglia_trasmissioni@sendSpamHere.com' , 'Regione Puglia - Ufficio Trasmissioni' , 'CC');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE' , 'test_trasmissioni_regione_puglia_trasmissioni@sendSpamHere.com' , 'Regione Puglia - Ufficio Trasmissioni' , 'CC');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'test_trasmissioni_amministratore@sendSpamHere.com'	  		   , 'Amministratore'	 					 , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'test_trasmissioni_operatore_regionale@sendSpamHere.com'		   , 'Operatore Regionale'					 , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'		 	, 'test_trasmissioni_amministratore@sendSpamHere.com'	  		   , 'Amministratore'	 					 , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'		 	, 'test_trasmissioni_operatore_regionale@sendSpamHere.com'		   , 'Operatore Regionale'					 , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('ESITO_CAMPIONAMENTO'			, 'test_trasmissioni_regione_puglia_campionamenti@sendSpamHere.com', 'Regione Puglia - Ufficio Campionamenti', 'TO');
