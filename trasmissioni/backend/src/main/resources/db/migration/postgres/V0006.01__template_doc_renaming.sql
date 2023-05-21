DELETE FROM placeholder_doc_sezione;
DELETE FROM template_doc_sezioni;
DELETE FROM template_doc_sezioni_default;
DELETE FROM template_doc;
INSERT INTO template_doc(nome, descrizione)	VALUES ('DocumentoDiTrasmissione', 'Lettera di trasmissione ');
INSERT INTO template_doc_sezioni_default(
	nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	VALUES ('Intestazione','DocumentoDiTrasmissione', 'valore della intestazione', null, 'HTML');
INSERT INTO template_doc_sezioni_default(
	nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	VALUES ('Oggetto','DocumentoDiTrasmissione', 'valore dello oggetto', null, 'TEXT');	
INSERT INTO template_doc_sezioni_default(
	nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	VALUES ('Firma','DocumentoDiTrasmissione', 'valore della firma', null, 'HTML');
INSERT INTO template_doc_sezioni_default(
	nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	VALUES ('Logo','DocumentoDiTrasmissione', '', null, 'IMAGE');
--in corrispondenza di questa riga viene inserito un file nel classpath /jasper/image_template_default/LETTERA_TRASMISSIONE_Logo.png	
INSERT INTO template_doc_sezioni SELECT * FROM template_doc_sezioni_default; 

INSERT INTO placeholder_doc_sezione(	placeholder_codice, template_doc_sezione_nome, template_doc_nome)
	VALUES ('OGGETTO', 'Oggetto','DocumentoDiTrasmissione' );
INSERT INTO placeholder_doc_sezione(	placeholder_codice, template_doc_sezione_nome, template_doc_nome)
	VALUES ('ID_FASCICOLO', 'Oggetto','DocumentoDiTrasmissione' );
INSERT INTO placeholder_doc_sezione(	placeholder_codice, template_doc_sezione_nome, template_doc_nome)
	VALUES ('COMUNE', 'Oggetto','DocumentoDiTrasmissione' );	
    	