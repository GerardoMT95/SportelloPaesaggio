create table "presentazione_istanza"."sospensione_archiviazione_attivazione"
(
	"id" bigserial primary key,
	"id_pratica" uuid not null references "presentazione_istanza"."pratica" on delete cascade,
	"type" varchar(50) not null,
	"draft" boolean not null,
	"note" varchar(2000),
	"data" timestamp without time zone,
	"username_utente_creazione" varchar not null,
	"stato_pratica" varchar(100) default null
);

comment on column "presentazione_istanza"."sospensione_archiviazione_attivazione"."type" is 'I valori ammessi per questa colonna sono ''ATTIVA'', ''SOSPENDI'', ''ARCHIVIA''';

create table "presentazione_istanza"."allegato_sosp_arc_att" 
(
    "id_allegato" uuid references "presentazione_istanza"."allegati" on delete cascade,
    "id_sosp_arc_att" bigint references "presentazione_istanza"."sospensione_archiviazione_attivazione" on delete cascade,
    constraint "allegato_sosp_arc_att_pk" primary key("id_allegato","id_sosp_arc_att")
);

insert into "presentazione_istanza"."tipo_contenuto"("id", "descrizione", "descrizione_estesa", "sezione", "multiple")
values (800, 'REQ_ASR', 'Richiesta archiviazione, sospensione, riattivazione pratica', 'REQ_ASR', true);
	   
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id")
values (1, 800), (2, 800), (3, 800), (4, 800);

alter table "presentazione_istanza"."pratica" add column "ultimo_stato_valido" varchar(100);