update "presentazione_istanza"."template_email"
set "placeholders"='CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA,URL_DOWNLOAD_RICEVUTA_PROTETTO' ,
	"testo" = '<p>Si comunica che, con la presente PEC, l’Ente Delegato Associazione  Ugento, Presicce, Acquarica, Taurisano ha effettuato la trasmissione della pratica <strong>APPPTR-5-2020</strong></p><p>&nbsp;</p><ul><li>tipologia di procedura: ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA Art. 167 e 181, D. Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)</li><li>richiedente: qwefwefwef awdwqd</li><li>oggetto dell’intervento: TEST 2</li><li>comune/i di intervento: Comune di Acquarica del Capo,Comune di Presicce,Comune di Taurisano,Comune di Ugento</li><li>data rilascio: 15/10/2020</li></ul><p><a href="http://localhost:8080/paesaggio-presentazione-istanza/public/downloadDocumentoDaMail/ricevutaTrasmissione?idPratica=7a6834f2-f80a-4c10-8b04-94915e5f6acf">Clicca qui</a> per scaricare il documento di trasmissione</p><p><br></p><p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).</p>'
where "codice" = 'INVIO_TRASMISSIONE';