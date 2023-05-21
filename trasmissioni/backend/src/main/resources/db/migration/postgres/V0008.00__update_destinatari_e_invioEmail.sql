UPDATE "template_destinatario_default" SET "pec"='test_trasmissioni_regione_puglia_trasmissioni@sendSpamHere.com' , "denominazione"='Regione Puglia - Ufficio Trasmissioni' , "tipo"='CC' WHERE codice_template='INVIO_TRASMISSIONE';
UPDATE "template_destinatario_default" SET "pec"='test_trasmissioni_regione_puglia_trasmissioni@sendSpamHere.com' , "denominazione"='Regione Puglia - Ufficio Trasmissioni' , "tipo"='CC' WHERE codice_template='ELIMINAZIONE_POST_TRASMISSIONE';
UPDATE "template_destinatario_default" SET "pec"='test_trasmissioni_regione_puglia_campionamenti@sendSpamHere.com', "denominazione"='Regione Puglia - Ufficio Campionamenti', "tipo"='TO' WHERE codice_template='ESITO_CAMPIONAMENTO';

UPDATE "tipo_procedimento" SET "invia_email"=true;
