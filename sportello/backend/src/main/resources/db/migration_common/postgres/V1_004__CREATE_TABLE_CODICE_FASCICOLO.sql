CREATE TABLE "paesaggio_codice_fascicolo"
(
    "codice_ente" character varying(20) NOT NULL,
    "anno" integer NOT NULL,
	"prefisso" character varying(10) NOT NULL,
    "seriale" integer NOT NULL,
     CONSTRAINT "codice_fascicolo_pkey" PRIMARY KEY ("codice_ente", "anno", "prefisso")
);