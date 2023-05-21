ALTER TABLE presentazione_istanza.pratica_cds_partecipanti ADD COLUMN denominazione varchar NOT NULL;

comment on column pratica_cds_partecipanti.denominazione is 'denominazione partecipante';