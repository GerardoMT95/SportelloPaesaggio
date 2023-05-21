drop table presentazione_istanza.allegato_delegato;

create table presentazione_istanza.allegato_delegato(
	id_allegato uuid not null references allegati,
	id_pratica uuid not null,
	indice_delegato int not null,
	primary key(id_allegato, id_pratica, indice_delegato),
	foreign key (id_pratica, indice_delegato) references presentazione_istanza.pratica_delegato (id, indice)
);