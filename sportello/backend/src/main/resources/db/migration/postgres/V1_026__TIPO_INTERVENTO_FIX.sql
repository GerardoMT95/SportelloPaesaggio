alter table "presentazione_istanza"."tipo_intervento"
	drop constraint "tipo_intervento_pkey";
	
alter table "presentazione_istanza"."tipo_intervento"
	add constraint "tipo_intervento_pkey" primary key("id_pratica");
	
alter table "presentazione_istanza"."tipo_intervento"
	alter column "codice" drop not null;