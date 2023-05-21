CREATE TABLE template_email (
	id_organizzazione int4 NOT NULL, -- se pari a 0 indica un template default non cancellabile.¶altrimenti indica un template riferito ad una organizzazione
	codice varchar(255) NOT NULL, -- codici parlanti in caso di template di configurazione (non cancellabili)
	nome varchar(255) NULL,
	descrizione varchar(1000) NOT NULL,
	oggetto varchar(255) NOT NULL,
	testo text NOT NULL,
	readonly_oggetto varchar(1) NULL DEFAULT 'N',
	readonly_testo varchar(1) NULL DEFAULT 'N',
	visibilita varchar(255) NOT NULL DEFAULT 'ED_', -- array con valori separati da ,  di tipi organizzazione¶ED_ ¶SBAP_¶ETI_¶CL_¶REG_
	sezione varchar(50) NULL, -- se non vuoto indica dove puo' essere utilizzato il template¶es.¶TRASM_REL_ENTE¶TRASM_PAR_SOP¶¶
	tipi_procedimento_ammessi varchar(255) NULL, -- array di tipo procedimento ammessi separati da , es.:¶AUT_PAES_ORD, AUT_PAES_SEMPL_DPR_31_2017¶
	protocollazione varchar(1) NULL, -- S se è prevista la protocollazione... per ora non utilizzato...
	placeholders text NULL, -- lista dei placeholders ammessi separati da ,
	cancellabile boolean NOT NULL DEFAULT true,
	CONSTRAINT template_email_pkey PRIMARY KEY (id_organizzazione, codice)
);

-- Column comments

COMMENT ON COLUMN template_email.id_organizzazione IS 'se pari a 0 indica un template default non cancellabile.
altrimenti indica un template riferito ad una organizzazione';
COMMENT ON COLUMN template_email.codice IS 'codici parlanti in caso di template di configurazione (non cancellabili)';
COMMENT ON COLUMN template_email.visibilita IS 'array con valori separati da ,  di tipi organizzazione
ED_ 
SBAP_
ETI_
CL_
REG_';
COMMENT ON COLUMN template_email.sezione IS 'se non vuoto indica dove puo'' essere utilizzato il template
es.
TRASM_REL_ENTE
TRASM_PAR_SOP

';
COMMENT ON COLUMN template_email.tipi_procedimento_ammessi IS 'array di tipo procedimento ammessi separati da , es.:
AUT_PAES_ORD, AUT_PAES_SEMPL_DPR_31_2017
';
COMMENT ON COLUMN template_email.protocollazione IS 'S se è prevista la protocollazione... per ora non utilizzato...';

CREATE TABLE template_destinatario (
	id bigserial NOT NULL,
	id_organizzazione int,
	codice_template varchar(255) NOT NULL,
	email varchar(255) NULL,
	pec varchar(255) NULL,
	denominazione varchar(255) NOT NULL,
	tipo varchar(10) NOT NULL,
	CONSTRAINT template_destinatario_pkey PRIMARY KEY (id),
	CONSTRAINT template_destinatario_codice_template_fkey FOREIGN KEY (id_organizzazione,codice_template) REFERENCES template_email(id_organizzazione,codice)
);	


CREATE SEQUENCE codice_template
    INCREMENT 1
    START 7
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

COMMENT ON SEQUENCE codice_template
    IS 'sequenza per generare un codice template';


-- FUNCTION: presentazione_istanza.next_codice_template()

-- DROP FUNCTION presentazione_istanza.next_codice_template();

CREATE OR REPLACE FUNCTION next_codice_template(
	)
    RETURNS character varying
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
AS $BODY$Select concat('TEMPL_',nextval('codice_template'));$BODY$;

COMMENT ON FUNCTION next_codice_template()
    IS 'genera un nuovo codice template a partire dalla sequence codice_template';



COMMENT ON COLUMN template_email.cancellabile
    IS 'indica se il template è di configurazione oppure è custom ed è cancellabile';
    
UPDATE presentazione_istanza.template_email	SET cancellabile=false;  

--template derivanti da trasmissioni 
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'RICH_PAR_SOP_11','trasmissione relazione tecnica - con avvio','146 - trasmissione relazione tecnica - con avvio','AUT. PAE. ORDINARIA -  AVVIO DEL PROCEDIMENTO - trasmissione relazione tecnica illustrativa e proposta di provvedimento: {CODICE_FASCICOLO} ','<div>
<p>
<b>AUT. PAE. ORDINARIA </b>-  AVVIO DEL PROCEDIMENTO - trasmissione relazione tecnica illustrativa e proposta di provvedimento
</p>
<br />
<p>
Comune/i intervento {COMUNE} - {NOME_COGNOME_RICHIEDENTE} <b>AUTORIZZAZIONE PAESAGGISTICA ORDINARIA </b>- 
<br> AVVIO DEL PROCEDIMENTO - trasmissione relazione tecnica illustrativa e proposta di provvedimento della domanda (L.n. 241/90, art. 146 c. 7 del D.Lgs. n. 42/2004 e art. 90 co. 4 NTA PPTR).
</p>
<br>
<p>
Con la presente si fa riferimento alla richiesta di rilascio di autorizzazione paesaggistica relativa al progetto per ''{OGGETTO}'', acquisita al protocollo di questo servizio con n. {NUM_PROTOCOLLO_ISTANZA} del {DATA_PROTOCOLLO_ISTANZA}
<br />
Pertanto si trasmette la relazione tecnica illustrativa unitamente alla proposta di accoglimento della domanda di competenza dello scrivente Servizio, alla Soprintendenza per i provvedimenti di competenza, ai sensi del co. 7 art. 146 del D.Lgs. 42/04 <b>e, contestualmente, si comunica, il solo avvio del procedimento istruttoria al</b> '''',  indicando nella persona del ''{RUP}''  il Responsabile del Procedimento.
<p>
<br>
<br>
<br />Il Responsabile del Procedimento <br>
(arch./ing./ geom.  {RUP} )  
<br>
<br />Il dirigente "ad interim" del Servizio
<br>Attuazione Pianificazione Paesaggistica
<br><b>(ing. Barbara LOCONSOLE)</b>
<br />
<br>
<p>
La firma autografa del dirigente del Servizio e del Responsabile del Procedimento e'' sostituita a mezzo stampa ai sensi dell'' art. 3 comma 2 del D.L. 39/1993 e ss.mm.ii.
</p>
</div>','S','N','ED_,REG_','TRASM_REL_ENTE','AUT_PAES_ORD,ACCERT_COMPAT_PAES_DLGS_42_2004','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'14','sollecito trasmissione parere','sollecito trasmissione parere','sollecito trasmissione parere','sollecito trasmissione parere','N','N','ED_,REG_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'15','richiesta di chiarimenti alla Soprintendenza','richiesta di chiarimenti alla Soprintendenza','richiesta di chiarimenti alla Soprintendenza','richiesta di chiarimenti alla Soprintendenza','N','N','ED_,REG_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'16','richiesta di chiarimenti all''Ente territoriale','richiesta di chiarimenti all''Ente territoriale','richiesta di chiarimenti all''Ente territoriale','richiesta di chiarimenti all''Ente territoriale','N','N','ED_,REG_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'17','richiesta parere soprintendenza Tipo 1','richiesta parere soprintendenza Tipo 1','richiesta parere soprintendenza Tipo 1','richiesta parere soprintendenza Tipo 1','N','N','ED_,REG_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'18','richiesta parere soprintendenza Tipo 2','richiesta parere soprintendenza Tipo 2','richiesta parere soprintendenza Tipo 2','richiesta parere soprintendenza Tipo 2','N','N','ED_,REG_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'RICH_PAR_SOP_12','trasmissione relazione tecnica - senza avvio','146 - trasmissione relazione tecnica - senza avvio','AUT. PAE. ORDINARIA -  TRASMISSIONE RELAZIONE TECNICO ILLUSTRATIVA e proposta di provvedimento pratica: {CODICE_FASCICOLO}','<div>
<p>
<b>AUT. PAE. ORDINARIA </b>-  TRASMISSIONE RELAZIONE TECNICO ILLUSTRATIVA e proposta di provvedimento
</p>
<br />
<p>
Comune/i intervento {COMUNE} - {NOME_COGNOME_RICHIEDENTE} - <b>AUTORIZZAZIONE PAESAGGISTICA ORDINARIA</b> -  TRASMISSIONE <br />RELAZIONE TECNICO ILLUSTRATIVA e proposta di provvedimento della domanda (art. 146 c. 7 del D.Lgs. n. 42/2004 e art. 90 co. 4 NTA PPTR).
</p>
<br />
<p>
Con la presente si fa riferimento alla richiesta di rilascio di autorizzazione paesaggistica relativa al progetto per ''{OGGETTO}'', acquisita al protocollo di questo servizio con n. {NUM_PROTOCOLLO_ISTANZA} del {DATA_PROTOCOLLO_ISTANZA}
<br />
Pertanto si trasmette la relazione tecnica illustrativa unitamente alla proposta di accoglimento della domanda di competenza dello scrivente Servizio, alla Soprintendenza per i provvedimenti di competenza, ai sensi del co. 7 art. 146 del D.Lgs. 42/04.

</p>
<br />
Il Responsabile del Procedimento <br />
(arch./ing./ geom. {RUP}) <br/> 
<br />
Il dirigente ''''ad interim''''del Servizio<br />
Attuazione Pianificazione Paesaggistica<br />
<b>(ing. Barbara LOCONSOLE)</b>
<br /><br />
<br />
<p>La firma autografa del dirigente del Servizio e del Responsabile del Procedimento è sostituita a mezzo stampa ai sensi <br />dell'' art. 3 comma 2 del D.L. 39/1993 e ss.mm.ii.</p>
</div>','S','N','ED_,REG_','TRASM_REL_ENTE','AUT_PAES_ORD,ACCERT_COMPAT_PAES_DLGS_42_2004','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'RICH_PAR_SOP_21','trasmissione relazione tecnica - con avvio','139 - trasmissione relazione tecnica - con avvio','AUT. PAE. SEMPLIFICATA  -  AVVIO DEL PROCEDIMENTO - trasmissione relazione tecnica illustrativa e proposta di provvedimento: {CODICE_FASCICOLO} ','<div>
<p>
<b>AUT. PAE. SEMPLIFICATA </b>-  AVVIO DEL PROCEDIMENTO - trasmissione relazione tecnica illustrativa e proposta di provvedimento
</p>
<p>
<b>AUTORIZZAZIONE PAESAGGISTICA  SEMPLIFICATA</b>-  AVVIO DEL PROCEDIMENTO - trasmissione relazione tecnica<br /> illustrativa e proposta di provvedimento della domanda (L.n. 241/90, art. 146 c. 9 del D.Lgs. n. 42/2004; artt. 4, 6 d.P.R. 139/2010 e art. 90 co. 4 NTA PPTR).
</p>
<br />
<p>
Con la presente si fa riferimento alla richiesta di rilascio di autorizzazione paesaggistica relativa al progetto per ''{OGGETTO}'', acquisita al protocollo di questo servizio con n. {NUM_PROTOCOLLO_ISTANZA} del {DATA_PROTOCOLLO_ISTANZA}
<br />
Pertanto si trasmette la relazione tecnica illustrativa unitamente alla proposta di accoglimento della domanda di competenza dello scrivente Servizio, alla Soprintendenza per i provvedimenti di competenza, ai sensi del comma 6 art. 4 del d.P.R. 139/2010 e, contestualmente, si comunica, il solo avvio del procedimento d''istruttoria al (inserire ditta o Comune o ..... ), indicando nella persona del {RUP} (xxxxxxxxx@entexxxx.it) il Responsabile del Procedimento.
</p>
<br />
Il Responsabile del Procedimento <br />
(arch./ing./ geom. {RUP})  <br />
<br />
Il dirigente ''''ad interim'''' del Servizio<br />
Attuazione Pianificazione Paesaggistica<br />
<b>(ing. Barbara LOCONSOLE)</b>
<br /><br />
<p>
La firma autografa del dirigente del Servizio e del Responsabile del Procedimento è sostituita a mezzo stampa ai sensi <br />dell'' art. 3 comma 2 del D.L. 39/1993 e ss.mm.ii.
</p>
</div>','S','N','ED_,REG_','TRASM_REL_ENTE','AUT_PAES_SEMPL_DPR_139_2010,ACCERT_COMPAT_PAES_DPR_139_2010','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'RICH_PAR_SOP_22','trasmissione relazione tecnica - senza avvio','139 - trasmissione relazione tecnica - senza avvio','AUT. PAE. SEMPLIFICATA -  TRASMISSIONE RELAZIONE TECNICO ILLUSTRATIVA e proposta di provvedimento: {CODICE_FASCICOLO}','<div>
<p>
<b>AUT. PAE. SEMPLIFICATA </b>-  TRASMISSIONE RELAZIONE TECNICO ILLUSTRATIVA e proposta di provvedimento
</p>
<br />
<p>
Comune/i intervento {COMUNE} - {NOME_COGNOME_RICHIEDENTE} <b>AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA </b>-  TRASMISSIONE RELAZIONE TECNICO ILLUSTRATIVA e proposta di provvedimento della domanda ( art. 146 c. 9 del D.Lgs. n. 42/2004; artt. 4, 6 d.P.R. 139/2010 e art. 90 co. 4 NTA PPTR)
</p>
<br />
<p>
Con la presente si fa riferimento alla richiesta di rilascio di autorizzazione paesaggistica relativa al progetto per ''{OGGETTO}'', acquisita al protocollo di questo servizio con n. {NUM_PROTOCOLLO_ISTANZA} del {DATA_PROTOCOLLO_ISTANZA} 
<br />Pertanto si trasmette la relazione tecnica illustrativa unitamente alla proposta di accoglimento della domanda di competenza dello scrivente Servizio, alla Soprintendenza per i provvedimenti di competenza, ai sensi del comma 6 art. 4 del d.P.R. 139/2010.
</p>
<br />
<br />
Il Responsabile del Procedimento <br />
(arch./ing./ geom. {RUP})  <br />
<br />
Il dirigente ''''ad interim'''' del Servizio<br />
Attuazione Pianificazione Paesaggistica<br />
<b>(ing. Barbara LOCONSOLE)</b>
<br />
<br />
<p>
La firma autografa del dirigente del Servizio e del Responsabile del Procedimento è sostituita a mezzo stampa ai sensi <br />dell'' art. 3 comma 2 del D.L. 39/1993 e ss.mm.ii.
</p>
</div>','S','N','ED_,REG_','TRASM_REL_ENTE','AUT_PAES_SEMPL_DPR_139_2010,ACCERT_COMPAT_PAES_DPR_139_2010','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'RICH_PAR_SOP_31','trasmissione relazione tecnica - con avvio','167 - trasmissione relazione tecnica - con avvio','in attesa ','<div>
<p>
<b>ACC.COMP. PAE. ex art. 167</b> -  AVVIO DEL PROCEDIMENTO - trasmissione relazione tecnica illustrativa e proposta di parere.
</p>
<br />
<p>
<b>{COMUNE} </b> <br />
<b>Istanza di accertamento di compatibilità  paesaggistica  ex art. 167 </b>D. Lgs 42/2004 e s.m.i.  relativa a ''{OGGETTO}'',<br />
Proponente :{NOME_COGNOME_RICHIEDENTE}
</p>
<br />
<p>
In allegato si trasmette la relazione tecnica illustrativa contenente la proposta di parere di questo Ufficio relativa alle opere in oggetto indicate, inoltrata dal proponente  di cui all''oggetto, acquisita al protocollo di questo servizio con n. {NUM_PROTOCOLLO_ISTANZA} del {DATA_PROTOCOLLO_ISTANZA} e finalizzata all''ottenimento dell''accertamento di compatibilità paesaggistica di cui al comma 5 dell'' art. 167 e del comma 1quater dell'' art.181 del D.Lgs 42/2004. <br />
Il parere di competenza di codesta Soprintendenza dovrà  essere trasmesso a questo Servizio. La presente vale, altresì, ai fini dell'' avvio del procedimento ai sensi del comma 7, art.146 del D. Lgs 42/04 e ss. mm. ii. 
</p>
<br />
<p>
A tal scopo si comunica :
<br />- Responsabile del procedimento:  {RUP} 
<br />- termine di conclusione del procedimento : come previsto dal D.Lgs 42/200 
</p>
<br /><br />
<p align="left">
IL FUNZIONARIO A.P.
<br />DELL'' UFFICIO ATTUAZIONE 
<br />PIANIFICAZIONE PAESAGGISTICA
 <br />(......)
</p>
<p>
La firma autografa del dirigente del Servizio e del Responsabile del Procedimento è sostituita a mezzo stampa ai sensi dell'' art. 3 comma 2 del D.L. 39/1993 e ss.mm.ii.
</p>
</div>','S','N','ED_,REG_','TRASM_REL_ENTE','AUT_PAES_SEMPL_DPR_31_2017,ACCERT_COMPAT_PAES_DPR_31_2017','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'RICH_PAR_SOP_32','trasmissione relazione tecnica - senza avvio','167 - trasmissione relazione tecnica - senza avvio','in attesa ','<div>
<p>
<b>ACC.COMP. PAE. ex art. 167 </b>-  TRASMISSIONE RELAZIONE TECNICO ILLUSTRATIVA e proposta di provvedimento
</p>
<br />
<p>
<b>{COMUNE} </b> <br />
<b>Istanza di accertamento di compatibilità  paesaggistica  ex art. 167 </b>D. Lgs 42/2004 e s.m.i.  relativa a ''{OGGETTO}'',<br />
Proponente :{NOME_COGNOME_RICHIEDENTE}
</p>
<br />
<p>
In allegato si trasmette la relazione tecnica illustrativa contenente la proposta di parere di questo Ufficio relativa alle opere in oggetto indicate, inoltrata dal proponente  di cui all''oggetto, acquisita al protocollo di questo servizio con n. {NUM_PROTOCOLLO_ISTANZA} del {DATA_PROTOCOLLO_ISTANZA} e finalizzata all'' ottenimento dell''accertamento di compatibilità  paesaggistica di cui al comma 5 dell'' art. 167 e del comma 1quater dell'' art.181 del D.Lgs 42/2004. 
<br />Il parere di competenza di codesta Soprintendenza dovrà  essere trasmesso a questo Servizio. 
</p>
<br />
<p>
A tal scopo si comunica :
<br />- termine di conclusione del procedimento : come previsto dal D.Lgs 42/200
</p>
<p align="left">
IL FUNZIONARIO A.P.
<br />  DELL'' UFFICIO ATTUAZIONE 
<br />PIANIFICAZIONE PAESAGGISTICA
 <br />(Arch. ..........)
</p>
<p>
La firma autografa del dirigente del Servizio e del Responsabile del Procedimento è sostituita a mezzo stampa ai sensi dell'' art. 3 comma 2 del D.L. 39/1993 e ss.mm.ii.
</p>
</div>','S','N','ED_,REG_','TRASM_REL_ENTE','AUT_PAES_SEMPL_DPR_31_2017,ACCERT_COMPAT_PAES_DPR_31_2017','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'COM_IMM_PAR_SOP_10','Trasmissione Parere Soprintendenza','Invio parere soprintendenza','Invio parere soprintendenza per fascicolo {CODICE_FASCICOLO}','invio parere soprintendenza per fascicolo {CODICE_FASCICOLO} con Oggetto: {OGGETTO}','N','N','SBAP_','TRASM_PAR_SOP','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'GEN_SOP','comunicazione generica soprintendenza','comunicazione generica soprintendenza','comunicazione generica soprintendenza','comunicazione generica soprintendenza','N','N','SBAP_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'GEN_ED','comunicazione generica ente delegato','comunicazione generica ente delegato','comunicazione generica ente delegato','comunicazione generica ente delegato','N','N','ED_,REG_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'GENERIC_TER','Trasmissione Comunicazione Ente Territoriale','Trasmissione Comunicazione Ente Territoriale','Comunicazione','Si comunica che con la presenta PEC è stata trasmessa la documentazione allegata relatica al Fascicolo <b>{CODICE_FASCICOLO}</b><br>Cordiali saluti<br><br><p align=''center''>Si prega di non rispondere a questa e-mail in quanto la stessa e prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).</p>','S','S','ETI_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'13','richiesta di integrazioni','richiesta di integrazioni','richiesta di integrazioni','richiesta di integrazioni','N','N','ED_,REG_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'INVIO_TRASMISSIONE','Trasmissione Provvedimento','Trasmissione Provvedimento','Trasmissione autorizzazione paesaggistica {CODICE_FASCICOLO} - {COMUNE} ','<p>Si comunica che, con la presente PEC, l’Ente Delegato {ENTE_PROCEDENTE} ha effettuato la trasmissione della pratica <strong>{CODICE_FASCICOLO}</strong></p><p>&nbsp;</p><ul><li>tipologia di procedura: {TIPO_PRECEDIMENTO}</li><li>richiedente: {NOME_COGNOME_RICHIEDENTE} </li><li>oggetto dell’intervento: {OGGETTO} </li><li>comune/i di intervento: {COMUNE} </li><li>data rilascio: {DATA_PROVVEDIMENTO} </li></ul><p><br></p><p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>','S','S','ED_,REG_','TRASM_PROVV','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'ASSEGNAMENTO_FASCICOLO','Assegnazione fascicolo','Assegnazione fascicolo','Notifica di assegnazione pratica {CODICE_FASCICOLO}','<p>La pratica in oggetto è stata assegnata all’utente {NOME_COGNOME_USER_ASSEGNATARIO}. </p><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>','S','S','ED_,REG_,SBAP_,ETI','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'DISASSEGNAMENTO_FASCICOLO','Assegnazione fascicolo','Revoca assegnazione fascicolo','Notifica di revoca assegnazione pratica {CODICE_FASCICOLO}','<p>La pratica in oggetto NON risulta più assegnata all’utente {NOME_COGNOME_USER_ASSEGNATARIO}. </p><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>','S','S','ED_,REG_,SBAP_,ETI','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'ULTER_DOC_ED','Ulteriore Documentazione per Ente Delegato','Ulteriore Documentazione per Ente Delegato','Ente Delegato Fascicolo {CODICE_FASCICOLO} documentazione allegata','<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p><ul><li>Nome file: {NOME_FILE_ULTERIORE_DOC} (<a href="{LINK_FILE_ULTERIORE_DOC}" rel="noopener noreferrer" target="_blank">clicca qui</a>)</li><li>Titolo: {TITOLO_FILE_ULTERIORE_DOC}</li><li>Descrizione: {DESCRIZIONE_FILE_ULTERIORE_DOC}</li></ul><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>','S','N','ED_,REG_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'ULTER_DOC_ET','Ulteriore Documentazione per Ente Territoriale','Ulteriore Documentazione per Ente Territoriale','Ente Territoriale Fascicolo {CODICE_FASCICOLO} documentazione allegata','<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p><ul><li>Nome file: {NOME_FILE_ULTERIORE_DOC} (<a href="{LINK_FILE_ULTERIORE_DOC}" rel="noopener noreferrer" target="_blank">clicca qui</a>)</li><li>Titolo: {TITOLO_FILE_ULTERIORE_DOC}</li><li>Descrizione: {DESCRIZIONE_FILE_ULTERIORE_DOC}</li></ul><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>','S','N','ETI_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile) VALUES (0,'ULTER_DOC_SOP','Ulteriore Documentazione per Soprintendenza','Ulteriore Documentazione per Soprintendenza','Soprintendenza Fascicolo {CODICE_FASCICOLO} documentazione allegata','<p>Si comunica che è disponibile la seguente documentazione alla pratica in oggetto.</p><p></p><ul><li>Nome file: {NOME_FILE_ULTERIORE_DOC} (<a href="{LINK_FILE_ULTERIORE_DOC}" rel="noopener noreferrer" target="_blank">clicca qui</a>)</li><li>Titolo: {TITOLO_FILE_ULTERIORE_DOC}</li><li>Descrizione: {DESCRIZIONE_FILE_ULTERIORE_DOC}</li></ul><p><br></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>','S','N','SBAP_','','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,RUP,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false);
;

