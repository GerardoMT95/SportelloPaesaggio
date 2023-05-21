--Dopo aver appurato che siano installate le estensioni POSTGIS (da me su windows con StackBuilder di 
--Postgres 10.10 ho installato la versione PostGIS 3.0.1
-- l'utente che esegue il flyway deve avere i diritti da superuser
--SET search_path TO public;
--CREATE extension IF NOT EXISTS postgis;

--UPDATE pg_extension 
--  SET extrelocatable = TRUE 
--    WHERE extname = 'postgis';

--ALTER EXTENSION postgis 
--  SET SCHEMA presentazione_istanza;

--ALTER EXTENSION postgis 
--  UPDATE;

--SET search_path TO public;

CREATE TABLE presentazione_istanza.layer_geo
(
  oid bigserial NOT NULL ,
  id_fascicolo UUID,
  name text,
  cat text,
  is_custom integer,
  is_particella boolean,
  geom geometry(MultiPolygonZ,32633),
  CONSTRAINT prova_pkey PRIMARY KEY (oid),
  CONSTRAINT "pratica_fkey" FOREIGN KEY ("id_fascicolo") 
        REFERENCES "presentazione_istanza"."pratica"("id") ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE presentazione_istanza.layer_geo
  OWNER TO presentazione_istanza;
