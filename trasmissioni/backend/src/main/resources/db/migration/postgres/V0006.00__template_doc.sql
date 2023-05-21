CREATE TABLE template_doc
(
    nome character varying(255) NOT NULL,
    descrizione text NOT NULL,
    PRIMARY KEY (nome)
);

COMMENT ON TABLE template_doc
    IS 'configurazioni per i documenti generati in automatico dal sistema';
	
CREATE TABLE template_doc_sezioni
(
    nome character varying(255) NOT NULL,
    template_doc_nome character varying(255) NOT NULL,
    valore text,
    id_allegato bigint,
    tipo_sezione character varying(20) NOT NULL,
    PRIMARY KEY (nome, template_doc_nome),
    CONSTRAINT template_doc_fkey FOREIGN KEY (template_doc_nome)
        REFERENCES template_doc (nome) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT allegato_fkey FOREIGN KEY (id_allegato)
        REFERENCES allegato (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

COMMENT ON TABLE template_doc_sezioni
    IS 'sezioni configurabili';

COMMENT ON COLUMN template_doc_sezioni.tipo_sezione
    IS 'TEXT HTML IMAGE';
COMMENT ON COLUMN template_doc_sezioni.tipo_sezione
    IS 'TEXT HTML IMAGE';    


CREATE TABLE template_doc_sezioni_default
(
    nome character varying(255) NOT NULL,
    template_doc_nome character varying(255) NOT NULL,
    valore text,
    id_allegato bigint,
    tipo_sezione character varying(20) NOT NULL,
    PRIMARY KEY (nome, template_doc_nome),
    CONSTRAINT template_doc_fkey FOREIGN KEY (template_doc_nome)
        REFERENCES template_doc (nome) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT allegato_fkey FOREIGN KEY (id_allegato)
        REFERENCES allegato (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

COMMENT ON TABLE template_doc_sezioni
    IS 'sezioni configurabili';

COMMENT ON COLUMN template_doc_sezioni.tipo_sezione
    IS 'TEXT HTML IMAGE';

CREATE TABLE placeholder_doc
(
    codice character varying(255) NOT NULL,
    info text NOT NULL,
    PRIMARY KEY (codice)
);

COMMENT ON TABLE placeholder_doc
    IS 'placeholder per le varie sezioni dei template doc';

CREATE TABLE placeholder_doc_sezione
(
    placeholder_codice character varying(255) NOT NULL,
    template_doc_sezione_nome character varying(255) NOT NULL,
    template_doc_nome character varying(255) NOT NULL,
    PRIMARY KEY (placeholder_codice, template_doc_sezione_nome, template_doc_nome),
    CONSTRAINT placeholder_doc_fkey FOREIGN KEY (placeholder_codice)
        REFERENCES placeholder_doc (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT template_doc_sezione_fkey FOREIGN KEY (template_doc_sezione_nome, template_doc_nome)
        REFERENCES template_doc_sezioni (nome, template_doc_nome) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

COMMENT ON TABLE placeholder_doc_sezione
    IS 'relazione tra placeholder e le sezioni del documento';


INSERT INTO template_doc(nome, descrizione)	VALUES ('LETTERA_TRASMISSIONE', 'Lettera di trasmissione ');
INSERT INTO template_doc_sezioni_default(
	nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	VALUES ('Intestazione','LETTERA_TRASMISSIONE', 'valore della intestazione', null, 'HTML');
INSERT INTO template_doc_sezioni_default(
	nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	VALUES ('Oggetto','LETTERA_TRASMISSIONE', 'valore dello oggetto', null, 'TEXT');	
INSERT INTO template_doc_sezioni_default(
	nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	VALUES ('Firma','LETTERA_TRASMISSIONE', 'valore della firma', null, 'HTML');
INSERT INTO template_doc_sezioni_default(
	nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	VALUES ('Logo','LETTERA_TRASMISSIONE', '', null, 'IMAGE');
--in corrispondenza di questa riga viene inserito un file nel classpath /jasper/image_template_default/LETTERA_TRASMISSIONE_Logo.png	
INSERT INTO template_doc_sezioni SELECT * FROM template_doc_sezioni_default; 
	
INSERT INTO placeholder_doc(codice, info)VALUES ('OGGETTO', 'oggetto dell''intervento');	
INSERT INTO placeholder_doc(codice, info)VALUES ('ID_FASCICOLO', 'identificativo pratica');
INSERT INTO placeholder_doc(codice, info)VALUES ('COMUNE', 'la lista dei comuni in cui ricade l''intervento separati da ,');

INSERT INTO placeholder_doc_sezione(	placeholder_codice, template_doc_sezione_nome, template_doc_nome)
	VALUES ('OGGETTO', 'Oggetto','LETTERA_TRASMISSIONE' );
INSERT INTO placeholder_doc_sezione(	placeholder_codice, template_doc_sezione_nome, template_doc_nome)
	VALUES ('ID_FASCICOLO', 'Oggetto','LETTERA_TRASMISSIONE' );
INSERT INTO placeholder_doc_sezione(	placeholder_codice, template_doc_sezione_nome, template_doc_nome)
	VALUES ('COMUNE', 'Oggetto','LETTERA_TRASMISSIONE' );	
	
    
    
    	