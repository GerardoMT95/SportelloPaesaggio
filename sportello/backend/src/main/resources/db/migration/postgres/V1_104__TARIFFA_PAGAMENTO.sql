CREATE TABLE presentazione_istanza.tariffa (
	id bigserial NOT NULL, -- Id pk key di tipo uuid
	id_organizzazione int NOT NULL, -- FK organizzazioni  0 per tariffa default per tutte le organizzazioni
	id_tipo_procedimento int NOT NULL, -- FK su tipo procedimento
	soglia_minima float8 NOT NULL, -- Soglia minima rispetto all'importo del progetto
	soglia_massima float8 NULL, -- Soglia massima rispetto all'importo del progetto. Se null e' piu' infinito
	delete_eccedente bool NOT NULL, -- Se true dall'algoritmo del calcolo si elimina la soglia minima
	percentuale float8 NOT NULL, -- Percentuale da applicare
	cifra_da_aggiungere float8 NOT NULL, -- Cifra da aggiungere al calcolo per ottenere l'onere finale
	percentuale_finale float8 NOT NULL, -- Percentuale finale da applicare
	start_date timestamp NOT NULL, -- Inizio validita' della tariffa
	end_date timestamp NULL, -- Termine validita' della tariffa
	create_date timestamp NOT NULL DEFAULT now(), -- Data creazione record
	create_user varchar(100) NOT NULL, -- Username creazione record
	CONSTRAINT tariffa_pkey PRIMARY KEY (id),
	CONSTRAINT tariffa_fk FOREIGN KEY (id_tipo_procedimento) REFERENCES presentazione_istanza.tipo_procedimento(id)
);
COMMENT ON TABLE presentazione_istanza.tariffa IS 'Tabella delle tariffe per il calcolo dei pagamenti degli oneri istruttori';

-- Column comments

COMMENT ON COLUMN presentazione_istanza.tariffa.id IS 'Id pk key di tipo uuid';
COMMENT ON COLUMN presentazione_istanza.tariffa.id_tipo_procedimento IS 'FK su tipo procedimento';
COMMENT ON COLUMN presentazione_istanza.tariffa.id_organizzazione IS '0 per default su tutte le organizzazioni, altrimenti ci sarà id organizzazione';
COMMENT ON COLUMN presentazione_istanza.tariffa.soglia_minima IS 'Soglia minima rispetto all''importo del progetto';
COMMENT ON COLUMN presentazione_istanza.tariffa.soglia_massima IS 'Soglia massima rispetto all''importo del progetto. Se null e'' piu'' infinito';
COMMENT ON COLUMN presentazione_istanza.tariffa.delete_eccedente IS 'Se true dall''algoritmo del calcolo si elimina la soglia minima';
COMMENT ON COLUMN presentazione_istanza.tariffa.percentuale IS 'Percentuale da applicare';
COMMENT ON COLUMN presentazione_istanza.tariffa.cifra_da_aggiungere IS 'Cifra da aggiungere al calcolo per ottenere l''onere finale';
COMMENT ON COLUMN presentazione_istanza.tariffa.percentuale_finale IS 'Percentuale finale da applicare';
COMMENT ON COLUMN presentazione_istanza.tariffa.start_date IS 'Inizio validita'' della tariffa';
COMMENT ON COLUMN presentazione_istanza.tariffa.end_date IS 'Termine validita'' della tariffa';
COMMENT ON COLUMN presentazione_istanza.tariffa.create_date IS 'Data creazione record';
COMMENT ON COLUMN presentazione_istanza.tariffa.create_user IS 'Username creazione record';

-- Permissions

ALTER TABLE presentazione_istanza.tariffa OWNER TO presentazione_istanza;
GRANT ALL ON TABLE presentazione_istanza.tariffa TO presentazione_istanza;


INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 1, 0, 200000, false, 0, 100, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 1, 200000, 5000000, false, 0.03, 0, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 1, 5000000, 20000000, true, 0.005, 1500, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 1, 20000000, NULL, true, 0.001, 2250, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');

INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 2, 0, 200000, false, 0, 100, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 2, 200000, 5000000, false, 0.03, 0, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 2, 5000000, 20000000, true, 0.005, 1500, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 2, 20000000, NULL, true, 0.001, 2250, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');

INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 3, 0, 200000, false, 0, 100, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 3, 200000, 5000000, false, 0.03, 0, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 3, 5000000, 20000000, true, 0.005, 1500, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 3, 20000000, NULL, true, 0.001, 2250, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');

INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 4, 0, 200000, false, 0, 100, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 4, 200000, 5000000, false, 0.03, 0, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 4, 5000000, 20000000, true, 0.005, 1500, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');
INSERT INTO presentazione_istanza.tariffa
(id_organizzazione, id_tipo_procedimento, soglia_minima, soglia_massima, delete_eccedente, percentuale, cifra_da_aggiungere, percentuale_finale, start_date, end_date, create_date, create_user)
VALUES(0, 4, 20000000, NULL, true, 0.001, 2250, 30, '2000-01-01 00:00:00.000', NULL, '2021-07-08 09:53:54.075', '');



--pagamenti pratica

CREATE TABLE presentazione_istanza.pratica_pagamenti (
	id bigserial NOT NULL, -- Id pk key di 
	id_pratica uuid NOT NULL, -- Fk su pratica
	id_tariffa bigint NOT NULL, -- Fk su tariffa utilizzata per il calcolo
	importo_progetto float8 NOT NULL, -- Importo progetto scritto da utente
	data_scadenza date NOT NULL, -- Data scadenza scritto da utente
	importo_pagamento float8 NOT NULL, -- Importo calcolato da algoritmo ottenuto da fk su tariffa
	iud varchar(4000) NOT NULL, -- Identificativo unico dovuto generato per mypay
	iuv varchar(4000) NOT NULL, -- Identificativo unico versamento generato da mypay
	url_pdf varchar(4000) NOT NULL, -- Url per download del pdf
	url_mypay varchar(4000) NULL, -- Url per redirect su mypay
	deleted bool NOT NULL DEFAULT false, -- True se cancellato
	pagato bool NOT NULL DEFAULT false, -- True se e' stato pagato
	create_date timestamp NOT NULL DEFAULT now(), -- Data di creazione del record
	create_user varchar(100) NOT NULL, -- Utente in sessione che ha creato record
	update_date timestamp NULL, -- Data di aggiornamento del record
	update_user varchar(100) NULL, -- Utente in sessione che ha aggiornato record
	causale varchar(400) NOT NULL,
	ricevuta text NULL,
	CONSTRAINT pratica_pagamenti_pkey PRIMARY KEY (id),
	CONSTRAINT pratica_pagamenti_fk_1 FOREIGN KEY (id_pratica) REFERENCES presentazione_istanza.pratica(id),
	CONSTRAINT pratica_pagamenti_fk_2 FOREIGN KEY (id_tariffa) REFERENCES presentazione_istanza.tariffa(id)
);
COMMENT ON TABLE presentazione_istanza.pratica_pagamenti IS 'Tabella pei pagamenti degli oneri istruttori';

-- Column comments

COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.id IS 'Id pk key di tipo bigint';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.id_pratica IS 'Fk su pratica';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.id_tariffa IS 'Fk su tariffa utilizzata per il calcolo';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.importo_progetto IS 'Importo progetto scritto da utente';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.data_scadenza IS 'Data scadenza scritto da utente';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.importo_pagamento IS 'Importo calcolato da algoritmo ottenuto da fk su tariffa';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.iud IS 'Identificativo unico dovuto generato per mypay';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.iuv IS 'Identificativo unico versamento generato da mypay';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.url_pdf IS 'Url per download del pdf';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.url_mypay IS 'Url per redirect su mypay';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.deleted IS 'True se cancellato';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.pagato IS 'True se e'' stato pagato';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.create_date IS 'Data di creazione del record';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.create_user IS 'Utente in sessione che ha creato record';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.update_date IS 'Data di aggiornamento del record';
COMMENT ON COLUMN presentazione_istanza.pratica_pagamenti.update_user IS 'Utente in sessione che ha aggiornato record';

-- Permissions

ALTER TABLE presentazione_istanza.pratica_pagamenti OWNER TO presentazione_istanza;
GRANT ALL ON TABLE presentazione_istanza.pratica_pagamenti TO presentazione_istanza;




    