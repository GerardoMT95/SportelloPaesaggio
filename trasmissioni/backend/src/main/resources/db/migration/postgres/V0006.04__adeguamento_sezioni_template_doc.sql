INSERT INTO template_doc_sezioni_default(
	nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	VALUES ('FirmaSup','DocumentoDiTrasmissione', 'LA DIRIGENTE AD INTERIM DEL SERVIZIO OSSERVATORIO E PIANIFICAZIONE PAESAGGISTICA', null, 'HTML');
INSERT INTO template_doc_sezioni(
	nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	VALUES ('FirmaSup','DocumentoDiTrasmissione', 'LA DIRIGENTE AD INTERIM DEL SERVIZIO OSSERVATORIO E PIANIFICAZIONE PAESAGGISTICA', null, 'HTML');	
UPDATE template_doc_sezioni_default SET
	 valore='(Ing. Barbara LOCONSOLE)'
	WHERE nome='Firma' and template_doc_nome='DocumentoDiTrasmissione';	
UPDATE template_doc_sezioni SET
	 valore='(Ing. Barbara LOCONSOLE)'
	WHERE nome='Firma' and template_doc_nome='DocumentoDiTrasmissione';	
UPDATE template_doc_sezioni_default SET
	 valore='Servizio Osservatorio e Pianificazione Paesaggistica'
	WHERE nome='Intestazione' and template_doc_nome='DocumentoDiTrasmissione';	
UPDATE template_doc_sezioni SET
	 valore='Servizio Osservatorio e Pianificazione Paesaggistica'
	WHERE nome='Intestazione' and template_doc_nome='DocumentoDiTrasmissione';	
UPDATE template_doc_sezioni_default SET
	valore='Ricezione dei dati riguardanti il Provvedimento Paesaggistico avente ad oggetto:<br>- {OGGETTO} (cod. pratica {CODICE_FASCICOLO} )<br>- Tipo Procedimento: {TIPO_PROCEDIMENTO}'
	WHERE nome='Oggetto' and template_doc_nome='DocumentoDiTrasmissione';	
	
UPDATE template_doc_sezioni SET
	valore='Ricezione dei dati riguardanti il Provvedimento Paesaggistico avente ad oggetto:<br>- {OGGETTO} (cod. pratica {CODICE_FASCICOLO} )<br>- Tipo Procedimento: {TIPO_PROCEDIMENTO}'
	WHERE nome='Oggetto' and template_doc_nome='DocumentoDiTrasmissione';	
INSERT INTO placeholder_doc(codice, info)VALUES ('CODICE_FASCICOLO', 'codice pratica');	
INSERT INTO placeholder_doc(codice, info)VALUES ('TIPO_PROCEDIMENTO', 'tipo procedimento PPTR');


INSERT INTO placeholder_doc_sezione(	placeholder_codice, template_doc_sezione_nome, template_doc_nome)
	VALUES ('CODICE_FASCICOLO', 'Oggetto','DocumentoDiTrasmissione' );
INSERT INTO placeholder_doc_sezione(	placeholder_codice, template_doc_sezione_nome, template_doc_nome)
	VALUES ('TIPO_PROCEDIMENTO', 'Oggetto','DocumentoDiTrasmissione' );	
