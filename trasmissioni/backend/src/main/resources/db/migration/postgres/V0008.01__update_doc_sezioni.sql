UPDATE template_doc_sezioni SET "valore" = 'Ricezione dei dati riguardanti il Provvedimento Paesaggistico avente ad oggetto:<br>- {OGGETTO} (cod. pratica {CODICE_FASCICOLO} <br>- Tipo Procedimento: {TIPO_PROCEDIMENTO}' 
WHERE "nome" = 'Oggetto' AND "template_doc_nome" = 'DocumentoDiTrasmissione';

UPDATE template_doc_sezioni_default SET "valore" = 'Ricezione dei dati riguardanti il Provvedimento Paesaggistico avente ad oggetto:<br>- {OGGETTO} (cod. pratica {CODICE_FASCICOLO} <br>- Tipo Procedimento: {TIPO_PROCEDIMENTO}' 
WHERE "nome" = 'Oggetto' AND "template_doc_nome" = 'DocumentoDiTrasmissione';
