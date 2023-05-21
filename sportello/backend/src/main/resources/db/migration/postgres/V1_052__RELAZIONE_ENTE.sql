CREATE TABLE "presentazione_istanza"."relazione_ente"
(
    "id_relazione_ente"			bigserial primary key,
    "id_pratica" 				uuid not null references "presentazione_istanza"."pratica" on delete cascade,
    "numero_protocollo_ente"	character varying ,
    "nominativo_istruttore"		character varying ,
    "dettaglio_relazione"		text,
    "nota_interna"				text,
    "id_corrispondenza"			bigint
);

CREATE TABLE "presentazione_istanza"."allegati_relazione_ente"
(
	"id_allegato" uuid references "presentazione_istanza"."allegati" on delete cascade,
    "id_relazione_ente" bigint references "presentazione_istanza"."relazione_ente" on delete cascade,
    constraint "allegati_relazione_ente_pk" primary key("id_allegato","id_relazione_ente")
);
ALTER TABLE presentazione_istanza.relazione_ente ADD CONSTRAINT relazione_ente_un UNIQUE (id_pratica);


INSERT INTO presentazione_istanza.tipo_contenuto (id,descrizione,descrizione_estesa,sezione)
	VALUES (1000,'REL_TEC_ILL','Relazione tecnica illustrata','RELAZIONE_ENTE');
INSERT INTO presentazione_istanza.tipo_contenuto (id,descrizione,descrizione_estesa,sezione)
	VALUES (1001,'OTHER','Altri','OTHER');