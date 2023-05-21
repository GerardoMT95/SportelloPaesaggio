
INSERT INTO template_email 		  (codice, nome, descrizione, oggetto, testo) VALUES ('INVIO_TRASMISSIONE'			  	  , 'nome 1', 'descrizione 1', 'oggetto 1', 'testo 1' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('INVIO_COMUNICAZIONI'			  	  , 'nome 2', 'descrizione 2', 'oggetto 2', 'testo 2' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('INVIO_ULTERIORE_DOCUMENTAZIONE'	  , 'nome 3', 'descrizione 3', 'oggetto 3', 'testo 3' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('ESITO_CAMPIONAMENTO'			  	  , 'nome 4', 'descrizione 4', 'oggetto 4', 'testo 4' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('ESITO_VERIFICA'     			  	  , 'nome 5', 'descrizione 5', 'oggetto 5', 'testo 5' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('MODIFICA_POST_TRASMISSIONE'    	  , 'nome 6', 'descrizione 6', 'oggetto 6', 'testo 6' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('REVOCA_MODIFICA_POST_TRASMISSIONE' , 'nome 6', 'descrizione 6', 'oggetto 6', 'testo 6' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE'	  , 'nome 7', 'descrizione 7', 'oggetto 7', 'testo 7' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'    	  , 'nome 8', 'descrizione 8', 'oggetto 8', 'testo 8' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('NOTIFICA_PRE_VERIFICA'		  	  , 'nome 9', 'descrizione 9', 'oggetto 9', 'testo 9' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('ASSEGNAMENTO_FASCICOLO'		  	  , 'nome 0', 'descrizione 0', 'oggetto 0', 'testo 0' );
INSERT INTO template_email		  (codice, nome, descrizione, oggetto, testo) VALUES ('DISASSEGNAMENTO_FASCICOLO'		  , 'nome 1', 'descrizione 1', 'oggetto 1', 'testo 1' );


INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('INVIO_TRASMISSIONE'			   	  , 'nome 1', 'descrizione 1', 'oggetto 1', 'testo 1' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('INVIO_COMUNICAZIONI'		   	  , 'nome 2', 'descrizione 2', 'oggetto 2', 'testo 2' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('INVIO_ULTERIORE_DOCUMENTAZIONE'	  , 'nome 3', 'descrizione 3', 'oggetto 3', 'testo 3' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('ESITO_CAMPIONAMENTO'		   	  , 'nome 4', 'descrizione 4', 'oggetto 4', 'testo 4' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('ESITO_VERIFICA'     		   	  , 'nome 5', 'descrizione 5', 'oggetto 5', 'testo 5' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('MODIFICA_POST_TRASMISSIONE'    	  , 'nome 6', 'descrizione 6', 'oggetto 6', 'testo 6' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('REVOCA_MODIFICA_POST_TRASMISSIONE', 'nome 6', 'descrizione 6', 'oggetto 6', 'testo 6' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE'	  , 'nome 7', 'descrizione 7', 'oggetto 7', 'testo 7' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'	   	  , 'nome 8', 'descrizione 8', 'oggetto 8', 'testo 8' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('NOTIFICA_PRE_VERIFICA'		   	  , 'nome 9', 'descrizione 9', 'oggetto 9', 'testo 9' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('ASSEGNAMENTO_FASCICOLO'		   	  , 'nome 0', 'descrizione 0', 'oggetto 0', 'testo 0' );
INSERT INTO template_email_default (codice, nome, descrizione, oggetto, testo) VALUES ('DISASSEGNAMENTO_FASCICOLO'		  , 'nome 1', 'descrizione 1', 'oggetto 1', 'testo 1' );


INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('INVIO_TRASMISSIONE'				, 'email primo   destinatario di INVIO_TRASMISSIONE'			, 'denominazione primo   destinatario di INVIO_TRASMISSIONE'			  , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('INVIO_TRASMISSIONE'				, 'pec   secondo destinatario di INVIO_TRASMISSIONE'			, 'denominazione secondo destinatario di INVIO_TRASMISSIONE'			  , 'CC');
INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('INVIO_COMUNICAZIONI'			, 'email primo   destinatario di INVIO_COMUNICAZIONI'			, 'denominazione primo   destinatario di INVIO_COMUNICAZIONI'			  , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('INVIO_COMUNICAZIONI'			, 'pec   secondo destinatario di INVIO_COMUNICAZIONI'			, 'denominazione secondo destinatario di INVIO_COMUNICAZIONI'			  , 'CC');
INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('INVIO_ULTERIORE_DOCUMENTAZIONE' , 'email primo   destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE', 'denominazione primo   destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE'  , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('INVIO_ULTERIORE_DOCUMENTAZIONE' , 'pec   secondo destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE', 'denominazione secondo destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE'  , 'CC');
INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('ESITO_CAMPIONAMENTO'			, 'email primo   destinatario di ESITO_CAMPIONAMENTO'			, 'denominazione primo   destinatario di ESITO_CAMPIONAMENTO'			  , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('ESITO_CAMPIONAMENTO'			, 'pec   secondo destinatario di ESITO_CAMPIONAMENTO'			, 'denominazione secondo destinatario di ESITO_CAMPIONAMENTO'			  , 'CC');
INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('ESITO_VERIFICA'					, 'email primo   destinatario di ESITO_VERIFICA'				, 'denominazione primo   destinatario di ESITO_VERIFICA'				  , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('ESITO_VERIFICA'					, 'pec   secondo destinatario di ESITO_VERIFICA'				, 'denominazione secondo destinatario di ESITO_VERIFICA'				  , 'CC');
INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('MODIFICA_POST_TRASMISSIONE'     , 'email primo   destinatario di MODIFICA_POST_TRASMISSIONE'	, 'denominazione primo   destinatario di MODIFICA_POST_TRASMISSIONE'	  , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('MODIFICA_POST_TRASMISSIONE'     , 'pec   secondo destinatario di MODIFICA_POST_TRASMISSIONE'    , 'denominazione secondo destinatario di MODIFICA_POST_TRASMISSIONE'	  , 'CC');
INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE' , 'email primo   destinatario di ELIMINAZIONE_POST_TRASMISSIONE', 'denominazione primo   destinatario di ELIMINAZIONE_POST_TRASMISSIONE'  , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE' , 'pec   secondo destinatario di ELIMINAZIONE_POST_TRASMISSIONE', 'denominazione secondo destinatario di ELIMINAZIONE_POST_TRASMISSIONE'  , 'CC');
INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'email primo   destinatario di NOTIFICA_PRE_CAMPIONAMENTO'    , 'denominazione primo   destinatario di NOTIFICA_PRE_CAMPIONAMENTO'	  , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'pec   secondo destinatario di NOTIFICA_PRE_CAMPIONAMENTO'    , 'denominazione secondo destinatario di NOTIFICA_PRE_CAMPIONAMENTO'	  , 'CC');
INSERT INTO template_destinatario		 (codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'			, 'email primo   destinatario di NOTIFICA_PRE_VERIFICA'         , 'denominazione primo   destinatario di NOTIFICA_PRE_VERIFICA'		  	  , 'TO');
INSERT INTO template_destinatario		 (codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'			, 'pec   secondo destinatario di NOTIFICA_PRE_VERIFICA'			, 'denominazione secondo destinatario di NOTIFICA_PRE_VERIFICA'		  	  , 'CC');


INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('INVIO_TRASMISSIONE'				, 'email primo   destinatario di INVIO_TRASMISSIONE'			, 'denominazione primo   destinatario di INVIO_TRASMISSIONE'			  , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('INVIO_TRASMISSIONE'				, 'pec   secondo destinatario di INVIO_TRASMISSIONE'			, 'denominazione secondo destinatario di INVIO_TRASMISSIONE'			  , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('INVIO_COMUNICAZIONI'			, 'email primo   destinatario di INVIO_COMUNICAZIONI'			, 'denominazione primo   destinatario di INVIO_COMUNICAZIONI'			  , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('INVIO_COMUNICAZIONI'			, 'pec   secondo destinatario di INVIO_COMUNICAZIONI'			, 'denominazione secondo destinatario di INVIO_COMUNICAZIONI'			  , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('INVIO_ULTERIORE_DOCUMENTAZIONE' , 'email primo   destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE', 'denominazione primo   destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE'  , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('INVIO_ULTERIORE_DOCUMENTAZIONE' , 'pec   secondo destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE', 'denominazione secondo destinatario di INVIO_ULTERIORE_DOCUMENTAZIONE'  , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('ESITO_CAMPIONAMENTO'			, 'email primo   destinatario di ESITO_CAMPIONAMENTO'			, 'denominazione primo   destinatario di ESITO_CAMPIONAMENTO'			  , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('ESITO_CAMPIONAMENTO'			, 'pec   secondo destinatario di ESITO_CAMPIONAMENTO'			, 'denominazione secondo destinatario di ESITO_CAMPIONAMENTO'			  , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('ESITO_VERIFICA'					, 'email primo   destinatario di ESITO_VERIFICA'				, 'denominazione primo   destinatario di ESITO_VERIFICA'				  , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('ESITO_VERIFICA'					, 'pec   secondo destinatario di ESITO_VERIFICA'				, 'denominazione secondo destinatario di ESITO_VERIFICA'				  , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('MODIFICA_POST_TRASMISSIONE'     , 'email primo   destinatario di MODIFICA_POST_TRASMISSIONE'	, 'denominazione primo   destinatario di MODIFICA_POST_TRASMISSIONE'	  , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('MODIFICA_POST_TRASMISSIONE'     , 'pec   secondo destinatario di MODIFICA_POST_TRASMISSIONE'    , 'denominazione secondo destinatario di MODIFICA_POST_TRASMISSIONE'	  , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE' , 'email primo   destinatario di ELIMINAZIONE_POST_TRASMISSIONE', 'denominazione primo   destinatario di ELIMINAZIONE_POST_TRASMISSIONE'  , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('ELIMINAZIONE_POST_TRASMISSIONE' , 'pec   secondo destinatario di ELIMINAZIONE_POST_TRASMISSIONE', 'denominazione secondo destinatario di ELIMINAZIONE_POST_TRASMISSIONE'  , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'email primo   destinatario di NOTIFICA_PRE_CAMPIONAMENTO'    , 'denominazione primo   destinatario di NOTIFICA_PRE_CAMPIONAMENTO'	  , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_CAMPIONAMENTO'		, 'pec   secondo destinatario di NOTIFICA_PRE_CAMPIONAMENTO'    , 'denominazione secondo destinatario di NOTIFICA_PRE_CAMPIONAMENTO'	  , 'CC');
INSERT INTO template_destinatario_default(codice_template, email, denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'		 	, 'email primo   destinatario di NOTIFICA_PRE_VERIFICA'         , 'denominazione primo   destinatario di NOTIFICA_PRE_VERIFICA'		  	  , 'TO');
INSERT INTO template_destinatario_default(codice_template, pec  , denominazione, tipo) VALUES ('NOTIFICA_PRE_VERIFICA'		 	, 'pec   secondo destinatario di NOTIFICA_PRE_VERIFICA'			, 'denominazione secondo destinatario di NOTIFICA_PRE_VERIFICA'		  	  , 'CC');

