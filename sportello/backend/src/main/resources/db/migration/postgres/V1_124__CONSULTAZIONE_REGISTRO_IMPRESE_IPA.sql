CREATE TABLE presentazione_istanza.tipo_azienda 
(id varchar(36) NOT NULL
,nome varchar(100) NOT NULL
,privato bool NOT NULL DEFAULT false
,CONSTRAINT tipo_azienda_pkey PRIMARY KEY (id)
);


insert into presentazione_istanza.tipo_azienda values (public.uuid_generate_v4(), 'Organizzazione Pubblica', false);
insert into presentazione_istanza.tipo_azienda values (public.uuid_generate_v4(), 'Organizzazione Privata', true);

COMMENT ON TABLE  presentazione_istanza.tipo_azienda         is 'Tipologia azienda';
COMMENT ON COLUMN presentazione_istanza.tipo_azienda.id      is 'Id azienda';
COMMENT ON COLUMN presentazione_istanza.tipo_azienda.nome    is 'Nome azienda';
COMMENT ON COLUMN presentazione_istanza.tipo_azienda.privato is 'Indica se privato';


alter table presentazione_istanza.referenti add column codice_ipa varchar(100);
alter table presentazione_istanza.referenti add column id_tipo_azienda varchar(36);
alter table presentazione_istanza.referenti add column tipo_azienda varchar(100);


ALTER TABLE presentazione_istanza.referenti ADD CONSTRAINT referenti_fk_azienda FOREIGN KEY (id_tipo_azienda) REFERENCES presentazione_istanza.tipo_azienda(id);