insert into "presentazione_istanza"."tipo_contenuto"("id", "descrizione", "descrizione_estesa", "sezione", "multiple")
values (1100, 'DOC_TRASM', 'Documento trasmissione', 'TRASMISSIONE', false),
	   (1101, 'DOC_TRASM_PROT', 'Documento trasmissione (protocollato)', 'TRASMISSIONE', false);
	   
insert into "presentazione_istanza"."procedimento_contenuto"("tipo_procedimento", "tipo_contenuto_id")
values (1, 1100), (2, 1100), (3, 1100), (4, 1100),
	   (1, 1101), (2, 1101), (3, 1101), (4, 1101);
	   
	   
alter table "presentazione_istanza"."configurazioni_ente"
	add column "tipo_pratica_seduta" varchar(50) default ',1,2,';

comment on column "presentazione_istanza"."configurazioni_ente"."tipo_pratica_seduta" is 'Permette di indentificare quale tipologia di pratica è possibile sottomettere alla commissione locale';