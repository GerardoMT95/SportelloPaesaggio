UPDATE presentazione_istanza.template_email
SET testo='<p>Salve,<br/>si comunica che l''istanza {CODICE_FASCICOLO} ha concluso il procedimento. Di seguito il link al provvedimento<br/><a href="{LINK_PROVVEDIMENTO}">Link Provvedimento</a></p><br/><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema Pugliacon 
(pugliacon.regione.puglia.it).<br><br></p>',
placeholders='CODICE_FASCICOLO,LINK_PROVVEDIMENTO'
WHERE codice='INOLTRO_PROVVEDIMENTO_ISTANZE';	