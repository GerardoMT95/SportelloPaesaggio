ALTER TABLE "presentazione_istanza"."layer_geo" 
	ADD COLUMN user_id character varying(100);
ALTER TABLE "presentazione_istanza"."layer_geo"
    ADD COLUMN update_user_id character varying(100);
ALTER TABLE "presentazione_istanza"."layer_geo"    
    ADD COLUMN date_created date;
ALTER TABLE "presentazione_istanza"."layer_geo"    
    ADD COLUMN date_updated date;

CREATE TABLE "presentazione_istanza"."layer_shape"
(id_shape uuid not null
,id_geo   bigint not null
,PRIMARY KEY (id_shape, id_geo)
,CONSTRAINT "layer_shape_fk_1" FOREIGN KEY (id_shape) REFERENCES "presentazione_istanza"."allegati"(id) 
,CONSTRAINT "layer_shape_fk_2" FOREIGN KEY (id_geo) REFERENCES "presentazione_istanza"."layer_geo"(oid) 
ON UPDATE CASCADE ON DELETE CASCADE
);

create or replace view "presentazione_istanza"."layer_view_editing"
as 
select 
	f.*
   ,g.is_custom
   ,g.is_particella
   ,g.geom
   ,g.oid
   ,g.user_id
   ,g.date_created
from
(
	select 
	  f.id as id_fascicolo
	 ,f.codice_pratica_appptr as codice
	 ,f.oggetto as oggetto_intervento
	from "presentazione_istanza"."pratica" f
) f	 
 inner join "presentazione_istanza"."layer_geo" g on f.id_fascicolo = g.id_fascicolo
;

--quella pubblica filtra lo stato...
create or replace view "presentazione_istanza"."layer_view_published"
as 
select 
	f.*
   ,g.is_custom
   ,g.is_particella
   ,g.geom
   ,g.oid
   ,g.user_id
   ,g.date_created
from
(
	select 
	  f.id as id_fascicolo
	  ,f.codice_pratica_appptr as codice
	 ,f.oggetto as oggetto_intervento
	from "presentazione_istanza"."pratica" f
	where f.stato  in ('TRANSMITTED')
) f	 
 inner join "presentazione_istanza"."layer_geo" g on f.id_fascicolo = g.id_fascicolo
;


ALTER TABLE "presentazione_istanza"."particelle_catastali"
	ADD COLUMN "oid" 	 int8;
ALTER TABLE "presentazione_istanza"."particelle_catastali"	
	ADD COLUMN	"note"	 varchar(255);