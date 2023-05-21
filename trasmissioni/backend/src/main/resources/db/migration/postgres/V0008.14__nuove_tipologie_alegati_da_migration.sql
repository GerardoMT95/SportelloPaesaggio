--valore inserito in DB solo per renderizzare alcune pratiche migrate.
INSERT INTO tipo_allegato ("type", "descrizione") VALUES ('SCHEDA_CONOSCITIVA_MANUFATTO', 'Scheda conoscitiva del manufatto e del contensto rurale');
INSERT INTO tipo_allegato ("type", "descrizione") VALUES ('SCHEDA_PROGETTO_MANUFATTO_RURALE', 'Scheda di progetto del manufatto rurale');
UPDATE tipo_allegato
	SET descrizione='Scheda conoscitiva del manufatto e del contesto rurale (rif. Capitolo 2 dell''elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione acquisiti dall''Ente e il riuso dell''Edilizia e dei Beni Rurali)'
	WHERE "type"='PARERI_SCHEDA';


	 	   