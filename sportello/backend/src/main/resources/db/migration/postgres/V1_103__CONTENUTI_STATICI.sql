CREATE TABLE presentazione_istanza.web_content
(
    codice_contenuto character varying(255) NOT NULL,
    tooltip character varying,
    contenuto character varying,
    PRIMARY KEY (codice_contenuto)
);

ALTER TABLE presentazione_istanza.web_content
    OWNER to presentazione_istanza;
COMMENT ON TABLE presentazione_istanza.web_content
    IS 'tabella contenente contenuti web statici NON storici es. messaggio di creazione nuovo fascicolo';
    
insert into web_content values('NUOVO_FASCICOLO',
	'<div><b>{ENTI_ABILITATI}</b>: Elenco degli enti abilitati allo sportello di presentazione autorizzazione paesaggistica<br></div>',
	'				
Attraverso la presente procedura telematica possono essere <b>presentate esclusivamente le istanze di competenza dei seguenti enti:</b>
{ENTI_ABILITATI}
Con riferimento alla Regione Puglia si specifica che quelle di competenza regionale sono quelle:
<br>
<br>
&gt; afferenti interventi ricadenti nei territori comunali per i quali non è stato delegato l’esercizio delle funzioni paesaggistiche 
<a href="http://www.sit.puglia.it/auth/portal/portale_autorizzazione_paesaggistica/Deleghe/Enti Delegati e Provvedimenti - Ricerca per Comune">link</a>
<br>
&gt; in capo alla Regione poiché, ai sensi del c. 6 art. 146 D.Lgs. 42/2004 e delle N.T.A. del PPTR ed indipendentemente dalla localizzazione, ricadono nelle seguenti casistiche
<br>
<div > 
a) le infrastrutture stradali, ferroviarie, portuali, aeroportuali e idrauliche e di telecomunicazioni di interesse regionale o sovra regionale;
</div>
<div > 
b) nuovi insediamenti produttivi, direzionali, commerciali o nuovi parchi tematici che richiedano per la loro realizzazione una superficie territoriale superiore a 40 mila metri quadrati;
</div>
<div > 
c) impianti di produzione di energia con potenza nominale superiore a 10 Megawatt. 
</div>
<br>
<br>
<br>
<br>    <b>Sei sicuro di voler procedere?</b>
<br>'
);    