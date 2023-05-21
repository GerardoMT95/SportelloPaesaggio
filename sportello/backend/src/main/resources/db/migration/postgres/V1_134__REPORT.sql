CREATE TABLE presentazione_istanza.report
(id varchar(36) NOT NULL
,tipo varchar(100) NOT NULL
,parametri text NOT NULL
,username varchar(100) NOT NULL
,path_file varchar(400) NULL
,file_name varchar(100) NULL
,stato varchar(1) NOT NULL DEFAULT 'I'
,dimensione_file bigint NULL
,data_richiesta timestamp NOT NULL DEFAULT current_timestamp
,data_avvio timestamp NULL
,data_creazione timestamp NULL
,data_scadenza timestamp NULL
,hash varchar(400)
,stato_originale varchar(1)
,ente_delegato int
,descrizione_ente varchar(255)
,PRIMARY KEY (id)
);


comment on table presentazione_istanza.report is 'Tabella dei report dei fascicoli delle istanze';
comment on column presentazione_istanza.report.id              is 'Tabella dei report dei fascicoli delle istanze';
comment on column presentazione_istanza.report.tipo            is 'Tipo Report. Coincide con Nome Bean in spring';
comment on column presentazione_istanza.report.username        is 'Utente del report';
comment on column presentazione_istanza.report.path_file       is 'Path fisico del file';
comment on column presentazione_istanza.report.file_name       is 'Nome file';
comment on column presentazione_istanza.report.stato           is 'Stato. (I = inserito, A = avviato, C = concluso, E = errore, S = scaduto)';
comment on column presentazione_istanza.report.dimensione_file is 'Dimensione report in bytes';
comment on column presentazione_istanza.report.data_richiesta  is 'Data richiesta';
comment on column presentazione_istanza.report.data_avvio      is 'Data di avvio';
comment on column presentazione_istanza.report.data_creazione  is 'Data creazione zip';
comment on column presentazione_istanza.report.data_scadenza   is 'Data scadenza/eliminazione fisica del report';
comment on column presentazione_istanza.report.hash is 'Hash of file';
comment on column presentazione_istanza.report.ente_delegato is 'Codice ente delegato richiedente report';
comment on column presentazione_istanza.report.descrizione_ente is 'Descrizione ente delegato richiedente report';
comment on column presentazione_istanza.report.stato_originale is 'Stato roginale prima della scadenza';


CREATE VIEW presentazione_istanza.v_report_oneri_istruttori as
SELECT p.id AS id_pratica,
	p.ente_delegato,
	p.data_presentazione as data_presentazione,
	tp.id AS id_tipo_procedimento,
	tp.descrizione AS nome_tipo_procedimento,
	pp.importo_pagamento,
	pp.importo_progetto
FROM presentazione_istanza.pratica p
JOIN presentazione_istanza.tipo_procedimento tp 
	ON tp.id::text = p.tipo_procedimento::text
JOIN presentazione_istanza.pratica_pagamenti pp 
	ON pp.id_pratica::text = p.id::text 
	AND pp.pagato = true;

comment on view presentazione_istanza.v_report_oneri_istruttori is 'View per filtrare le pratiche per oneri istruttori';