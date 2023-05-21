/* Template per notifica sospensione */
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile,riservata)
	VALUES (0,'NOT_SOSP','Notifica sospensione istanza','Template per la mail automatica di notifica della sospesione di una pratica','Notifica sospensione pratica - {CODICE_FASCICOLO}',
'Si comunica che la procedura con codice {CODICE_PRATICA} presentata dal sig./sig.na {NOME_COGNOME_RICHIEDENTE} è stata sospesa.<br>
Note del funzionario che ha sospeso la pratica:<br/>
<p><i>{NOTE_SOSPENSIONE}</i></p> 

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>',
'S','N','ED_','NOTIFICHE_ASR','','N','CODICE_FASCICOLO,CODICE_PRATICA,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA,NOTE_SOSPENSIONE',false,false);

/* Template per notifica archiviazione */
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile,riservata)
	VALUES (0,'NOT_ARC','Notifica archiviazione istanza','Template per la mail automatica di notifica dell''archiviazione di una pratica','Notifica archiviazione pratica - {CODICE_FASCICOLO}',
'Si comunica che la procedura con codice {CODICE_PRATICA} presentata dal sig./sig.na {NOME_COGNOME_RICHIEDENTE} è stata archiviata.<br>
Note del funzionario che ha archiviato la pratica:<br/>
<p><i>{NOTE_ARCHIVIAZIONE}</i></p> 

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>',
'S','N','ED_','NOTIFICHE_ASR','','N','CODICE_FASCICOLO,CODICE_PRATICA,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA,NOTE_ARCHIVIAZIONE',false,false);

/* Template per notifica riattivazione */
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile,riservata)
	VALUES (0,'NOT_ATT','Notifica riattivazione istanza','Template per la mail automatica di notifica della riattivazione di una pratica','Notifica riattivazione pratica - {CODICE_FASCICOLO}',
'Si comunica che la procedura con codice {CODICE_PRATICA} presentata dal sig./sig.na {NOME_COGNOME_RICHIEDENTE} è stata riattivata.<br>
Note del funzionario che ha riattivato la pratica:<br/>
<p><i>{NOTE_RIATTIVAZIONE}</i></p> 

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>',
'S','N','ED_','NOTIFICHE_ASR','','N','CODICE_FASCICOLO,CODICE_PRATICA,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA,NOTE_RIATTIVAZIONE',false,false);

/* Template per richiesta archiviazione */
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile,riservata)
	VALUES (0,'REQ_ARC','Richiesta arhiviazione pratica','Template per la mail di richiesta di archiviazione per una pratica','Richiesta archiviazione pratica - {CODICE_FASCICOLO}',
'Il sottoscritto {NOME_FUNZIONARIO} {COGNOME_FUNZIONARIO} richiede l''archiviazione per la pratica con codice {CODICE_PRATICA} presentata dal sig./sig.na {NOME_COGNOME_RICHIEDENTE}.<br>

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>',
'S','N','ED_','RICHIESTE','','N','CODICE_FASCICOLO,CODICE_PRATICA,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA,NOME_FUNZIONARIO,COGNOME_FUNZIONARIO',false,false);

/* Template per richiesta riattivazione */
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile,riservata)
	VALUES (0,'REQ_ATT','Richiesta riattivazione pratica','Template per la mail di richiesta di riattivazione di una pratica archiviata','Richiesta riattivazione pratica - {CODICE_FASCICOLO}',
'Il sottoscritto {NOME_FUNZIONARIO} {COGNOME_FUNZIONARIO} richiede la riattivazione della pratica con codice {CODICE_PRATICA} presentata dal sig./sig.na {NOME_COGNOME_RICHIEDENTE}.<br>

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>',
'S','N','ED_','RICHIESTE','','N','CODICE_FASCICOLO,CODICE_PRATICA,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA,NOME_FUNZIONARIO,COGNOME_FUNZIONARIO',false,false);