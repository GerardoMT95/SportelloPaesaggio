alter table "comuni_competenza" 
	add column "vincolo_art_38"  varchar not null default 'ERRORE! Impossibile trovare informazioni in merito all''art.38  delle NTA del PPTR',
	add column "vincolo_art_100" varchar not null default 'ERRORE! Impossibile trovare informazioni in merito all''art.100 delle NTA del PPTR',
	add column "notifica" boolean,
	add column "data_accettazione_cambio_vincoli" timestamp;
	
comment on column "presentazione_istanza"."comuni_competenza"."notifica" is 'non più usato';


alter table "parchi_paesaggi_immobili"
	add column "tipo_variazione" char,
	add column "notifica" boolean,
	add column "data_accettazione_cambio_vincolo" timestamp;

comment on column "presentazione_istanza"."parchi_paesaggi_immobili"."notifica" is 'non più usato';
comment on column "presentazione_istanza"."parchi_paesaggi_immobili"."tipo_variazione" is 'I = inserimento di un nuovo vincolo 
R = rimozione di un vincolo preesistente 
';
