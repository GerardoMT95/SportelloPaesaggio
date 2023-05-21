-- Modifica per Sospensione/archiviazione/attivazione pratica
alter table "presentazione_istanza"."sospensione_archiviazione_attivazione"
	alter column "type" drop not null;

-- Modifica per Sospensione/archiviazione/attivazione pratica
alter table "presentazione_istanza"."allegati"
	alter column "pratica_id" drop not null;
	
-- Realizzazione abella per sedute di commissione
create table "presentazione_istanza"."seduta_commissione"
(
	"id" bigserial primary key,
	"id_organizzazione" int4 not null,
	"id_ente_delegato"	int4 not null,
	"nome_seduta" varchar(255) not null,
	"data_seduta" timestamp not null,
	"username_utente_creazione" varchar(255),
	"data_creazione" timestamp without time zone default current_date,
	"pubblica" boolean,
	"stato" varchar default 'IN_BOZZA'
);

comment on column "presentazione_istanza"."seduta_commissione"."stato" is 'I valori che il campo può assumere sono: ''IN_BOZZA'', ''PUBBLICATA'', ''CONCLUSA''';

create table "presentazione_istanza"."seduta_pratica"
(
	"id_seduta" bigint references "presentazione_istanza"."seduta_commissione" on delete cascade,
	"id_pratica" uuid references "presentazione_istanza"."pratica" on delete cascade,
	primary key("id_seduta", "id_pratica")
);

create table "presentazione_istanza"."seduta_allegati"
(
	"id_seduta" bigint references "presentazione_istanza"."seduta_commissione" on delete cascade,
	"id_allegato" uuid references "presentazione_istanza"."allegati" on delete cascade,
	
	primary key("id_seduta", "id_allegato")
);

create table "presentazione_istanza"."seduta_pratica_allegati"
(
	"id_allegato" uuid references "presentazione_istanza"."allegati" on delete cascade,
	"id_seduta" bigint references "presentazione_istanza"."seduta_commissione" on delete cascade,
	"id_pratica" uuid references "presentazione_istanza"."pratica" on delete cascade,
	
	primary key("id_seduta", "id_pratica", "id_allegato"),
	foreign key("id_seduta", "id_pratica")  references "presentazione_istanza"."seduta_pratica" on delete cascade,
	foreign key("id_seduta", "id_allegato") references "presentazione_istanza"."seduta_allegati" on delete cascade
);

insert into "presentazione_istanza"."tipo_contenuto"("id", "descrizione", "descrizione_estesa", "sezione", "multiple")
values (900, 'VERB_SCL'	, 'Verbale commissione locale', 'COM_LOC', true),
	   (901, 'ST_SCL'	, 'Scheda tecnica commissione locale', 'COM_LOC', true),
	   (902, 'ALTRO_SCL', 'Altro', 'COM_LOC', true);
	   
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id")
values (1, 900), (2, 900), (3, 900), (4, 900),
	   (1, 901), (2, 901), (3, 901), (4, 901),
	   (1, 902), (2, 902), (3, 902), (4, 902);