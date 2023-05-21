CREATE TABLE presentazione_istanza.pratica_cds_partecipanti (
	id varchar(36) NOT NULL,
	username varchar NOT NULL,
	CONSTRAINT pratica_cds_partecipanti_pkey PRIMARY KEY (id),
	CONSTRAINT pratica_cds_partecipanti_fk FOREIGN KEY (id) REFERENCES presentazione_istanza.pratica_cds(id)
);

comment on table  pratica_cds_partecipanti                                is 'Configurazione per cds';
comment on column pratica_cds_partecipanti.id                             is 'PK e FK su pratica_cds';
comment on column pratica_cds_partecipanti.username                       is 'username partecipante';