CREATE TABLE template_doc
(
	id_organizzazione int  NOT NULL,
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT template_doc_pkey PRIMARY KEY (id_organizzazione,nome)
);

COMMENT ON TABLE template_doc
    IS 'configurazioni per i documenti generati in automatico dal sistema';


CREATE TABLE template_doc_sezioni
(
	id_organizzazione int  NOT NULL,
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    template_doc_nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    valore text COLLATE pg_catalog."default",
    id_allegato uuid,
    placeholders text,
    tipo_sezione character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT template_doc_sezioni_pkey PRIMARY KEY (id_organizzazione,nome, template_doc_nome),
    CONSTRAINT allegato_fkey FOREIGN KEY (id_allegato)
        REFERENCES allegati (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT template_doc_fkey FOREIGN KEY (id_organizzazione,template_doc_nome)
        REFERENCES template_doc (id_organizzazione,nome) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

COMMENT ON TABLE template_doc_sezioni
    IS 'sezioni configurabili,qui dentro ci saranno solo le sezioni con id_organizzazione=0 che viene interpretato come default';

COMMENT ON COLUMN template_doc_sezioni.tipo_sezione
    IS 'TEXT HTML IMAGE';
COMMENT ON COLUMN template_doc_sezioni.placeholders
    IS 'lista separata da , di placeholders ammessi nella sezione';



--il default 
INSERT INTO template_doc (id_organizzazione,nome,descrizione) VALUES (0,'DocumentoDiTrasmissione','Lettera di trasmissione ');
INSERT INTO template_doc_sezioni (id_organizzazione,nome,template_doc_nome,valore,id_allegato,tipo_sezione,placeholders) VALUES (0,'Logo','DocumentoDiTrasmissione','',NULL,'IMAGE',null);
INSERT INTO template_doc_sezioni (id_organizzazione,nome,template_doc_nome,valore,id_allegato,tipo_sezione,placeholders) VALUES (0,'FirmaSup','DocumentoDiTrasmissione','LA DIRIGENTE AD INTERIM DEL SERVIZIO OSSERVATORIO E PIANIFICAZIONE PAESAGGISTICA',NULL,'HTML',null);
INSERT INTO template_doc_sezioni (id_organizzazione,nome,template_doc_nome,valore,id_allegato,tipo_sezione,placeholders) VALUES (0,'Firma','DocumentoDiTrasmissione','(Ing. Barbara LOCONSOLE)',NULL,'HTML',null);
INSERT INTO template_doc_sezioni (id_organizzazione,nome,template_doc_nome,valore,id_allegato,tipo_sezione,placeholders) VALUES (0,'Intestazione','DocumentoDiTrasmissione','Servizio Osservatorio e Pianificazione Paesaggistica',NULL,'HTML',null);
INSERT INTO template_doc_sezioni (id_organizzazione,nome,template_doc_nome,valore,id_allegato,tipo_sezione,placeholders) VALUES (0,'Oggetto','DocumentoDiTrasmissione','Ricezione dei dati riguardanti il Provvedimento Paesaggistico avente ad oggetto:<br>- {OGGETTO} (cod. pratica {CODICE_FASCICOLO} )<br>- Tipo Procedimento: {TIPO_PROCEDIMENTO}',NULL,'TEXT','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,COMUNE,ENTE_PROCEDENTE,ESITO_PROVVEDIMENTO');

INSERT INTO tipo_contenuto
(id, descrizione, descrizione_estesa, sezione, multiple)
VALUES(1500, 'Logo ', 'Logo utilizzato in modello documento autogenerato dal sistema(template documentazione)', 'CONF_TEMPLATE_DOC', false);

    