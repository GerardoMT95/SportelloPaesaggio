INSERT INTO tipo_procedimento (codice, descrizione, invia_email, sanatoria) VALUES ('PUTT_X'		   , 'Pratica paesaggistica presentata nell’ambito del art. 5.01 NTA del PUTT'							  , false, false);
INSERT INTO tipo_procedimento (codice, descrizione, invia_email, sanatoria) VALUES ('PUTT_DLGS_42_2004', 'Pratica paesaggistica presentata nell’ambito del art. 5.01 NTA del PUTT e dell’art. 146 D.Lgs 42/04', false, false);





INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (201, 'radiobutton', 'a', 1    , 'Interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)', 0);

-- occhio che i prossimi dovrebbero essere dei 'radiobutton', almeno dalla figura in analisi, ma penso sia un errore grafico
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (221, 'checkbox'	 , 'b', 2    , 'Nuovi insediamenti civili e rurali', 1);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (222, 'checkbox'	 , 'b', 2    , 'Ristrutturazione e ampliamenti di insediamenti civili e rurali', 2);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (223, 'checkbox'	 , 'b', 2    , 'Ristrutturazione e ampliamenti di insediamenti industriali e commerciali', 8);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (224, 'checkbox'	 , 'b', 2    , 'Produzione e/o trasporto di energia', 10);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (225, 'checkbox'	 , 'b', 2    , 'Infrastrutture per telefonia', 11);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (226, 'checkbox'	 , 'b', 2    , 'Infrastrutture primarie (viarie, acqua, gas, ecc.) per telefonia', 12);



INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (211, 'radiobutton', 'e', 4    , 'Parere ai sensi dell’art 32 della L. 47/85 per beni tutelati ai sensi delle NTA del PUTT/P', 1);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (212, 'radiobutton', 'e', 4    , 'Autorizzazione Paesaggistica ordinaria ex art. 5.01 NTA del PUTT/P', 2);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (213, 'radiobutton', 'e', 4    , 'Autorizzazione Paesaggistica in sanatoria ex art. 5.01 NTA del PUTT/P', 3);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (214, 'radiobutton', 'e', 400  , 'Autorizzazione Paesaggistica semplificata ex art. 5.01 NTA del PUTT/P', 4);

INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (215, 'radiobutton', 'e', 4    , 'Parere ai sensi dell’art. 32 della L. 47/85 per beni tutelati ai sensi degli art. 136 e 142 D.Lgs. 42/04 (condono edilizio L. 47/85, L. 724/94, L. 326/03)', 1);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (216, 'radiobutton', 'e', 4    , 'Accertamento di compatibilità paesaggistica ex art. 167 D.Lgs. 42/04 (sanatoria ordinaria)', 2);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (217, 'radiobutton', 'e', 4    , 'Autorizzazione Paesaggistica ordinaria ex art. 146 D.Lgs. 42/04', 3);
INSERT INTO tipi_e_qualificazioni (id, stile, raggruppamento, zona, label, ordine) VALUES (218, 'radiobutton', 'e', 400  , 'Autorizzazione Paesaggistica semplificata ex art. 146 c.9 D.Lgs. 42/04 e D.P.R. 31/17', 4);



INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',201);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',  1);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',  2);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',  3);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',  4);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',221);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',222);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 12);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',223);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 14);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',224);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',225);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',226);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 18);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 19);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',211);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',212);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',213);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',214);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 68);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 69);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 70);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 71);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 72);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 73);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 74);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 75);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 76);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 77);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 78);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 79);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 80);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 81);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 82);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 83);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 84);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 85);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 86);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 87);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 88);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 89);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 90);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 91);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 92);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 93);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 94);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 95);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 96);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 97);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 98);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X', 99);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',100);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',101);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',102);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',103);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',104);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',105);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',106);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',107);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',108);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_X',109);



INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',201);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',  1);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',  2);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',  3);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',  4);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',221);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',222);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 12);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',223);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 14);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',224);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',225);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',226);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 18);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 19);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',215);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',216);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',217);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',218);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 68);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 69);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 70);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 71);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 72);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 73);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 74);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 75);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 76);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 77);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 78);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 79);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 80);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 81);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 82);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 83);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 84);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 85);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 86);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 87);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 88);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 89);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 90);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 91);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 92);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 93);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 94);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 95);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 96);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 97);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 98);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004', 99);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',100);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',101);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',102);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',103);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',104);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',105);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',106);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',107);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',108);
INSERT INTO procedimento_qualificazioni (codice_tipo_procedimento, id_tipi_qualificazioni) VALUES ('PUTT_DLGS_42_2004',109);



INSERT INTO tipo_allegato ("type", "descrizione") VALUES ('ATTESTAZIONE_CONFORMITA'   , 'Attestazione di conformità urbanistica o relativa asseverazione');
-- PREAVVISO
INSERT INTO tipo_allegato ("type", "descrizione") VALUES ('RICHIESTA_SOPRINTENDENZA'  , 'Richiesta parere alla Soprintendenza');
INSERT INTO tipo_allegato ("type", "descrizione") VALUES ('PROPOSTA_PROVVEDIMENTO'	  , 'Relazione tecnica illustrativa e proposta di provvedimento (DLGS 42/04 ART.146 C7 e DPR 139/10 ART.4 C6)');
INSERT INTO tipo_allegato ("type", "descrizione") VALUES ('DOCUMENTAZIONE_FOTOGRAFICA', 'Documentazione fotografica (se prodotta a parte da ''Elaborati di progetto'')');
-- PARERE
INSERT INTO tipo_allegato ("type", "descrizione") VALUES ('RETTIFICA_AUTORIZZAZIONE'  , 'Rettifica autorizzazione paesaggistica');
-- VERBALE
-- ISTANZA
-- ALTRI_PARERI
-- ELABORATI
INSERT INTO tipo_allegato ("type", "descrizione") VALUES ('ALTRO'	   			 	  , 'Altro');


UPDATE tipo_allegato SET "descrizione" = 'Preavviso di diniego autorizzazione' WHERE "type" = 'PREAVVISO';
UPDATE tipo_allegato SET "descrizione" = 'Istanza di autorizzazione' 		   WHERE "type" = 'ISTANZA';


INSERT INTO allegato_obbligatorio ("type", "codice_tipo_procedimento") VALUES ('PARERE_MIBAC', 'PUTT_DLGS_42_2004');
INSERT INTO allegato_obbligatorio ("type", "codice_tipo_procedimento") VALUES ('ISTANZA'	 , 'PUTT_DLGS_42_2004');
INSERT INTO allegato_obbligatorio ("type", "codice_tipo_procedimento") VALUES ('ISTANZA'	 , 'PUTT_X');


INSERT INTO placeholder_doc(codice, info) VALUES ('DATA_PRESENTAZIONE', 'data di presentazione/attivazione della pratica');

INSERT INTO placeholder_doc_sezione (placeholder_codice, template_doc_sezione_nome, template_doc_nome) VALUES ('DATA_PRESENTAZIONE', 'Oggetto', 'DocumentoDiTrasmissione');	

UPDATE template_doc_sezioni SET "valore" = 'Ricezione dei dati riguardanti il Provvedimento Paesaggistico avente ad oggetto:<br>- {OGGETTO} (cod. pratica {CODICE_FASCICOLO} )<br>- Tipo Procedimento: {TIPO_PROCEDIMENTO}<br>- Data Presentazione: {DATA_PRESENTAZIONE}' 
WHERE "nome" = 'Oggetto' AND "template_doc_nome" = 'DocumentoDiTrasmissione';

	
