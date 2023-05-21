--Dopo aver appurato che siano installate le estensioni POSTGIS (da me su windows con StackBuilder di 
--Postgres 10.10 ho installato la versione PostGIS 3.0.1
-- deve avere i diritti da superuser

--SET search_path TO default;

CREATE TABLE layer_geo
(oid bigserial NOT NULL
,id_fascicolo  bigint
,user_id       varchar(100)
,update_user_id varchar(100)
,date_created date
,date_updated date
,is_custom     integer
,is_particella boolean
,geometry      geometry(MultiPolygonZ,32633)
,CONSTRAINT layer_geo_pk PRIMARY KEY (oid)
,CONSTRAINT "layer_geo_fk" FOREIGN KEY ("id_fascicolo") REFERENCES "fascicolo"("id") 
ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX layer_geo_idx_1 on layer_geo(id_fascicolo);
CREATE INDEX layer_geo_idx_2 on layer_geo USING GIST (geometry);

CREATE TABLE layer_shape
(id_shape bigint not null
,id_geo   bigint not null
,PRIMARY KEY (id_shape, id_geo)
,CONSTRAINT "layer_shape_fk_1" FOREIGN KEY (id_shape) REFERENCES allegato(id) 
,CONSTRAINT "layer_shape_fk_2" FOREIGN KEY (id_geo) REFERENCES layer_geo(oid) 
ON UPDATE CASCADE ON DELETE CASCADE
);

create or replace view "layer_view_editing"
as 
select 
	f.*
   ,g.is_custom
   ,g.is_particella
   ,g.geometry
   ,g.oid
   ,g.user_id
   ,g.date_created
from
(
	select 
	  f.id as id_fascicolo
	 ,f.codice as codice
	 ,f.oggetto_intervento as oggetto_intervento
	from fascicolo f
) f	 
 inner join layer_geo g on f.id_fascicolo = g.id_fascicolo
;

--quella pubblica filtra lo stato...
create or replace view "layer_view_published"
as 
select 
	f.*
   ,g.is_custom
   ,g.is_particella
   ,g.geometry
   ,g.oid
   ,g.user_id
   ,g.date_created
from
(
	select 
	  f.id as id_fascicolo
	  ,f.codice as codice
	 ,f.oggetto_intervento as oggetto_intervento
	from fascicolo f
	where f.stato NOT in ('WORKING','CANCELED','DELETED')
) f	 
 inner join layer_geo g on f.id_fascicolo = g.id_fascicolo
;
