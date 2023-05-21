CREATE TABLE "log_protocollo"
(
    "id" bigserial NOT NULL,
    "request" text,
    "response" text,
    "protocollo" character varying(100),
    "verso" character varying(4),
    "data_protocollo" timestamp,
    "data_esecuzione" timestamp,
    "id_allegato" bigint,
    PRIMARY KEY ("id"),
    CONSTRAINT "fkey_allegato" FOREIGN KEY ("id_allegato")
        REFERENCES "allegato" ("id") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
