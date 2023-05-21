insert into "presentazione_istanza"."tipo_contenuto"("id", "descrizione", "descrizione_estesa", "sezione", "multiple")
values (700, 'istanza', 'Documento autogenerato ''Istanza''', 'GENERA_STAMPA', false),
	   (701, 'Dichiarazione tecnica', 'Documento autogenerato ''Dichiarazione tecnica''', 'GENERA_STAMPA', false);
	   
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id")
values (1, 700), (2, 700), (3, 700), (4, 700),
	   (1, 701), (2, 701), (3, 701), (4, 701);