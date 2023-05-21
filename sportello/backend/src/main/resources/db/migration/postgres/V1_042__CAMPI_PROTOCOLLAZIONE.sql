alter table "presentazione_istanza"."pratica" add column "numero_protocollo" varchar(100) default null;
alter table "presentazione_istanza"."pratica" add column "data_protocollo" timestamp without time zone default null;

comment on column "presentazione_istanza"."pratica"."stato" is 'COMPILA_DOMANDA, GENERA_STAMPA_DOMANDA, ALLEGA_DOCUMENTI_SOTTOSCRITTI, IN_ATTESA_DI_PROTOCOLLAZIONE, NON_ASSEGNATA, IN_PROTOCOLLAZIONE, IN_PREISTRUTTORIA, IN_LAVORAZIONE, RELAZIONE_DA_TRASMETTERE, ATTESA_PARERE_SOPRINTENDENZA, IN_TRASMISSIONE, SOSPESA, ARCHIVIATA, TRANSMITTED';