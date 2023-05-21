create table "presentazione_istanza"."seduta_corrispondenza"
(
	"id_seduta" bigint references "presentazione_istanza"."seduta_commissione" on delete cascade,
	"id_corrispondenza" integer references "presentazione_istanza"."corrispondenza" on delete cascade,
	
	primary key("id_seduta", "id_corrispondenza")
);

create table "presentazione_istanza"."parere_soprintendenza"
(
	"id" bigserial primary key,
	"id_pratica" uuid references "presentazione_istanza"."pratica" not null unique,
	"numero_protocollo" varchar(50),
	"nominativo_istruttore" varchar(255),
	"esito_parere" varchar(50) not null,
	"note" varchar,
	"data_utente_creazione" varchar(255),
	"data_inserimento" timestamp without time zone default now(),
	"id_allegato" uuid references "presentazione_istanza"."allegati" on delete cascade unique
);

comment on column "presentazione_istanza"."parere_soprintendenza"."esito_parere" is 'Può assumere i seguenti valori ''AUTORIZZATO'', ''NON_AUTORIZZATO'', ''AUT_PRESCR''';

insert into "presentazione_istanza"."tipo_contenuto"("id", "descrizione", "descrizione_estesa", "sezione", "multiple")
values (903, 'PARERE_MIBAC'	, 'Parere Mibac', 'PARERE_SOPR', false);
	   
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id")
values (1, 903), (2, 903), (3, 903), (4, 903);

alter table "presentazione_istanza"."pratica"
	add column "stato_seduta_commissione" varchar(50) default null,
	add column "stato_relazione_ente" varchar(50) default null,
	add column "stato_parere_soprintendenza" varchar(50) default null;