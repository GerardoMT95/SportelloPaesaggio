drop table "presentazione_istanza"."qualificazione_intervento";
drop table "presentazione_istanza"."tipo_intervento";

alter table "presentazione_istanza"."descrizione_scheda_tecnica_values"
	add column "sezione" varchar(255);

alter table "presentazione_istanza"."tipi_e_qualificazioni"
	add column "sezione" varchar(255) default 'DESCR';	

create table "presentazione_istanza"."tipo_intervento"(
	"codice" varchar(255) 
		references "presentazione_istanza"."tipi_e_qualificazioni",
	"id_pratica" uuid 
		references "presentazione_istanza"."pratica",
	"artt" varchar(1000),
	"data_approvazione" date,
	"con" varchar(1000),
	
	primary key ("codice", "id_pratica")
);

comment on table "presentazione_istanza"."tipo_intervento"
is 'Tabella separata per tipo intervento rispetto a quanto presente nel tab ''descrizione'' di scheda tecnica in quanto sono presenti informazioni aggiuntive';


