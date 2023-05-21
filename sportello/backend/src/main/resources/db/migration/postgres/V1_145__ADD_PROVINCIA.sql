alter table presentazione_istanza.pratica_delegato
add column id_provincia_nascita int;
alter table presentazione_istanza.pratica_delegato
add column provincia_nascita varchar(400);

alter table presentazione_istanza.pratica_delegato
add column id_provincia_residenza int;
alter table presentazione_istanza.pratica_delegato
add column provincia_residenza varchar(400);