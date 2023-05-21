CREATE TABLE presentazione_istanza.cds_allegati_inviati (
	id_pratica uuid NOT NULL,
	id_cds int8 NULL,
	id_allegato uuid NOT NULL,
	date_create timestamp NOT NULL DEFAULT now(),
	user_create varchar(100) NULL,
	date_update timestamp NULL,
	user_update varchar(100) NULL,
	CONSTRAINT cds_allegati_inviati_pkey PRIMARY KEY (id_pratica,id_cds,id_allegato),
	CONSTRAINT pratica_cds_fk FOREIGN KEY (id_pratica) REFERENCES presentazione_istanza.pratica(id),
	CONSTRAINT allegato_fk FOREIGN KEY (id_allegato) REFERENCES presentazione_istanza.allegati(id)
);

comment on table  pratica_cds                                is 'Allegati inviati alla cds, serve per non reinviare più volte lo stesso allegato';
comment on column pratica_cds.id_pratica                     is 'FK su Pratica';
comment on column pratica_cds.date_create                    is 'Data creazione record';
comment on column pratica_cds.user_create                    is 'Utente creazione record';
comment on column pratica_cds.date_update                    is 'Data ultimo aggiornamento record';
comment on column pratica_cds.user_update                    is 'Utente ultimo aggiornamento record';
comment on column pratica_cds.id_cds 						 is 'Id conferenza su meetpad';

