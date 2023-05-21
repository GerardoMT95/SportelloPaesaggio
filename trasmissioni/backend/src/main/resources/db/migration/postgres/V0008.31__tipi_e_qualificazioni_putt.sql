insert into "tipi_e_qualificazioni" ("id", "stile", "raggruppamento", "zona", "label", "ordine") 
	values (228, 'radiobutton', 'e', 4, 'Autorizzazione Paesaggistica ai sensi dell''art. 5.01 delle Nta del P.U.T.T./p', 5),
		   (229, 'radiobutton', 'e', 4, 'Autorizzazione Paesaggistica ai sensi dell''art. 146', 5),
		   (230, 'radiobutton', 'e', 4, 'Autorizzazione Paesaggistica semplificata ai sensi dell''art. 146', 6);
		   
insert into "procedimento_qualificazioni" ("codice_tipo_procedimento", "id_tipi_qualificazioni", "date_start", "date_end", "escluso_eti")
values ('PUTT_X'			, 228, '1970-01-01 00:00:00', '2017-12-31 23:59:59', true),
	   ('PUTT_DLGS_42_2004'	, 229, '1970-01-01 00:00:00', '2017-12-31 23:59:59', true),
	   ('PUTT_DLGS_42_2004'	, 230, '1970-01-01 00:00:00', '2017-12-31 23:59:59', true);