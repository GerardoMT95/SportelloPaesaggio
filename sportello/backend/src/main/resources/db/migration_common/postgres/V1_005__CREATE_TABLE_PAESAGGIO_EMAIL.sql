CREATE TABLE "paesaggio_email"
(
    "id" 				  		bigserial 			   NOT NULL primary key,
    "codice_applicazione" 		character varying(50)  NOT NULL,
    "email" 					character varying(200) NOT NULL,
	"denominazione" 	  		character varying(200) NOT NULL,
    "pec" 				  		boolean,
	"id_competenze_territorio"  integer							references "common"."ente",
	"id_ente" 					integer							references "common"."paesaggio_organizzazione_competenze"
);