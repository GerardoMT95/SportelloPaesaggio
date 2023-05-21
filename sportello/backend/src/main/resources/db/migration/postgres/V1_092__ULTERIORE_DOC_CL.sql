INSERT INTO presentazione_istanza.template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile,riservata)
	VALUES (0,'ULTER_DOC_CL','Ulteriore Documentazione per Commissione Locale','Ulteriore Documentazione per Commissione Locale','Commissione Locale  Fascicolo {CODICE_FASCICOLO} documentazione allegata','<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p><ul><li>Nome file: <a href="{URL_FILE_ULTERIORE_DOC}">{NOME_FILE_ULTERIORE_DOC}</a> </li><li>Titolo: {TITOLO_FILE_ULTERIORE_DOC}</li><li>Descrizione: {DESCRIZIONE_FILE_ULTERIORE_DOC}</li></ul><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>','S','N',',CL_,','','','N','NOME_FILE_ULTERIORE_DOC,TITOLO_FILE_ULTERIORE_DOC,DESCRIZIONE_FILE_ULTERIORE_DOC,URL_FILE_ULTERIORE_DOC',false,true);
	
