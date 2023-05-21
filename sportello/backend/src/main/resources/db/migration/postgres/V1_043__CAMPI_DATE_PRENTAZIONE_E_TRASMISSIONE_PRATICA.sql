alter table "presentazione_istanza"."pratica" add column "data_presentazione" timestamp without time zone default null;
alter table "presentazione_istanza"."pratica" add column "data_trasmissione_provvedimento_finale" timestamp without time zone default null;

comment on column "presentazione_istanza"."pratica"."data_presentazione" is 'data in cui il richiedente ha presentato la pratica';
comment on column "presentazione_istanza"."pratica"."data_trasmissione_provvedimento_finale" is 'data in cui il richiedente è stata effettuata la trasmissione del provvedimento finale (data che verrà visualizzata nella timeline della view del richiedente)';