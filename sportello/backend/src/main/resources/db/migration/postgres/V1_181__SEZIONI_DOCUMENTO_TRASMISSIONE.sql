INSERT INTO presentazione_istanza.template_doc_sezioni 
(id_organizzazione,nome,template_doc_nome,valore,placeholders,tipo_sezione)
SELECT 
id_organizzazione,'Dipartimento',template_doc_nome,'DIPARTIMENTO AMBIENTE, PAESAGGIO E QUALITÀ URBANA'
,null,'TEXT' 
FROM
presentazione_istanza.template_doc_sezioni where template_doc_nome='DocumentoDiTrasmissione' and  nome='Firma';
	
INSERT INTO presentazione_istanza.template_doc_sezioni 
(id_organizzazione,nome,template_doc_nome,valore,placeholders,tipo_sezione)
SELECT 
id_organizzazione,'Sezione',template_doc_nome,'SEZIONE TUTELA E VALORIZZAZIONE DEL PAESAGGIO'
,null,'TEXT' 
FROM
presentazione_istanza.template_doc_sezioni where template_doc_nome='DocumentoDiTrasmissione' and  nome='Firma';	


