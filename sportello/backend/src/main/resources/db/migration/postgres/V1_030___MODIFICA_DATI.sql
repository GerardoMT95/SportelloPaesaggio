update "presentazione_istanza"."tipi_e_qualificazioni"
set "dependency" = null
where "codice" = 'DPR_31_2017_2';

update "presentazione_istanza"."tipi_e_qualificazioni"
set "key" = null
where "codice" = 'DPR_31_2017_1';

update "presentazione_istanza"."ulteriori_contesti_paesaggistici"
set "type" = 'unselectable'
where "codice" in ('PARCHI_E_RISERVE', 'AREE_NOTEVOLE_INSTERESSE_PUBB', 'PAES_RUR');

alter table "presentazione_istanza"."ulteriori_contesti_paesaggistici"
drop column "data_inizio_val";

alter table "presentazione_istanza"."ulteriori_contesti_paesaggistici"
drop column "data_fine_val";

create table "presentazione_istanza"."ucp_tipo_procedimento"(
	"id" bigserial primary key,
	"id_tipo_procedimento" integer not null
		references "presentazione_istanza"."tipo_procedimento",
	"codice_ucp" varchar(255) not null
		references "presentazione_istanza"."ulteriori_contesti_paesaggistici",
	"data_inizio_val" date default '01/01/1950'::date,
	"data_fine_val" date default null
);