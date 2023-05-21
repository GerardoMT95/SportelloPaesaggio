CREATE INDEX template_email_sezione_idx ON "presentazione_istanza"."template_email" ("sezione","id_organizzazione","codice");

--template seduta di commissione convocazione
insert into "presentazione_istanza"."template_email" ("id_organizzazione","codice","nome","descrizione","oggetto","testo","readonly_oggetto","readonly_testo","visibilita","sezione","tipi_procedimento_ammessi","protocollazione","placeholders","cancellabile") 
values(0,'CONV_SED_COM','Convocazione nuova seduta di commissione','Template della comunicazione inviata al momento della convocazione di una nuova seduta di commissione','Convocazione per la seduta di commissione ''{NOME_SEDUTA}''',
		'<div>
				<p>
					Con la presente si comunica la convocazione per la seduta di commissione ''{NOME_SEDUTA}'' che si terrà in data {DATA_SEDUTA}
					alle ore {{ORARIO_SEDUTA}}.
				</p>
				<p>
					Il Responsabile del Procedimento <br />
					(arch./ing./ geom. {RUP})
				</p>
		 </div>','S','N',',ED_,','SED_COM',',1,2,','N','NOME_SEDUTA, DATA_SEDUTA, ORARIO_SEDUTA, PRATICHE_DA_ESAMINARE, RUP, MEMBRI_COMMISSIONE',false);
		 
--template seduta di commissione aggiornamento
insert into "presentazione_istanza"."template_email" ("id_organizzazione","codice","nome","descrizione","oggetto","testo","readonly_oggetto","readonly_testo","visibilita","sezione","tipi_procedimento_ammessi","protocollazione","placeholders","cancellabile") 
values(0,'AGG_SED_COM','Aggiornamento seduta di commissione','Template della comunicazione inviata per notificare l''aggiornamento di una seduta di commissione','Convocazione per la seduta di commissione ''{NOME_SEDUTA}''',
		'<div>
				<p>
					Con la presente si comunica la convocazione per la seduta di commissione ''{NOME_SEDUTA}'' che si terrà in data {DATA_SEDUTA}
					alle ore {{ORARIO_SEDUTA}}.
				</p>
				<p>
					Il Responsabile del Procedimento <br />
					(arch./ing./ geom. {RUP})
				</p>
		 </div>','S','N',',ED_,','SED_COM',',1,2,','N','NOME_SEDUTA, DATA_SEDUTA, ORARIO_SEDUTA, PRATICHE_DA_ESAMINARE, RUP, MEMBRI_COMMISSIONE ',false);
	 
update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,',
	"tipi_procedimento_ammessi" = ',1,3,'
where "codice" = 'RICH_PAR_SOP_11';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,'
where "codice" = '14';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,'
where "codice" = '15';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,'
where "codice" = '16';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,'
where "codice" = '17';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,'
where "codice" = '18';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,',
	"tipi_procedimento_ammessi" = ',1,3,'
where "codice" = 'RICH_PAR_SOP_12';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,',
	"tipi_procedimento_ammessi" = ',5,6,'
where "codice" = 'RICH_PAR_SOP_13';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,'
where "codice" = 'INVIO_TRASMISSIONE';

update "presentazione_istanza"."template_email"
set "visibilita" = ',SBAP_,',
	"tipi_procedimento_ammessi" = ',5,6,'
where "codice" = 'RICH_PAR_SOP_21';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,',
	"tipi_procedimento_ammessi" = ',5,6,'
where "codice" = 'RICH_PAR_SOP_22';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,',
	"tipi_procedimento_ammessi" = ',2,4,'
where "codice" = 'RICH_PAR_SOP_31';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,',
	"tipi_procedimento_ammessi" = ',2,4,'
where "codice" = 'RICH_PAR_SOP_32';

update "presentazione_istanza"."template_email"
set "visibilita" = ',SBAP_,'
where "codice" = 'COM_IMM_PAR_SOP_10';

update "presentazione_istanza"."template_email"
set "visibilita" = ',SBAP_,'
where "codice" = 'GEN_SOP';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,'
where "codice" = 'GEN_ED';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ETI_,'
where "codice" = 'GENERIC_TER';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,'
where "codice" = '13';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,SBAP_,ETI,'
where "codice" = 'ASSEGNAMENTO_FASCICOLO';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,SBAP_,ETI,'
where "codice" = 'DISASSEGNAMENTO_FASCICOLO';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,'
where "codice" = 'ULTER_DOC_ED';

update "presentazione_istanza"."template_email"
set "visibilita" = ',ETI_,'
where "codice" = 'ULTER_DOC_ET';

update "presentazione_istanza"."template_email"
set "visibilita" = ',SBAP_,'
where "codice" = 'ULTER_DOC_SOP';