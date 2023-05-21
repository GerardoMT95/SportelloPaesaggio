create table "presentazione_istanza"."provvedimento_finale"
(
	"id" bigserial primary key,
	"id_pratica" uuid references "presentazione_istanza"."pratica" unique not null,
	"numero_provvedimento" varchar(255),
	"data_rilascio_autorizzazione" timestamp without time zone,
	"esito_provvedimento" varchar,
	"rup" varchar(1000),
	"draft" boolean,
	"id_corrispondenza" bigint references "presentazione_istanza"."corrispondenza"
);

comment on column "presentazione_istanza"."provvedimento_finale"."esito_provvedimento" is 'Può assumere i seguenti valori ''AUTORIZZATO'', ''NON_AUTORIZZATO'', ''AUT_PRESCR''';

insert into "presentazione_istanza"."tipo_contenuto"("id", "descrizione", "descrizione_estesa", "sezione", "multiple")
values (950, 'PROV_FIN_PRV'	   , 'Provvedimento finale (privato)' , 'PROVVEDIMENTO', false),
	   (951, 'PROV_FIN_PUB'	   , 'Provvedimento finale (pubblico)', 'PROVVEDIMENTO', false),
	   (904, 'PARERE_MIBAC_PUB', 'Parere Mibac (pubblico)'		  , 'PARERE_SOPR'  , false);
	   
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id")
values (1, 950), (2, 950), (3, 950), (4, 950), 
	   (1, 951), (2, 951), (3, 951), (4, 951);

update "presentazione_istanza"."tipo_contenuto"
set "descrizione" = 'PARERE_MIBAC_PRV',
	"descrizione_estesa" = 'Parere Mibac (privato)'
where "id" = 903;