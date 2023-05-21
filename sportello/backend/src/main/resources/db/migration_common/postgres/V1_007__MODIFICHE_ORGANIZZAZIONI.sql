ALTER TABLE "paesaggio_organizzazione" ADD "denominazione"    varchar(200);
ALTER TABLE "paesaggio_organizzazione" RENAME COLUMN "tipo_ente_delegato" TO "tipo_organizzazione_specifica";

ALTER TABLE "paesaggio_organizzazione_competenze" ADD "codice_civilia" varchar(20);

ALTER TABLE "paesaggio_email" DROP COLUMN "id_competenze_territorio";
ALTER TABLE "paesaggio_email" DROP COLUMN "id_ente";
ALTER TABLE "paesaggio_email" ADD "organizzazione_id" integer references "common"."paesaggio_organizzazione";
ALTER TABLE "paesaggio_email" ADD "ente_id" 		  integer references "common"."ente";

ALTER TABLE "paesaggio_organizzazione" ALTER COLUMN "ente_id" drop not null;


UPDATE "paesaggio_organizzazione" 
SET denominazione = CONCAT(tipo_organizzazione,'_',descrizione)
FROM "ente"
WHERE "paesaggio_organizzazione".ente_id = "ente".id;


INSERT INTO "paesaggio_email"(codice_applicazione, email, denominazione, pec, organizzazione_id, ente_id)
SELECT  'AUTPAE',
		CONCAT('TEST_',denominazione,'@TEST_puglia.com'),
		denominazione,
		false,
		id,
		null
FROM    "paesaggio_organizzazione";


INSERT INTO "paesaggio_email"(codice_applicazione, email, denominazione, pec, organizzazione_id, ente_id)
SELECT  'AUTPAE',
		CONCAT('TEST_ENTE_',descrizione,'@TEST_puglia.com'),
		descrizione,
		true,
		null,
		id
FROM    "ente";

UPDATE "paesaggio_email" SET email = replace(email, ' -', '-');
UPDATE "paesaggio_email" SET email = replace(email, '- ', '-');
UPDATE "paesaggio_email" SET email = replace(email, '`' , ' ');
UPDATE "paesaggio_email" SET email = replace(email, '''', ' ');
UPDATE "paesaggio_email" SET email = replace(email, ' ' , '_');
UPDATE "paesaggio_email" SET email = replace(email, ',' , '_');
UPDATE "paesaggio_email" SET email = replace(email, '__', '_');
