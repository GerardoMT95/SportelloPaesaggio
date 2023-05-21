alter table "presentazione_istanza"."tipo_contenuto"
	add column "multiple" boolean not null default true;
	
update "presentazione_istanza"."tipo_contenuto"
set "multiple" = false
where "id"='500';