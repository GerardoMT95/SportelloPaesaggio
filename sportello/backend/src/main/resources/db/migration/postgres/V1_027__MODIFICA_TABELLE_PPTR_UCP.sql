alter table "presentazione_istanza"."ulteriori_contesti_paesaggistici"
	add column "sezione" varchar(50) default null;
alter table "presentazione_istanza"."pptr_selezioni"
	add column "sezione" varchar(50) default null;

delete from "presentazione_istanza"."ulteriori_contesti_paesaggistici";

-- GRUPPO "STRUTTURA IDRO-GEO-MORFOLOGICA"
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo") values ('STRT_IDRO_GEO_MORF', 'group', '6.1 - STRUTTURA IDRO-GEO-MORFOLOGICA', null, null, null, null, false, null);
-- SOTTOGRUPPO "Componenti geomorfologiche"
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo") values ('COM_GEOMORF', 'type', '6.1.1 - Componenti geomorfologiche', null, 'art. 49', 'Indirizzi / Direttive', 'art. 51/art. 52', false, 'STRT_IDRO_GEO_MORF');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('VERSANTI', 'option', 'UCP - Versanti', 'art. 143, co. 1,  lett. e)', 'art. 50 - 1)', 'Misure di salvaguardia e utilizzazione', 'art. 53', false, 'COM_GEOMORF','art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('LAME_E_GRAVINE', 'option', 'UCP - Lame e gravine', 'art. 143, co. 1,  lett. e)', 'art. 50 - 2)', 'Misure di salvaguardia e utilizzazione', 'art. 54', false, 'COM_GEOMORF','art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('DOLINE', 'option', 'UCP - Doline', 'art. 143, co. 1,  lett. e)', 'art. 50 - 3)', null, null, false, 'COM_GEOMORF','art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('GROTTE', 'option', 'UCP - Grotte (100m)', 'art. 143, co. 1,  lett. e)', 'art. 50 - 4)', 'Misure di salvaguardia e utilizzazione', 'art. 55', false, 'COM_GEOMORF','art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('GEOSITI', 'option', 'UCP - Geositi (199m)', 'art. 143, co. 1,  lett. e)', 'art. 50 - 5)', 'Misure di salvaguardia e utilizzazione', 'art. 56', false, 'COM_GEOMORF','art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('INGHIOTTITOI', 'option', 'UCP - Inghiottitoi (50m)', 'art. 143, co. 1,  lett. e)', 'art. 50 - 6)', 'Misure di salvaguardia e utilizzazione', 'art. 56', false, 'COM_GEOMORF','art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('CORDONI_DUNARI', 'option', 'UCP - Cordoni dunari', 'art. 143, co. 1,  lett. e)', 'art. 50 - 7)', 'Misure di salvaguardia e utilizzazione', 'art. 56', false, 'COM_GEOMORF','art_143');
-- SOTTOGRUPPO "Componenti geomorfologiche"
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo") values ('COM_IDRO', 'type', '6.1.2 - Componenti idrologiche', null, 'art. 40', 'Indirizzi / Direttive', 'art. 43/art. 44', false, 'STRT_IDRO_GEO_MORF');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('TERRITORI_COSTIERI', 'option', 'BP -Territoti costieri (300m)', 'art. 142, co. 1,  lett. a)', 'art. 41 - 1)', 'Prescrizioni', 'art. 45', false, 'COM_IDRO', 'art_142');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('TERRITORI_LAGHI', 'option', 'BP - Territori contermini ai laghi (300m)', 'art. 142, co. 1,  lett. b)', 'art. 41 - 2)', 'Prescrizioni', 'art. 45', false, 'COM_IDRO', 'art_142');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('ACQUE_PUBBLICHE', 'option', 'BP - Fiumi, torrenti, corsi d’acqua iscritti negli elenchi delle acque pubbliche (150m)', 'art. 142, co. 1,  lett. c)', 'art. 41 - 3)', 'Prescrizioni', 'art. 46', false, 'COM_IDRO', 'art_142');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('RETICOLO_IDROGRAFICO', 'option', 'UCP - Reticolo idrografico di connessione dellaR.E.R. (100m)', 'art. 143, co. 1,  lett. e)', 'art. 42 - 1)', 'Misure di salvaguardia e utilizzazione', 'art. 47', false, 'COM_IDRO', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('SORGENTI', 'option', 'UCP - Sorgenti (25m)', 'art. 143, co. 1,  lett. e)', 'art. 42 - 2)', 'Misure di salvaguardia e utilizzazione', 'art. 48', false, 'COM_IDRO', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('AREE_VINCOLO', 'option', 'UCP - Aree soggette a vincolo idrogeologico', 'art. 143, co. 1,  lett. e)', 'art. 42 - 3)', null, null, false, 'COM_IDRO', 'art_143');

-- GRUPPO "STRUTTURA ECOSISTEMICA - AMBIENTALE"
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo") values ('STRT_ECO_AMB', 'group', '6.2 - STRUTTURA ECOSISTEMICA - AMBIENTALE', null, null, null, null, false, null);
-- SOTTOGRUPPO "Componenti botanico-vegetazionali"
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo") values ('COMP_BOT_VEG', 'type', '6.2.1 - Componenti botanico-vegetazionali', null, 'art. 57', 'Indirizzi / Direttive', 'art. 60/art. 61', false, 'STRT_ECO_AMB');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('BOSCHI', 'option', 'BP - Boschi', 'art. 142, co. 1,  lett. g)', 'art. 58 - 1)', 'Prescrizioni', 'art. 62', false, 'COMP_BOT_VEG', 'art_142');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('ZONE_UMIDE_RAMSAR', 'option', 'BP - Zone umide Ramsar', 'art. 142, co. 1,  lett. i)', 'art. 58 - 2)', 'Prescrizioni', 'art. 64', true, 'COMP_BOT_VEG', 'art_142');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('AREE_UMIDE', 'option', 'UCP - Aree umide', 'art. 143, co. 1,  lett. e)', 'art. 59 - 1)', 'Misure di salvaguardia e utilizzazione', 'art. 65', true, 'COMP_BOT_VEG', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('PRATI_E_PASCOLI', 'option', 'UCP - Prati e pascoli naturali', 'art. 143, co. 1,  lett. e)', 'art. 59 - 2)', 'Misure di salvaguardia e utilizzazione', 'art. 66', false, 'COMP_BOT_VEG', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('FORMAZIONI_ARBUSTIVE', 'option', 'UCP - Formazioni arbustive in evoluzione naturale', 'art. 143, co. 1,  lett. e)', 'art. 59 - 3', 'Misure di salvaguardia e utilizzazione', 'art. 66', false, 'COMP_BOT_VEG', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('AREE_DI_RISPETTO_DEI_BOSCHI', 'option', 'UCP - Aree di rispetto dei boschi (100m - 50m - 20m)', 'art. 143, co. 1,  lett. e)', 'art. 59 - 4', 'Misure di salvaguardia e utilizzazione', 'art. 63', false, 'COMP_BOT_VEG', 'art_143');
-- SOTTOGRUPPO "Componenti delle aree protette e dei siti naturalistici"
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo") values ('COMP_AREE_PROTETTE_NAT', 'type', '6.2.2 - Componenti delle aree protette e dei siti naturalistici', null, 'art. 67', 'Indirizzi / Direttive', 'art. 69/art. 70', false, 'STRT_ECO_AMB');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('PARCHI_E_RISERVE', 'option', 'BP - Parchi e riserve', 'art. 142, co. 1,  lett. f)', 'art. 68 - 1)', 'Misure di salvaguardia e utilizzazione', 'art. 71', false, 'COMP_AREE_PROTETTE_NAT', 'art_142');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('SITI_RIL_NAT', 'option', 'UCP - Siti di rilevanza naturalistica', 'art. 143, co. 1,  lett. e)', 'art. 68 - 2)', 'Misure di salvaguardia e utilizzazione', 'art. 73', false, 'COMP_AREE_PROTETTE_NAT', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('AREE_DI_RISPETTO:DEI_PARCHI', 'option', 'UCP - Siti di rilevanza naturalistica', 'art. 143, co. 1,  lett. e)', 'art. 68 - 3)', 'Misure di salvaguardia e utilizzazione', 'art. 72', false, 'COMP_AREE_PROTETTE_NAT', 'art_143');


-- GRUPPO "STRUTTURA ANTROPICA E STORICO-CULTURALE "
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo") values ('STRUT_ANTR_STOR_CULT', 'group', '6.3 - STRUTTURA ANTROPICA E STORICO-CULTURALE', null, null, null, null, false, null);
-- SOTTOGRUPPO "Componenti culturali e insediative"
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo") values ('COMP_CULT_INS', 'type', '6.3.1 - Componenti culturali e insediative', null, 'art. 74', 'Indirizzi / Direttive', 'art. 77/art. 78', false, 'STRUT_ANTR_STOR_CULT');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('AREE_NOTEVOLE_INSTERESSE_PUBB', 'option', 'BP - Immobili e aree di notevole interesse pubblico', 'art. 136', 'art. 75 - 1)', 'Prescrizioni', 'art. 79', false, 'COMP_CULT_INS', 'art_136');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('ZONE_USI_CIVICI', 'option', 'BP - Zone gravate da usi civici', 'art. 142, co. 1,  lett. h)', 'art. 75 - 2)', null, null, false, 'COMP_CULT_INS', 'art_142');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('ZONE_INT_ARCH', 'option', 'BP - Immobili e aree di notevole interesse pubblico', 'art. 142, co. 1,  lett. m)', 'art. 75 - 3)', 'Prescrizioni', 'art. 80', false, 'COMP_CULT_INS', 'art_142');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('CITTA_CONSOLIDATA', 'option', 'UCP - Città Consolidata', 'art. 143, co. 1,  lett. e)', 'art. 76 - 1)', null, null, false, 'COMP_CULT_INS', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo") values ('TEST_STRAT_INS', 'optiongroup', 'UCP - Testimonianze della Stratificazione Insediativa', null, null, null, null, false, 'COMP_CULT_INS');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('TEST_STRAT_INS_1', 'option', 'segnalazioni architettoniche e segnalazioni archeologiche', 'art. 143, co. 1,  lett. e)', 'art. 76 - 2)a', 'Misure di salvaguardia e utilizzazione', 'art. 81 co. 2 e 3', false, 'TEST_STRAT_INS', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('TEST_STRAT_INS_2', 'option', 'aree appartenenti alla rete dei tratturi', 'art. 143, co. 1,  lett. e)', 'art. 76 - 2)b', 'Misure di salvaguardia e utilizzazione', 'art. 81 co. 2 e 3', false, 'TEST_STRAT_INS', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('TEST_STRAT_INS_3', 'option', 'aree a rischio archeologico', 'art. 143, co. 1,  lett. e)', 'art. 76 - 2)c', 'Misure di salvaguardia e utilizzazione', 'art. 81 co. 2 e 3', false, 'TEST_STRAT_INS', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('AREE_RISP_COMP_CULT', 'option', 'UCP - Area di rispetto delle componenti culturali e insediative (100m - 30m)', 'art. 143, co. 1,  lett. e)', 'art. 76 - 3)', 'Misure di salvaguardia e utilizzazione', 'art. 82', false, 'COMP_CULT_INS', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('PAES_RUR', 'option', 'UCP - Paesaggi rurali', 'art. 143, co. 1,  lett. e)', 'art. 76 - 4)', 'Misure di salvaguardia e utilizzazione', 'art. 83', false, 'COMP_CULT_INS', 'art_143');
-- SOTTOGRUPPO "Componenti dei valoripercettivi"
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo") values ('COMP_VALPER', 'type', '6.3.2 - Componenti dei valoripercettivi', null, 'art. 84', 'Indirizzi / Direttive', 'art. 86/art. 87', false, 'STRUT_ANTR_STOR_CULT');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('STRADE_VAL_PAES', 'option', 'UCP - Strade a valenza paesaggistica', 'art. 143, co. 1, lett. e)', 'art. 85 - 1)', 'Misure di salvaguardia e utilizzazione', 'art. 88', false, 'COMP_VALPER', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('STRADE_PANORAMICHE', 'option', 'UCP - Strade panoramiche', 'art. 143, co. 1, lett. e)', 'art. 85 - 2)', 'Misure di salvaguardia e utilizzazione', 'art. 88', false, 'COMP_VALPER', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('LUOG_PAN', 'option', 'UCP - Luoghi panoramici', 'art. 143, co. 1, lett. e)', 'art. 85 - 3)', 'Misure di salvaguardia e utilizzazione', 'art. 88', false, 'COMP_VALPER', 'art_143');
insert into "presentazione_istanza"."ulteriori_contesti_paesaggistici"("codice", "type", "label", "art1", "definizione", "disposizioni", "art2", "hasText", "gruppo", "sezione") values ('CONI_VISUALI', 'option', 'UCP - Coni visuali', 'art. 143, co. 1, lett. e)', 'art. 85 - 4)', 'Misure di salvaguardia e utilizzazione', 'art. 88', false, 'COMP_VALPER', 'art_143');