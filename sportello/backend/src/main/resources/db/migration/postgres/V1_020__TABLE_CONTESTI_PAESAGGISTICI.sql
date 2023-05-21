create table "presentazione_istanza"."ulteriori_contesti_paesaggistici"(
	"codice" varchar(255) primary key,
	"label" varchar not null,
	"art1" varchar(255),
	"definizione" varchar(255),
	"disposizioni" varchar(500),
	"art2" varchar(255),
	"type" varchar(255),
	"hasText" boolean,
	"data_inizio_val" date default CURRENT_TIMESTAMP::date,
	"data_fine_val" date default null,
	"gruppo" varchar(255) 
		references "presentazione_istanza"."ulteriori_contesti_paesaggistici"
);
comment on table "presentazione_istanza"."ulteriori_contesti_paesaggistici" is 'tipologica che contiene i dati per popolare la tabella degli ulteriori contesti paesaggistici in frot end';

drop table "presentazione_istanza"."pptr_selezioni";

create table "presentazione_istanza"."pptr_selezioni"(
	"id_pratica" uuid
		references "presentazione_istanza"."pratica",
	"codice" varchar(255)
		references "presentazione_istanza"."ulteriori_contesti_paesaggistici",
	"specifica" varchar(1000),
	primary key("id_pratica", "codice")
);
comment on table "presentazione_istanza"."pptr_selezioni" is 'Tabella contenenti i valori scelti nel tab "pptr" di scheda tecnica';
