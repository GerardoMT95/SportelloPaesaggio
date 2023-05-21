CREATE TABLE "paesaggio_commissione_locale"
(
    "id" 				  	bigserial NOT NULL primary key,
	"id_ente_delegato"  	integer	  NOT NULL references "common"."paesaggio_organizzazione",
	"id_commissione_locale" integer	  NOT NULL references "common"."paesaggio_organizzazione"
);

ALTER TABLE "paesaggio_organizzazione_attributi" ADD "email_commissione_locale" varchar(200);
