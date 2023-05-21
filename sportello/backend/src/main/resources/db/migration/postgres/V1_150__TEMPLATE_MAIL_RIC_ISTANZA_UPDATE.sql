UPDATE template_email
	SET testo='Si informa che in data {DATA_PRESENTAZIONE} è stata ricevuta l''istanza con codice: <br>

{CODICE_PRATICA}<br>

per il tipo procedimento autorizzativo:<br>

{TIPO_PROCEDIMENTO}<br>

avente ad oggetto:<br>

{OGGETTO}<br>

{PROTOCOLLO}

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema Pugliacon (pugliacon.regione.puglia.it).<br><br></p>'
WHERE codice='RIC_ISTANZA';
