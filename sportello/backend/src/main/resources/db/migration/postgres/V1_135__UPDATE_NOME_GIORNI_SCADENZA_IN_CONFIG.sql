-- Aggiunta giorni scadenza
UPDATE presentazione_istanza.configurazione
	SET valore='{"esoneroOneriLabel":"Dichiara di essere esonerato dal pagamento degli oneri istruttori ai sensi dell''art. 10 bis della L.R. 20/2009 come modificata dalla L.R. 19/2010 in quanto ente locale","esoneroBolloLabel":"Dichiara di essere esonerato dal pagamento della marca da bollo ai sensi del DPR 642/1972", "giorniScadenzaPagamento":"30"}'
	WHERE chiave='it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.config.SportelloConfigBean';
