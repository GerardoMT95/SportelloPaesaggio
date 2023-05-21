INSERT INTO presentazione_istanza.template_email (id_organizzazione, codice, nome, descrizione, oggetto, testo, readonly_oggetto, readonly_testo, 
visibilita, sezione, tipi_procedimento_ammessi, protocollazione, placeholders, cancellabile, riservata, sotto_sezione) VALUES(0, 
'NOTIFICA_CDS', 
'Notifica CDS', 
'Notifica CDS',
'SIT Puglia - Notifica Conferenza dei servizi {CODICE_FASCICOLO}',
'Salve,<br/> si comunica che l''istanza {CODICE_FASCICOLO} è disponibile 
 all''interno della conferenza dei servizi.<br/><br/>La presente comunicazione è stata generata automaticamente, 
 si chiede pertanto di non rispondere al messaggio.', 'S', 'N', '', '', '', 'N', 'CODICE_FASCICOLO', false, true, NULL);