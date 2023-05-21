insert into presentazione_istanza.tipo_contenuto (id, descrizione, descrizione_estesa, sezione, multiple) 
values(1600, 'Allegato delega', 'Allegato delega', 'ALLEGATO_DELEGA', true);

create table presentazione_istanza.allegato_delegato(
	id_allegato uuid not null,
	id_seduta uuid not null,
	id_pratica uuid not null,
	primary key(id_allegato, id_seduta)
);