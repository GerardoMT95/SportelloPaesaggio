DELETE FROM template_destinatario_default;
DELETE FROM template_destinatario;
DELETE FROM template_email_default;
DELETE FROM template_email;

INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('INVIO_TRASMISSIONE'			   	  , 'INVIO_TRASMISSIONE', 'Trasmissione Provvedimento',
	'Trasmissione autorizzazione paesaggistica {CODICE_FASCICOLO} - {COMUNE} ', 
	'<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato la trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong></p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p><p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('INVIO_ULTERIORE_DOCUMENTAZIONE'	  , 'INVIO_ULTERIORE_DOCUMENTAZIONE', 'Notifica ulteriore documentazione', 
	'Notifica aggiunta di ulteriore documentazione alla pratica {CODICE_FASCICOLO}', 
	'<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p><ul><li>Nome file: {NOME_FILE_ULTERIORE_DOC} (<a href="{LINK_FILE_ULTERIORE_DOC}" rel="noopener noreferrer" target="_blank">clicca qui</a>)</li><li>Titolo: {TITOLO_FILE_ULTERIORE_DOC}</li><li>Descrizione: {DESCRIZIONE_FILE_ULTERIORE_DOC}</li></ul><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('ESITO_CAMPIONAMENTO'		   	  , 'ESITO_CAMPIONAMENTO', 'Comunicazione Fascicoli Selezionati', 
	'Verifiche a campione sulle pratiche trasmesse', 
	'<p>Si trasmette in allegato la nota prot. della Sezione Regionale Autorizzazioni Paesaggistiche {PROTOCOLLO_SET_CAMPIONAMENTO} del {DATA_FINE_CAMPIONAMENTO}, recante la definizione del campione di Autorizzazioni Paesaggistiche registrate nel periodo dal {DATA_INIZIO_CAMPIONAMENTO} al {DATA_FINE_CAMPIONAMENTO} sottoposte alle verifiche opportune.</p><p></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('ESITO_VERIFICA'     		   	  , 'ESITO_VERIFICA', 'Notifica esito verifica', 
	'Esito verifica a campione pratica {CODICE_FASCICOLO}', 
	'<p>Si trasmette in allegato il provvedimento conclusivo della verifica effettuata sulla pratica in oggetto. </p><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('MODIFICA_POST_TRASMISSIONE'    	  , 'MODIFICA_POST_TRASMISSIONE', 'Notifica attivazione modifica post trasmissione', 
	'Attivazione modifica post trasmissione pratica {CODICE_FASCICOLO}', 
	'<p>Si comunica l’attivazione della modifica post trasmissione della pratica in oggetto, si ricorda che tale funzionalità risulta attiva fino a {DATA_SCADENZA_MODIFICHE}. </p><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('REVOCA_MODIFICA_POST_TRASMISSIONE', 'REVOCA_MODIFICA_POST_TRASMISSIONE', 'Notifica revoca modifica post trasmissione', 
	'Revoca modifica post trasmissione pratica {CODICE_FASCICOLO}', 
	'<p>Si comunica la revoca della modifica post trasmissione della pratica in oggetto. </p><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE'	  , 'ELIMINAZIONE_POST_TRASMISSIONE', 'Notifica eliminazione post trasmissione', 
	'Eliminazione post trasmissione pratica {CODICE_FASCICOLO}', 
	'<p>Si comunica che la pratica in oggetto è stata cancellata. </p><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'	   	  , 'NOTIFICA_PRE_CAMPIONAMENTO', 'Notifica pre campionamento', 
	'Notifica prossima data di campionamento', 
	'<p>Si notifica che il prossimo campionamento per la selezione delle autorizzazioni paesaggistiche trasmesse è fissato in data {DATA_FINE_CAMPIONAMENTO} </p><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('NOTIFICA_PRE_VERIFICA'		   	  , 'NOTIFICA_PRE_VERIFICA', 'Notifica scadenza termini verifica', 
	'Notifica scadenza termini di verifica', 
	'<p>Si notifica che la scadenza dei termini di verifica è fissata in data {DATA_FINE_VERIFICA}. </p><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('ASSEGNAMENTO_FASCICOLO'		   	  , 'ASSEGNAMENTO_FASCICOLO', 'Assegnazione fascicolo', 
	'Notifica di assegnazione pratica {CODICE_FASCICOLO}', 
	'<p>La pratica in oggetto è stata assegnata all’utente {NOME_COGNOME_USER_ASSEGNATARIO}. </p><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('DISASSEGNAMENTO_FASCICOLO'		  , 'DISASSEGNAMENTO_FASCICOLO', 'Revoca assegnazione fascicolo', 
	'Notifica di revoca assegnazione pratica {CODICE_FASCICOLO}', 
	'<p>La pratica in oggetto NON risulta più assegnata all’utente {NOME_COGNOME_USER_ASSEGNATARIO}. </p><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
);

INSERT INTO template_email SELECT * FROM template_email_default;


INSERT INTO template_destinatario(codice_template, email, denominazione, tipo) VALUES ('INVIO_TRASMISSIONE'				, 'email primo   destinatario di INVIO_TRASMISSIONE'			, 'denominazione primo   destinatario di INVIO_TRASMISSIONE'			  , 'TO');
INSERT INTO template_destinatario(codice_template, pec  , denominazione, tipo) VALUES ('INVIO_TRASMISSIONE'				, 'pec   secondo destinatario di INVIO_TRASMISSIONE'			, 'denominazione secondo destinatario di INVIO_TRASMISSIONE'			  , 'CC');
INSERT INTO template_destinatario(codice_template, email, denominazione, tipo) VALUES ('INVIO_ULTERIORE_DOCUMENTAZIONE' , 'email primo   destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE', 'denominazione primo   destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE'  , 'TO');
INSERT INTO template_destinatario(codice_template, pec  , denominazione, tipo) VALUES ('INVIO_ULTERIORE_DOCUMENTAZIONE' , 'pec   secondo destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE', 'denominazione secondo destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE'  , 'CC');
INSERT INTO template_destinatario(codice_template, email, denominazione, tipo) VALUES ('ESITO_CAMPIONAMENTO'			, 'email primo   destinatario di ESITO_CAMPIONAMENTO'			, 'denominazione primo   destinatario di ESITO_CAMPIONAMENTO'			  , 'TO');
INSERT INTO template_destinatario(codice_template, pec  , denominazione, tipo) VALUES ('ESITO_CAMPIONAMENTO'			, 'pec   secondo destinatario di ESITO_CAMPIONAMENTO'			, 'denominazione secondo destinatario di ESITO_CAMPIONAMENTO'			  , 'CC');
INSERT INTO template_destinatario(codice_template, email, denominazione, tipo) VALUES ('ESITO_VERIFICA'					, 'email primo   destinatario di ESITO_VERIFICA'				, 'denominazione primo   destinatario di ESITO_VERIFICA'				  , 'TO');
INSERT INTO template_destinatario(codice_template, pec  , denominazione, tipo) VALUES ('ESITO_VERIFICA'					, 'pec   secondo destinatario di ESITO_VERIFICA'				, 'denominazione secondo destinatario di ESITO_VERIFICA'				  , 'CC');
INSERT INTO template_destinatario(codice_template, email, denominazione, tipo) VALUES ('MODIFICA_POST_TRASMISSIONE'     , 'email primo   destinatario di MODIFICA_POST_TRASMISSIONE'	, 'denominazione primo   destinatario di MODIFICA_POST_TRASMISSIONE'	  , 'TO');
INSERT INTO template_destinatario(codice_template, pec  , denominazione, tipo) VALUES ('MODIFICA_POST_TRASMISSIONE'     , 'pec   secondo destinatario di MODIFICA_POST_TRASMISSIONE'    , 'denominazione secondo destinatario di MODIFICA_POST_TRASMISSIONE'	  , 'CC');
INSERT INTO template_destinatario(codice_template, email, denominazione, tipo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE' , 'email primo   destinatario di ELIMINAZIONE_POST_TRASMISSIONE', 'denominazione primo   destinatario di ELIMINAZIONE_POST_TRASMISSIONE'  , 'TO');
INSERT INTO template_destinatario(codice_template, pec  , denominazione, tipo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE' , 'pec   secondo destinatario di ELIMINAZIONE_POST_TRASMISSIONE', 'denominazione secondo destinatario di ELIMINAZIONE_POST_TRASMISSIONE'  , 'CC');
INSERT INTO template_destinatario(codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'email primo   destinatario di NOTIFICA_PRE_CAMPIONAMENTO'    , 'denominazione primo   destinatario di NOTIFICA_PRE_CAMPIONAMENTO'	  , 'TO');
INSERT INTO template_destinatario(codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'pec   secondo destinatario di NOTIFICA_PRE_CAMPIONAMENTO'    , 'denominazione secondo destinatario di NOTIFICA_PRE_CAMPIONAMENTO'	  , 'CC');
INSERT INTO template_destinatario(codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'		 	, 'email primo   destinatario di NOTIFICA_PRE_VERIFICA'         , 'denominazione primo   destinatario di NOTIFICA_PRE_VERIFICA'		  	  , 'TO');
INSERT INTO template_destinatario(codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'		 	, 'pec   secondo destinatario di NOTIFICA_PRE_VERIFICA'			, 'denominazione secondo destinatario di NOTIFICA_PRE_VERIFICA'		  	  , 'CC');

UPDATE template_destinatario set email='test_peo_autpae@mailinator.com'  where email like '% destinatario di%';
UPDATE template_destinatario set pec  ='test_pec_autpae@mailinator.com'  where pec   like '% destinatario di%';

INSERT INTO template_destinatario_default SELECT * FROM template_destinatario;
