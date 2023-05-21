INSERT INTO template_email_default
(codice, nome, descrizione, oggetto, testo)
VALUES('RICHIESTA_MODIFICA_POST_TRASMISSIONE', 'RICHIESTA DI MODIFICA POST TRASMISSIONE', 
	'Richiesta di modifica post trasmissione', 
	'Richiesta di modifica post trasmissione autorizzazione paesaggistica {CODICE_FASCICOLO} - {COMUNE} ', 
	'<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato una nuova richiesta di modifica post trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong></p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p>
<p><strong>Motivo richiesta:</strong><br>{MOTIVAZIONE}</p>
<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>');

INSERT INTO template_email_default
(codice, nome, descrizione, oggetto, testo)
VALUES('RICHIESTA_ELIMINAZIONE_POST_TRASMISSIONE', 'RICHIESTA DI ELIMINAZIONE POST TRASMISSIONE', 
	'Richiesta di eliminazione post trasmissione', 
	'Richiesta di eliminazione post trasmissione autorizzazione paesaggistica {CODICE_FASCICOLO} - {COMUNE} ', 
	'<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato una nuova richiesta di eliminazione post trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong></p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p>
<p><strong>Motivo richiesta:</strong><br>{MOTIVAZIONE}</p>
<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>');



INSERT INTO template_email
(codice, nome, descrizione, oggetto, testo)
VALUES('RICHIESTA_MODIFICA_POST_TRASMISSIONE', 'RICHIESTA DI MODIFICA POST TRASMISSIONE', 
	'Richiesta di modifica post trasmissione', 
	'Richiesta di modifica post trasmissione autorizzazione paesaggistica {CODICE_FASCICOLO} - {COMUNE} ', 
	'<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato una nuova richiesta di modifica post trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong></p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p>
<p><strong>Motivo richiesta:</strong><br>{MOTIVAZIONE}</p>
<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>');

INSERT INTO template_email
(codice, nome, descrizione, oggetto, testo)
VALUES('RICHIESTA_ELIMINAZIONE_POST_TRASMISSIONE', 'RICHIESTA DI ELIMINAZIONE POST TRASMISSIONE', 
	'Richiesta di eliminazione post trasmissione', 
	'Richiesta di eliminazione post trasmissione autorizzazione paesaggistica {CODICE_FASCICOLO} - {COMUNE} ', 
	'<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato una nuova richiesta di eliminazione post trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong></p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p>
<p><strong>Motivo richiesta:</strong><br>{MOTIVAZIONE}</p>
<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>');    