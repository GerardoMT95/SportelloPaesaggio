create table "presentazione_istanza"."integrazione"(
	"id" serial primary key,
	"data_creazione" timestamp without time zone default current_date,
	"data_caricamento" timestamp without time zone,
	"stato" varchar not null,
	"richiesta_integrazione" boolean,
	"username_utente_creazione" varchar,
	"descrizione" varchar(255) not null,
	"note" varchar,
	"pratica_id" uuid references "presentazione_istanza"."pratica" on delete cascade not null 
);

comment on table "presentazione_istanza"."integrazione" is 'Tabella per le integrazioni documentali. Questa tabella verrà popolata con record che rappresenteranno l''integrazione di documenti ad una pratica da parte del richiedente; questa operazione può essere effettuata spontaneamente dal richiedente o meno, l''informazione è registrata in un apposito campo booleano ''richiesta_integrazione''. La tabella è predisposta per salvare informazioni utili ad uno storico come data creazione del record (interpretabile come data richiesta integrazione se non è integrazione spontanea), utente creazone (funzionario che ha effettuato la richiesta se non è integrazione spontanea) e data completamento integrazione.';
comment on column "presentazione_istanza"."integrazione"."stato" is 'IN_BOZZA - l''integrazione in questione si trova in stato di bozza\nIN_ATTESA - l''integrazione in questione si trova in attesa che il documento autogenerato di riepilogo integrazione venga firmato e allegato\nCOMPLETATA - l''integrazione documentale è stata completata\nANNULLATA - se l''integrazione documentale è stata annullata';

alter table "presentazione_istanza"."allegati"
	add column "deleted" boolean default false;

alter table "presentazione_istanza"."allegati"
	add column "integrazione_id" integer references "presentazione_istanza"."integrazione" on delete cascade default null;
	
insert into "presentazione_istanza"."tipo_contenuto"("id", "descrizione", "descrizione_estesa", "sezione", "multiple")
values (702, 'INT_DOC', 'Integrazione documentale', 'INT_DOC', false);
	   
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id")
values (1, 702), (2, 702), (3, 702), (4, 702);