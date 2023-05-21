create table tipo_cds_tipo
(id varchar(36) not null
,id_tipo_procedimento int not null
,id_organizzazione int not null
,tipo varchar(100) not null
,start_date timestamp not null default current_timestamp
,end_date timestamp
,user_create varchar(100) not null
,primary key(id)
);

ALTER TABLE tipo_cds_tipo  ADD CONSTRAINT tipo_cds_tipo_fk_1 FOREIGN KEY (id_tipo_procedimento) REFERENCES tipo_procedimento(id);

create unique index tipo_cds_tipo_uni_idx_1 on tipo_cds_tipo(id_tipo_procedimento,id_organizzazione,tipo,start_date); 
create index tipo_cds_tipo_idx_1 on tipo_cds_tipo(id_tipo_procedimento);
create index tipo_cds_tipo_idx_2 on tipo_cds_tipo(id_organizzazione); 
create index tipo_cds_tipo_idx_3 on tipo_cds_tipo(start_date); 
create index tipo_cds_tipo_idx_4 on tipo_cds_tipo(end_date); 

comment on table tipo_cds_tipo is 'Tipi conferenza ammessi per tipologia procedimento';
comment on column tipo_cds_tipo.id is 'PK uuid';
comment on column tipo_cds_tipo.id_tipo_procedimento is 'FK su tipo procedimento';
comment on column tipo_cds_tipo.id_organizzazione is 'Id ente';
comment on column tipo_cds_tipo.tipo is 'Tipo';
comment on column tipo_cds_tipo.start_date is 'data inizio validita''';
comment on column tipo_cds_tipo.end_date is 'fine inizio validita''';
comment on column tipo_cds_tipo.user_create is 'Utente creazione';

create table tipo_cds_attivita
(id varchar(36) not null
,id_tipo_procedimento int not null
,id_organizzazione int not null
,attivita varchar(1000) not null
,start_date timestamp not null default current_timestamp
,end_date timestamp
,user_create varchar(100) not null
,primary key(id)
);

ALTER TABLE tipo_cds_attivita  ADD CONSTRAINT tipo_cds_attivita_fk_1 FOREIGN KEY (id_tipo_procedimento) REFERENCES tipo_procedimento(id);

create unique index tipo_cds_attivita_uni_idx_1 on tipo_cds_attivita(id_tipo_procedimento,id_organizzazione,attivita,start_date); 
create index tipo_cds_attivita_idx_1 on tipo_cds_attivita(id_tipo_procedimento); 
create index tipo_cds_attivita_idx_2 on tipo_cds_attivita(id_organizzazione); 
create index tipo_cds_attivita_idx_3 on tipo_cds_attivita(start_date); 
create index tipo_cds_attivita_idx_4 on tipo_cds_attivita(end_date); 

comment on table tipo_cds_attivita is 'Tipi di attivita ammessi per tipologia procedimento';
comment on column tipo_cds_attivita.id is 'PK uuid';
comment on column tipo_cds_attivita.id_tipo_procedimento is 'FK su tipo procedimento';
comment on column tipo_cds_attivita.id_organizzazione is 'Id ente';
comment on column tipo_cds_attivita.attivita is 'Attivita''';
comment on column tipo_cds_attivita.start_date is 'data inizio validita''';
comment on column tipo_cds_attivita.end_date is 'fine inizio validita''';
comment on column tipo_cds_attivita.user_create is 'Utente creazione';

create table tipo_cds_azione
(id varchar(36) not null
,id_tipo_procedimento int not null
,id_organizzazione int not null
,azione varchar(1000) not null
,start_date timestamp not null default current_timestamp
,end_date timestamp
,user_create varchar(100) not null
,primary key(id)
);

ALTER TABLE tipo_cds_azione  ADD CONSTRAINT tipo_cds_azione_fk_1 FOREIGN KEY (id_tipo_procedimento) REFERENCES tipo_procedimento(id);

create unique index tipo_cds_azione_uni_idx_1 on tipo_cds_azione(id_tipo_procedimento,id_organizzazione,azione,start_date); 
create index tipo_cds_azione_idx_1 on tipo_cds_azione(id_tipo_procedimento); 
create index tipo_cds_azione_idx_2 on tipo_cds_azione(id_organizzazione); 
create index tipo_cds_azione_idx_3 on tipo_cds_azione(start_date); 
create index tipo_cds_azione_idx_4 on tipo_cds_azione(end_date); 

comment on table tipo_cds_azione is 'Tipi di azione ammessi per tipologia procedimento';
comment on column tipo_cds_azione.id is 'PK uuid';
comment on column tipo_cds_azione.id_tipo_procedimento is 'FK su tipo procedimento';
comment on column tipo_cds_azione.id_organizzazione is 'Id ente';
comment on column tipo_cds_azione.azione is 'azione';
comment on column tipo_cds_azione.start_date is 'data inizio validita''';
comment on column tipo_cds_azione.end_date is 'fine inizio validita''';
comment on column tipo_cds_azione.user_create is 'Utente creazione';