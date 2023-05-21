UPDATE template_email
	SET testo='<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato la trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong></p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p>
<p>
<a href="{URL_DOWNLOAD_RICEVUTA_PROTETTO}" rel="noopener noreferrer" target="_blank">Clicca qui</a> per scaricare il documento di trasmissione.</p>

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
	WHERE codice='INVIO_TRASMISSIONE';
	
UPDATE template_email
	SET testo='<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato una nuova trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong> che aggiorna la precedente.</p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p>
<p>
<a href="{URL_DOWNLOAD_RICEVUTA_PROTETTO}" rel="noopener noreferrer" target="_blank">Clicca qui</a> per scaricare il documento di trasmissione.</p>

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
	WHERE codice='INVIO_RITRASMISSIONE';
	
UPDATE template_email_default
	SET testo='<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato la trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong></p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p>
<p>
<a href="{URL_DOWNLOAD_RICEVUTA_PROTETTO}" rel="noopener noreferrer" target="_blank">Clicca qui</a> per scaricare il documento di trasmissione.</p>

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
	WHERE codice='INVIO_TRASMISSIONE';
	
UPDATE template_email_default
	SET testo='<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato una nuova trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong> che aggiorna la precedente.</p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p>
<p>
<a href="{URL_DOWNLOAD_RICEVUTA_PROTETTO}" rel="noopener noreferrer" target="_blank">Clicca qui</a> per scaricare il documento di trasmissione.</p>

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
	WHERE codice='INVIO_RITRASMISSIONE';


ALTER TABLE allegato_corrispondenza ADD is_url boolean NULL DEFAULT false;
COMMENT ON COLUMN allegato_corrispondenza.is_url IS 'se a true indica che l''allegato non va inserito nella mail, ma potrà esserci un url nel corpo della mail per raggiungerlo dall''esterno.';	
	