update presentazione_istanza.template_email 
set testo=replace(testo,'SIT Puglia (www.sit.puglia.it)','Pugliacon (pugliacon.regione.puglia.it)')
where testo like '%SIT Puglia (www.sit.puglia.it)%';

--allineamento alle loro descrizioni 
UPDATE presentazione_istanza.tipo_procedimento
	SET descrizione='AUTORIZZAZIONE PAESAGGISTICA art. 146, D.Lgs. 42/2004 - art. 90, NTA PPTR (ORDINARIA)'
	WHERE id=1;
UPDATE presentazione_istanza.tipo_procedimento
	SET descrizione='AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 31/2017 - ART 90, NTA PPTR PER INTERVENTI ED OPERE DI LIEVE ENTITA'' SOGGETTI AL PROCEDIMENTO AUTORIZZATORIO SEMPLIFICATO A NORMA DELL'' ART 146 c.9 DEL D.LGS. 42/2004'
	WHERE id=2;
UPDATE presentazione_istanza.tipo_procedimento
	SET descrizione='ACCERTAMENTO DI COMPATIBILITA'' PAESAGGISTICA Artt. 167 e 181, D.Lgs. 42/2004 (PER OPERE IN DIFFORMITA'' O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)'
	WHERE id=3;

ALTER TABLE presentazione_istanza.tipo_procedimento ADD descr_stampa varchar(400) NULL;
ALTER TABLE presentazione_istanza.tipo_procedimento ADD descr_stampa_titolo varchar(255) NULL;
ALTER TABLE presentazione_istanza.tipo_procedimento ADD descr_stampa_sottotitolo varchar(400) NULL;

COMMENT ON COLUMN presentazione_istanza.tipo_procedimento.descr_stampa IS 'Sezione jasper in alto a sinistra per la Dichiarazione_ tecnica';
COMMENT ON COLUMN presentazione_istanza.tipo_procedimento.descr_stampa_titolo IS 'Sezione jasper per il titolo nella sezione di prima pagina in alto a destra';
COMMENT ON COLUMN presentazione_istanza.tipo_procedimento.descr_stampa_sottotitolo IS 'Sezione jasper per il sottotitolo in Domanda istanza in prima pagina';

UPDATE presentazione_istanza.tipo_procedimento SET descr_stampa='SCHEDA TECNICA<br/>ALLEGATO ALL''ISTANZA DI<br/>AUTORIZZAZIONE PAESAGGISTICA<br/><font size="2">(ART. 146 D.LGS. 42/2004 - ART.90 NTA PPTR)</font>', 
descr_stampa_titolo='Accertamento paesaggistica ordinaria (art. 146 D.gs. n. 42/2004)', descr_stampa_sottotitolo='SCHEDA TECNICA<br/>ALLEGATA ALL''ISTANZA DI AUTORIZZAZIONE' WHERE id=1;
UPDATE presentazione_istanza.tipo_procedimento SET descr_stampa='<font size="2">SCHEDA PER LA PRESENTAZIONE DELLA RICHIESTA DI<br/> AUTORIZZAZIONE PAESAGGISTICA PER LE OPERE IL CUI IMPATTO<br/>PAESAGGISTICO E'' VALUTATO MEDIANTE UNA DOCUMENTAZIONE<br/> SEMPLIFICATA DI CUI AL D.P.M. 12//12/2005</font><br/>ALLEGATA ALL''ISTANZA DI <br/>AUTORIZZAZIONE PAESAGGISTICA <br/>SEMPLIFICATA<br/> D.P.R. 139/2010 ART. 90, NTA PPTR', descr_stampa_titolo='Autorizzazione paesaggistica semplificata (d.P.R. n.139/2010)', descr_stampa_sottotitolo='RELAZIONE PAESAGGISTICA SEMPLIFICATA<br/>ALLEGATA ALL''ISTANZA DI AUTORIZZAZIONE' WHERE id=6;
UPDATE presentazione_istanza.tipo_procedimento SET descr_stampa='SCHEDA TECNICA ALLEGATA<br/>ALL''ISTANZA DI ACCERTAMENTO DI COMPATIBILITA''<br/>PAESAGGISTICA<br/>ART. 167 E 181 D.LGS. N.42/2004<br/><strong><font size="2">(PER OPERE IN DIFFORMITA'' O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)</font></strong>', descr_stampa_titolo='Accertamento di compatibilità paesaggistica (artt. 167-181 D.Lgs. n. 42/04)', descr_stampa_sottotitolo='SCHEDA TECNICA ALLEGATA<br/>ALL''ISTANZA DI ACCERTAMENTO DI COMPATIBILITA'' PAESAGGISTICA' WHERE id=3;
UPDATE presentazione_istanza.tipo_procedimento SET descr_stampa='SCHEDA TECNICA ALLEGATA<br/>ALL''ISTANZA DI ACCERTAMENTO DI<br/>COMPATIBILITA'' PAESAGGISTICA<br/>ART.91 NTA PPTR<br/><strong><font size="2">(PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI <br/>ULTERIORI CONTESTI COME INDIVIDUATI NELL''ART.3.1 NTA PPTR)</font></strong>', descr_stampa_titolo='Accertamento di compatibilita''  paesaggistica (art. 91 PPTR)', descr_stampa_sottotitolo='SCHEDA TECNICA ALLEGATA ALL''ISTANZA DI<br/>ACCERTAMENTO DI COMPATIBILITA'' PAESAGGISTICA ART.91 NTA PPTR' WHERE id=5;
UPDATE presentazione_istanza.tipo_procedimento SET descr_stampa='<font size="1">SCHEDA PER LA PRESENTAZIONE DELLA RICHIESTA DI<br/> AUTORIZZAZIONE PAESAGGISTICA PER INTERVENTI ED OPERE DI LIEVE ENTITA'' SOGGETTI AL PROCEDIMENTO AUTORIZZATORIO SEMPLIFICATO<br/>CONFORMEMENTE ALL''ALLEGATO "D" DI CUI ALL''ART.8 c.1 DEL DPR 31/2017</font><br/>ALLEGATA ALL''ISTANZA DI <br/>AUTORIZZAZIONE PAESAGGISTICA <br/>SEMPLIFICATA<br/> D.P.R. N.31/2017 ART. 90 NTA PPTR', descr_stampa_titolo='Autorizzazione paesaggistica semplificata (d.P.R. n.31/2017)', descr_stampa_sottotitolo='<b>RELAZIONE PAESAGGISTICA SEMPLIFICATA<br/>ALLEGATA ALL''ISTANZA DI AUTORIZZAZIONE</b><br/><font size="1">[N.B.: LA PRESENTE RELAZIONE E'' REDATTA SECONDO IL MODELLO SEMPLIFICATO DI CUI ALL''ALLEGATO D DEL DPR 31/2017 E PERTANTO E'' DA INTENDERSI SOSTITUTIVA DELLO STESSO]</font>' WHERE id=2;
UPDATE presentazione_istanza.tipo_procedimento SET descr_stampa='SCHEDA TECNICA ALLEGATA<br/>ALL''ISTANZA DI ACCERTAMENTO DI<br/>COMPATIBILITA'' PAESAGGISTICA<br/>ART.91 NTA PPTR<br/><strong><font size="2">(PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI <br/>ULTERIORI CONTESTI COME INDIVIDUATI NELL''ART. 38 C. 3.1 NTA PPTR)</font></strong>', descr_stampa_titolo='Accertamento di compatibilita'' paesaggistica (art. 91 PPTR)', descr_stampa_sottotitolo='SCHEDA TECNICA ALLEGATA ALL''ISTANZA DI<br/>ACCERTAMENTO DI COMPATIBILITA'' PAESAGGISTICA ART.91 NTA PPTR' WHERE id=4;
