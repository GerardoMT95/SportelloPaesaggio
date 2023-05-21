update presentazione_istanza.template_email 
set testo = '<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p>{FILES_ULTERIORE_DOCUMENTAZIONE}<p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>', 
	placeholders = 'CODICE_FASCICOLO, FILES_ULTERIORE_DOCUMENTAZIONE'
where codice = 'ULTER_DOC_REG';

update presentazione_istanza.template_email 
set testo = '<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p>{FILES_ULTERIORE_DOCUMENTAZIONE}<p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>', 
	placeholders = 'CODICE_FASCICOLO, FILES_ULTERIORE_DOCUMENTAZIONE'
where codice = 'ULTER_DOC_CL';

update presentazione_istanza.template_email 
set testo = '<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p>{FILES_ULTERIORE_DOCUMENTAZIONE}<p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>', 
	placeholders = 'CODICE_FASCICOLO, FILES_ULTERIORE_DOCUMENTAZIONE'
where codice = 'ULTER_DOC_ED';

update presentazione_istanza.template_email 
set testo = '<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p>{FILES_ULTERIORE_DOCUMENTAZIONE}<p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>', 
	placeholders = 'CODICE_FASCICOLO, FILES_ULTERIORE_DOCUMENTAZIONE'
where codice = 'ULTER_DOC_ET';

update presentazione_istanza.template_email 
set testo = '<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p>{FILES_ULTERIORE_DOCUMENTAZIONE}<p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>', 
	placeholders = 'CODICE_FASCICOLO, FILES_ULTERIORE_DOCUMENTAZIONE'
where codice = 'ULTER_DOC_SOP';