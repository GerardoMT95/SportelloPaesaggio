alter table "presentazione_istanza"."pratica"
	alter column "stato_seduta_commissione"    set default 'VERBALE_SEDUTA_ASSENTE',
	alter column "stato_relazione_ente" 	   set default 'RELAZIONE_NON_TRASMESSA',
	alter column "stato_parere_soprintendenza" set default 'PARERE_NON_ALLEGATO';

alter table "presentazione_istanza"."parere_soprintendenza"
	drop  column "id_allegato",
	drop  column "data_utente_creazione",
	add   column "dettaglio_prescrizione" varchar,
	add   column "username_utente_creazione" varchar(255),
	add   column "organizzazione_creazione" varchar(10) not null,
	add   column "id_corrispondenza" bigint references "presentazione_istanza"."corrispondenza" on delete set null,
	alter column "esito_parere" drop not null;

create table "presentazione_istanza"."parere_soprintendenza_allegato"
(
	"id_parere" bigint references "presentazione_istanza"."parere_soprintendenza",
	"id_allegato" uuid references "presentazione_istanza"."allegati",
	
	primary key("id_parere", "id_allegato")
);

insert into "presentazione_istanza"."tipo_contenuto"("id", "descrizione", "descrizione_estesa", "sezione", "multiple")
values (905, 'ALLEGATO_COMUNICAZIONE', 'Allegato generico per la comunicazione', 'COMUNICAZIONI', false);
	   
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id")
values (1, 905), (2, 905), (3, 905), (4, 905);
