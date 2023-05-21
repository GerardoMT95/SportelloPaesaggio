insert into "presentazione_istanza"."tipo_contenuto"("id", "descrizione", "descrizione_estesa", "sezione") values('500', 'Dichiarazione d''assenso', 'Allegato dichiarazione d''assenso', 'DICHIARAZIONI_ASSENSO');
insert into "presentazione_istanza"."tipo_contenuto"("id", "descrizione", "descrizione_estesa", "sezione") values('600', 'Shape file', 'Allegato shape file', 'LOCALIZZAZIONE');

insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id") values('1', '500');
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id") values('2', '500');
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id") values('3', '500');
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id") values('4', '500');

insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id") values('1', '600');
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id") values('2', '600');
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id") values('3', '600');
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id") values('4', '600');

alter table "presentazione_istanza"."pratica"
	add column "validazione_istanza" boolean default false;
alter table "presentazione_istanza"."pratica"
	add column "validazione_scheda_tecnica" boolean default false;
alter table "presentazione_istanza"."pratica"
	add column "validazione_allegati" boolean default false;

