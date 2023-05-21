ALTER TABLE template_email
    ADD COLUMN riservata boolean DEFAULT true;

COMMENT ON COLUMN template_email.riservata
    IS 'indica se la corrispondenza è visibile da parte del richiedente nell''as-is vengono divise in riservate e non e le non riservate contengono la mail di ricezione istanza ';
UPDATE template_email SET riservata=false where codice  like 'TEMP%';


-- Auto-generated SQL script #202010081638
INSERT INTO template_email (id_organizzazione,codice,nome,descrizione,oggetto,testo,readonly_oggetto,readonly_testo,visibilita,sezione,tipi_procedimento_ammessi,protocollazione,placeholders,cancellabile,riservata)
	VALUES (0,'RIC_ISTANZA','Conferma ricezione istanza','Conferma ricezione istanza','Ricezione - {TIPO_PROCEDIMENTO} - {CODICE_FASCICOLO}',
		'Si informa che in data {DATA_PRESENTAZIONE} è stata ricevuta l''istanza con codice: <br>

{CODICE_PRATICA}<br>

per il tipo procedimento autorizzativo:<br>

{TIPO_PROCEDIMENTO}<br>

avente ad oggetto:<br>

{OGGETTO}<br>

acquisita con protocollo:<br>

{NUM_PROTOCOLLO_ISTANZA}  del {DATA_PROTOCOLLO_ISTANZA}<br>
<br>
 

<p>Cordiali saluti.</p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>','S','N','SBAP_','RIC_ISTANZA','','N','CODICE_FASCICOLO,TIPO_PROCEDIMENTO,OGGETTO,DATA_PRESENTAZIONE,COMUNE,ENTE_PROCEDENTE,NOME_COGNOME_RICHIEDENTE,NUM_PROTOCOLLO_ISTANZA,DATA_PROTOCOLLO_ISTANZA',false,false);


ALTER TABLE corrispondenza
    ADD COLUMN id_organizzazione_owner integer;

COMMENT ON COLUMN corrispondenza.id_organizzazione_owner
    IS 'id dell''organizzazione owner della mail';

ALTER TABLE corrispondenza
    ADD COLUMN visibilita character varying(255);

COMMENT ON COLUMN corrispondenza.visibilita
    IS 'eventuale lista di tipi organizzazione su cui è visibile la mail (oltre l''owner) es. 
ED_,SBAP_,ETI_';

ALTER TABLE corrispondenza
    ADD COLUMN tipo_organizzazione_owner character varying(10);

COMMENT ON COLUMN corrispondenza.tipo_organizzazione_owner
    IS 'ED_   oppure SBAP_  oppure REG_ etc...';

ALTER TABLE corrispondenza
    ADD COLUMN codice_template character varying(255);

ALTER TABLE corrispondenza
    ADD COLUMN riservata boolean;

COMMENT ON COLUMN corrispondenza.riservata
    IS 'stessa definizione del template ovvero sono tutte riservate tranne quelle visibili al richiedente.';
    
UPDATE template_email set sezione='ASSEGN_FASC' where codice='ASSEGNAMENTO_FASCICOLO';
UPDATE template_email set sezione='DISASS_FASC' where codice='DISASSEGNAMENTO_FASCICOLO';    