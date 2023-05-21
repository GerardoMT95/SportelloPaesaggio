ALTER TABLE presentazione_istanza.corrispondenza ADD mittente_ruolo varchar NULL;

UPDATE presentazione_istanza.template_email 
SET placeholders='NOME_FILE_ULTERIORE_DOC,TITOLO_FILE_ULTERIORE_DOC,DESCRIZIONE_FILE_ULTERIORE_DOC' 
WHERE codice LIKE'ULTER_DOC%';

UPDATE presentazione_istanza.template_email 
SET testo='<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p><ul><li>Nome file: <a href="{URL_FILE_ULTERIORE_DOC}">{NOME_FILE_ULTERIORE_DOC}</a> </li><li>Titolo: {TITOLO_FILE_ULTERIORE_DOC}</li><li>Descrizione: {DESCRIZIONE_FILE_ULTERIORE_DOC}</li></ul><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>' 
WHERE codice LIKE'ULTER_DOC%';
