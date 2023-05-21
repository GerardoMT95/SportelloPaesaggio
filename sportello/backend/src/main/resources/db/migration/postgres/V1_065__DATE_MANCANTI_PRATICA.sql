alter table "presentazione_istanza"."pratica"
	add column "data_inizio_lavorazione" timestamp without time zone default null,
	add column "data_trasmissione_verbale" timestamp without time zone default null,
	add column "data_trasmissione_relazione" timestamp without time zone default null,
	add column "data_trasmissione_parere" timestamp without time zone default null;
	
alter table "presentazione_istanza"."corrispondenza"
	alter column "tipo_organizzazione_owner" type varchar(20);