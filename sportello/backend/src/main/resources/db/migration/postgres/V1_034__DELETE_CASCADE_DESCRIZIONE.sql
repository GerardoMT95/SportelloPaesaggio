alter table "presentazione_istanza"."descrizione_scheda_tecnica_values"
	drop constraint "descrizione_scheda_tecnica_values_pratica_id_fkey";
alter table "presentazione_istanza"."descrizione_scheda_tecnica_values"
	add foreign key("pratica_id") references "presentazione_istanza"."pratica" on delete cascade;

alter table "presentazione_istanza"."tipo_intervento"
	drop constraint "tipo_intervento_id_pratica_fkey";
alter table "presentazione_istanza"."tipo_intervento"
	add foreign key("id_pratica") references "presentazione_istanza"."pratica" on delete cascade;
	
alter table "presentazione_istanza"."pptr_selezioni"
	drop constraint "pptr_selezioni_id_pratica_fkey";
alter table "presentazione_istanza"."pptr_selezioni"
	add foreign key("id_pratica") references "presentazione_istanza"."pratica" on delete cascade;	