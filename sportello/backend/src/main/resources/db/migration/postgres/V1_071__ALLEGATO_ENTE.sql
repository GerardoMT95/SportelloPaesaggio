CREATE TABLE presentazione_istanza.allegato_ente (
	id_allegato UUID NOT NULL,
	codice varchar(20) NOT NULL,
	CONSTRAINT allegato_ente_pkey PRIMARY KEY (id_allegato, codice)
);
ALTER TABLE presentazione_istanza.allegato_ente ADD CONSTRAINT allegato_ente_id_allegato_fkey FOREIGN KEY (id_allegato) REFERENCES presentazione_istanza.allegati(id);
INSERT INTO presentazione_istanza.tipo_contenuto (id,descrizione,descrizione_estesa,sezione)
	VALUES (1200,'ULTERIORE_DOCUMENTAZIONE','ULTERIORE DOCUMENTAZIONE','ULTERIORE_DOCUMENTAZIONE');