UPDATE template_email
	SET testo='Si comunica che in data 
{DATA_PRESENTAZIONE} è stata ricevuta l''istanza con codice:

{CODICE_FASCICOLO}

per il tipo procedimento autorizzativo:

{TIPO_PROCEDIMENTO}

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
	WHERE id_organizzazione=43 AND codice='RIC_ISTANZA';
