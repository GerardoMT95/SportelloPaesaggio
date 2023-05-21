create table "presentazione_istanza"."corrispondenza"
(
	"id" bigserial primary key,
    "id_eml_cmis" character varying,
    "message_id" character varying,
    "mittente_email" character varying not null,
    "mittente_denominazione" character varying not null,
    "mittente_username" character varying not null,
    "mittente_ente" character varying not null,
    "data_invio" timestamp without time zone,
    "data_creazione" timestamp without time zone not null default current_date,
    "oggetto" character varying not null,
    "stato" boolean not null,
    "comunicazione" boolean not null,
    "bozza" boolean not null,
    "text" text
);

create table "presentazione_istanza"."fascicolo_corrispondenza"
(
	"id_corrispondenza" bigint references "presentazione_istanza"."corrispondenza" on delete cascade,
	"id_pratica" uuid references "presentazione_istanza"."pratica" on delete cascade,
	constraint "presentazione_istanza_pk" primary key("id_corrispondenza", "id_pratica")
);

create table "presentazione_istanza"."allegato_corrispondenza" 
(
    "id_allegato" uuid references "presentazione_istanza"."allegati" on delete cascade,
    "id_corrispondenza" bigint references "presentazione_istanza"."corrispondenza" on delete cascade,
    constraint "allegato_corrispondenza_pk" primary key("id_allegato","id_corrispondenza")
);

create table "presentazione_istanza"."destinatario"
(
	"id" bigserial primary key,
	"type" character varying(2) not null,
	"email" character varying not null,
	"stato" character varying(50),
	"pec" boolean not null,
	"denominazione" character varying(255),
	"data_ricezione" timestamp without time zone,
	"id_corrispondenza" bigint references "presentazione_istanza"."corrispondenza" on delete cascade not null 
);

comment on column "presentazione_istanza"."destinatario"."type" is 'TO, CC';

create table "presentazione_istanza"."ricevuta"
(
	"id" bigserial primary key,
	"id_corrispondenza" bigint references "presentazione_istanza"."corrispondenza" on delete cascade,
	"id_destinatario" bigint references "presentazione_istanza"."destinatario" on delete cascade,
	"tipo_ricevura" character varying not null,
	"errore" character varying,
	"descrizione_errore" character varying,
	"id_cms_eml" character varying,
	"id_cms_dati_cert" character varying,
	"id_cms_smime" character varying,
	"data" timestamp without time zone
);

