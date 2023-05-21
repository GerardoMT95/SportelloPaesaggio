INSERT INTO presentazione_istanza.tipo_contenuto
(id, descrizione, descrizione_estesa, sezione, multiple)
VALUES(750, 'istanza originale', 'Documento autogenerato ''Istanza'' originale', 'GENERA_STAMPA_PREVIEW', false);
INSERT INTO presentazione_istanza.tipo_contenuto
(id, descrizione, descrizione_estesa, sezione, multiple)
VALUES(751, 'Dichiarazione tecnica originale', 'Documento autogenerato ''Dichiarazione tecnica'' originale', 'GENERA_STAMPA_PREVIEW', false);
INSERT INTO presentazione_istanza.tipo_contenuto
(id, descrizione, descrizione_estesa, sezione, multiple)
VALUES(752, 'Modulo integrazione documentale autogenerato', 'Modulo integrazione documentale autogenerato', 'INT_DOC_PREVIEW', true);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(1, 750);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(2, 750);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(3, 750);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(4, 750);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(1, 751);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(2, 751);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(3, 751);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(4, 751);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(1, 752);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(2, 752);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(3, 752);
INSERT INTO presentazione_istanza.procedimento_contenuto
(tipo_procedimento, tipo_contenuto_id)
VALUES(4, 752);
