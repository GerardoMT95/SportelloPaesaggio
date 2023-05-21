INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile,riservata)
	VALUES (0,'RIC_INTEGR_IST','Conferma ricezione integrazione istanza','Conferma ricezione integrazione istanza','Conferma ricezione integrazione istanza {CODICE_FASCICOLO}','Si comunica che in data 
{DATA_INTEGRAZIONE} è stata ricevuta documentazione aggiuntiva per la pratica con identificativo:<br>
{CODICE_FASCICOLO}
<br>
<br>
<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>','S','N',',ED_,REG_,','RIC_INTEGR_IST','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA,DATA_INTEGRAZIONE',false,false);

INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile,riservata)
	VALUES (0,'RICH_INTEGRAZIONE','Richiesta integrazione al richiendente','Richiesta integrazione al richiendente','Richiesta integrazione documentale istanza {CODICE_FASCICOLO}','In merito al corretto svolgimento dell''istruttoria dell''istanza con identificativo:<br>
{CODICE_FASCICOLO} <br>
si richiede la seguente documentazione:<br> ......  <br>
<br>
<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>','S','N',',ED_,REG_,','RICH_INTEGRAZIONE','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false,false);


