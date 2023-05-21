ALTER TABLE "protocollo"  ADD COLUMN "request" text;
ALTER TABLE "protocollo"  ADD COLUMN "response" text;
ALTER TABLE "protocollo"  ADD COLUMN "protocollo" character varying(100);
ALTER TABLE "protocollo"  ADD COLUMN "verso" character varying(4);
ALTER TABLE "protocollo"  ADD COLUMN "data_esecuzione" timestamp;
ALTER TABLE "protocollo"  ADD COLUMN "id_allegato" uuid;
ALTER TABLE "protocollo"  ADD COLUMN "id_pratica" uuid;
ALTER TABLE "protocollo" ADD CONSTRAINT "fkey_allegati" FOREIGN KEY ("id_allegato")
        REFERENCES "allegati" ("id") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;
        
ALTER TABLE "protocollo" ADD CONSTRAINT "fkey_pratica" FOREIGN KEY ("id_pratica")
        REFERENCES "pratica" ("id") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;        