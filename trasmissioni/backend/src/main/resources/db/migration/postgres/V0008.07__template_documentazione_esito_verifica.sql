insert into template_doc(nome, descrizione)	
	values ('LETTERA_TRASMISSIONE_ESITO_VERIFICA', 'Lettera di trasmissione esito verifica');

insert into template_doc_sezioni_default(nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	values ('Intestazione','LETTERA_TRASMISSIONE_ESITO_VERIFICA', 'Servizio Osservatorio e Pianificazione Paesaggistica', null, 'HTML'),
		   ('Oggetto','LETTERA_TRASMISSIONE_ESITO_VERIFICA', 'Comunicazione esito verifica effettuato il {DATA_PROVVEDIMENTO_ESITO_VERIFICA} sulla pratica con codice {CODICE_FASCICOLO}.', null, 'TEXT'),
		   ('Esito','LETTERA_TRASMISSIONE_ESITO_VERIFICA', 'A valle dell''analisi effettuata, l'' esito risulta essere: {ESITO_VERIFICA} con provvedimento {NUMERO_PROVVEDIMENTO_ESITO_VERIFICA} emesso in data {DATA_PROVVEDIMENTO_ESITO_VERIFICA}.', null, 'HTML'),
	 	   ('FirmaSup','LETTERA_TRASMISSIONE_ESITO_VERIFICA', 'LA DIRIGENTE AD INTERIM DEL SERVIZIO OSSERVATORIO E PIANIFICAZIONE PAESAGGISTICA', null, 'HTML'),
	 	   ('Firma','LETTERA_TRASMISSIONE_ESITO_VERIFICA', '(Ing. Barbara LOCONSOLE)', null, 'HTML'),
		   ('Logo','LETTERA_TRASMISSIONE_ESITO_VERIFICA', '', null, 'IMAGE');
--in corrispondenza di questa riga viene inserito un file nel classpath /jasper/image_template_default/LETTERA_TRASMISSIONE_Logo.png	
INSERT INTO template_doc_sezioni SELECT * FROM template_doc_sezioni_default where template_doc_nome = 'LETTERA_TRASMISSIONE_ESITO_VERIFICA'; 
	
INSERT INTO placeholder_doc(codice, info) 
	values ('ESITO_VERIFICA', 'Esito della verifica'),
		   ('NUMERO_PROVVEDIMENTO_ESITO_VERIFICA', 'Numero provvedimento'),
		   ('DATA_PROVVEDIMENTO_ESITO_VERIFICA', 'Data emissione provvedimento');	

INSERT INTO placeholder_doc_sezione(placeholder_codice, template_doc_sezione_nome, template_doc_nome)
	VALUES ('OGGETTO', 'Oggetto','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('ID_FASCICOLO', 'Oggetto','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('COMUNE', 'Oggetto','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('CODICE_FASCICOLO', 'Oggetto','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('TIPO_PROCEDIMENTO', 'Oggetto','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('DATA_PRESENTAZIONE', 'Oggetto','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('ESITO_VERIFICA', 'Oggetto','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('NUMERO_PROVVEDIMENTO_ESITO_VERIFICA', 'Oggetto','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('DATA_PROVVEDIMENTO_ESITO_VERIFICA', 'Oggetto','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('CODICE_FASCICOLO', 'Esito','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('ESITO_VERIFICA', 'Esito','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('NUMERO_PROVVEDIMENTO_ESITO_VERIFICA', 'Esito','LETTERA_TRASMISSIONE_ESITO_VERIFICA' ),
	 	   ('DATA_PROVVEDIMENTO_ESITO_VERIFICA', 'Esito','LETTERA_TRASMISSIONE_ESITO_VERIFICA' );	
	 	   