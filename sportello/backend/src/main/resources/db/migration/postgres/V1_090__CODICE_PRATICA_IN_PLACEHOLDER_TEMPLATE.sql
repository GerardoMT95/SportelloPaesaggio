UPDATE presentazione_istanza.template_email 
SET placeholders='CODICE_PRATICA,'||placeholders 
where not placeholders like '%CODICE_PRATICA%' and placeholders like '%CODICE_FASCICOLO%';
