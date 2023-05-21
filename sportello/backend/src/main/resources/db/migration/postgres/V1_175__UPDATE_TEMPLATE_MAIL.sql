INSERT INTO presentazione_istanza.template_email (id_organizzazione, codice, nome, descrizione, oggetto, testo, readonly_oggetto, readonly_testo, 
visibilita, sezione, tipi_procedimento_ammessi, protocollazione, placeholders, cancellabile, riservata, sotto_sezione) VALUES(0, 
'INOLTRO_PROVVEDIMENTO_ISTANZE', 
'Inoltro provvedimento finale', 
'Inoltro provvedimento finale', 
'Inoltro Provvedimento Finale Fascicolo {CODICE_FASCICOLO}', 
'<p>Salve,<br/>si comunica che l''istanza {CODICE_FASCICOLO} ha concluso il procedimento. Di seguito i provvedimenti<br/><a href="{PROVVEDIMENTO_PUBBLICO}">Link Provvedimento Finale Pubblico</a><br/><a href="{PROVVEDIMENTO_PRIVATO}">Link Provvedimento Finale Privato</a> </p><br/><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema Pugliacon 
(pugliacon.regione.puglia.it).<br><br></p>', 'S', 'N', '', '', '', 'N', 'CODICE_FASCICOLO,PROVVEDIMENTO_PUBBLICO,PROVVEDIMENTO_PRIVATO', false, true, NULL);