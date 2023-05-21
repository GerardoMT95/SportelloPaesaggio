CREATE TABLE presentazione_istanza.pratica_cds (
	id varchar(36) NOT NULL,
	id_pratica uuid NOT NULL,
	tipo varchar(100) NULL,
	attivita varchar(1000) NULL,
	azione varchar(1000) NULL,
	termine_richiesta_integrazione date NULL,
	termine_pareri date NULL,
	prima_sessione date NULL,
	data_termine date NULL,
	comune_pertinenza varchar(400) NULL,
	provincia_pertinenza varchar(400) NULL,
	indirizzo_pertinenza varchar(400) NULL,
	modalita_incontro varchar(100) NULL,
	link varchar(400) NULL,
	comune varchar(400) NULL,
	provincia varchar(400) NULL,
	cap varchar(400) NULL,
	indirizzo varchar(400) NULL,
	orario varchar(10) NULL,
	date_create timestamp NOT NULL DEFAULT now(),
	user_create varchar(100) NOT NULL,
	date_update timestamp NULL,
	user_update varchar(100) NULL,
	completed bool NOT NULL DEFAULT false,
	id_cds int8 NULL,
	comitato bool NULL DEFAULT false,
	username_creatore varchar(100) NULL,
	nome_creatore varchar(100) NULL,
	cognome_creatore varchar(100) NULL,
	mail_creatore varchar(100) NULL,
	telefono_creatore varchar(100) NULL,
	codice_fiscale_creatore varchar(16) NULL,
	username_responsabile varchar(100) NULL,
	nome_responsabile varchar(100) NULL,
	cognome_responsabile varchar(100) NULL,
	codice_fiscale_responsabile varchar(16) NULL,
	mail_responsabile varchar(100) NULL,
	telefono_responsabile varchar(100) NULL,
	CONSTRAINT pratica_cds_pkey PRIMARY KEY (id),
	CONSTRAINT pratica_cds_fk FOREIGN KEY (id_pratica) REFERENCES presentazione_istanza.pratica(id)
);

comment on table  pratica_cds                                is 'Configurazione per cds';
comment on column pratica_cds.id                             is 'PK UUID';
comment on column pratica_cds.id_pratica                     is 'FK su Pratica';
comment on column pratica_cds.tipo                           is 'Tipo';
comment on column pratica_cds.attivita                       is 'Tipo attivita';
comment on column pratica_cds.azione                         is 'Tipo azione';
comment on column pratica_cds.termine_richiesta_integrazione is 'Data per termine richiesta integrazione';
comment on column pratica_cds.termine_pareri                 is 'Data per termine pareri';
comment on column pratica_cds.prima_sessione                 is 'Data prima sessione';
comment on column pratica_cds.data_termine                   is 'Data termine';
comment on column pratica_cds.modalita_incontro              is 'Modalita incontro (ON_LINE/FISICA)';
comment on column pratica_cds.link                           is 'Link riunione caso online';
comment on column pratica_cds.comune                         is 'Codice istat comune caso fisica';
comment on column pratica_cds.provincia                      is 'Sigla Provincia caso fisica';
comment on column pratica_cds.cap                            is 'Cap caso fisico';
comment on column pratica_cds.indirizzo                      is 'Indirizzo caso fisico';
comment on column pratica_cds.orario                         is 'Orario in formato HH:mm';
comment on column pratica_cds.date_create                    is 'Data creazione record';
comment on column pratica_cds.user_create                    is 'Utente creazione record';
comment on column pratica_cds.date_update                    is 'Data ultimo aggiornamento record';
comment on column pratica_cds.user_update                    is 'Utente ultimo aggiornamento record';
comment on column pratica_cds.comune_pertinenza              is 'Codice istat comune pertinenza dell''istanza';
comment on column pratica_cds.provincia_pertinenza           is 'Sigla provincia pertinenza dell''istanza';
comment on column pratica_cds.indirizzo_pertinenza           is 'Indirizzo pertinenza dell''istanza';
comment on column pratica_cds.completed                      is 'Se true indica che è stata avviato allineamento cds';
comment on column pratica_cds.id_cds 						 is 'Id conferenza su meetpad';
comment on column pratica_cds.comitato 						 is 'Indica se conferenza è comitato';
comment on column pratica_cds.username_creatore 			 is 'Indica la username dell''utente designato come creatore della conferenza. Utilizzato per comitato';
comment on column pratica_cds.nome_creatore 				 is 'Indica il nome dell''utente designato come creatore della conferenza. Utilizzato per comitato';
comment on column pratica_cds.cognome_creatore 				 is 'Indica il cognome dell''utente designato come creatore della conferenza. Utilizzato per comitato';
comment on column pratica_cds.mail_creatore 				 is 'Indica la mail dell''utente designato come creatore della conferenza. Utilizzato per comitato';
comment on column pratica_cds.telefono_creatore 			 is 'Indica il telefono dell''utente designato come creatore della conferenza. Utilizzato per comitato';
comment on column pratica_cds.codice_fiscale_creatore 		 is 'Indica il codice fiscale dell''utente designato come creatore della conferenza. Utilizzato per comitato';
COMMENT ON COLUMN pratica_cds.username_responsabile          is 'Username responsabile conferenza per conferenze non comitato';
COMMENT ON COLUMN pratica_cds.nome_responsabile              is 'Nome responsabile conferenza per conferenze non comitato';
COMMENT ON COLUMN pratica_cds.cognome_responsabile           is 'Cognome responsabile conferenza per conferenze non comitato';
COMMENT ON COLUMN pratica_cds.codice_fiscale_responsabile    is 'Codice Fiscale responsabile conferenza per conferenze non comitato';
COMMENT ON COLUMN pratica_cds.mail_responsabile              is 'Mail responsabile conferenza per conferenze non comitato';
COMMENT ON COLUMN pratica_cds.telefono_responsabile          is 'Telefono responsabile conferenza per conferenze non comitato';