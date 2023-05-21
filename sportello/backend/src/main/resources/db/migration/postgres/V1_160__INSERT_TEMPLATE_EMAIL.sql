INSERT INTO presentazione_istanza.template_email (id_organizzazione, codice, nome, descrizione, oggetto, testo, readonly_oggetto, readonly_testo, 
visibilita, sezione, tipi_procedimento_ammessi, protocollazione, placeholders, cancellabile, riservata, sotto_sezione) VALUES(0, 
'CONVALIDA_RICHIEDENTE_NUOVO_CODICE', 
'Email nuovo codice segreto convalida richiedente', 
'Email nuovo codice segreto convalida richiedente', 
'Subentro fascicolo {CODICE_FASCICOLO}', 
'<p>Salve,<br/>a seguito di un subentro si invia il nuovo codice segreto {CODICE_SEGRETO} per l''istanza {CODICE_FASCICOLO} di tipo {TIPO_FASCICOLO}</p><br/><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema Pugliacon 
(pugliacon.regione.puglia.it).<br><br></p>', 'S', 'N', '', '', '', 'N', 'CODICE_FASCICOLO,CODICE_SEGRETO,TIPO_FASCICOLO', false, true, NULL);