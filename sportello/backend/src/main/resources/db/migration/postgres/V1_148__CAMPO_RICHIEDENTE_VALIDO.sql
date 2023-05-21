alter table presentazione_istanza.pratica add column validazione_richiedente boolean default false;
update presentazione_istanza.pratica set validazione_richiedente = validazione_istanza;