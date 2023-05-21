insert into template_doc(nome, descrizione)	
	values ('Campionamento', 'Documento di riepilogo esito campionamento');

insert into template_doc_sezioni_default(nome, template_doc_nome, valore, id_allegato, tipo_sezione)
	values ('Intestazione','Campionamento', 'Servizio Osservatorio e Pianificazione Paesaggistica', null, 'TEXT'),
		   ('Dipartimento','Campionamento', 'DIPARTIMENTO MOBILITÀ, QUALITÀ URBANA, OPERE PUBBLICHE, ECOLOGIA E PAESAGGIO', null, 'TEXT'),
		   ('Oggetto','Campionamento', 'Elenco Autorizzazioni Paesaggistiche trasmesse dal {DATA_REGISTRAZIONE_PRIMO_PIANO} al {DATA_FINE_CAMPIONAMENTO} che devono essere sottoposti alle verifiche opportune.', null, 'HTML'),
		   ('Introduzione','Campionamento', 'Questa Sezione ha provveduto ad effettuare una ricognizione delle autorizzazioni per le quali risulta avviata la registrazione  nel periodo dal {DATA_REGISTRAZIONE_PRIMO_PIANO} al {DATA_FINE_CAMPIONAMENTO}.  Le predette Autorizzazioni sono elencate nella tabella 1.', null, 'HTML'),
		   ('IntroTabella1','Campionamento', 'Tabella 1: autorizzazioni per le quali è stata avviata la procedura di registrazione, nel periodo dal {DATA_REGISTRAZIONE_PRIMO_PIANO} al {DATA_FINE_CAMPIONAMENTO}.', null, 'TEXT'),
		   ('IntroParametriSelezione','Campionamento', 'In seguito, sulla base della metodologia definita nelle opportune sedi, è stato selezionato il campione di autorizzazioni per le quali è prevista la verifica . Le autorizzazioni selezionate sono elencate nella tabella 2.', null, 'HTML'),
		   ('IntroTabella2','Campionamento', 'Tabella 2: autorizzazioni selezionate ai fini della verifica, nel periodo dal {DATA_REGISTRAZIONE_PRIMO_PIANO} al {DATA_FINE_CAMPIONAMENTO}.', null, 'TEXT'),
		   ('FraseAvvioVerifiche','Campionamento', 'Con la presente, si comunica pertanto l''avvio dei procedimenti di verifica, relativi ad ognuna delle autorizzazioni elencate nella tabella 2. Entro il termine previsto, questa Sezione concluderà tali verifiche.', null, 'HTML'),
		   ('IntroNonSelezionate','Campionamento', 'Le autorizzazioni che non sono state selezionate ai fini della verifica sono invece elencate nella tabella 3.', null, 'TEXT'),
		   ('IntroTabella3','Campionamento', 'Tabella 3: autorizzazioni non selezionate per la verifica, registrate nel periodo dal {DATA_REGISTRAZIONE_PRIMO_PIANO} al {DATA_FINE_CAMPIONAMENTO}.', null, 'TEXT'),
		   ('RestandoaDisposizione','Campionamento', 'Restando a disposizione per ogni ulteriore chiarimento, si inviano cordiali saluti.', null, 'TEXT'),
		   ('TitoloDirigente','Campionamento', 'LA DIRIGENTE AD INTERIM DEL SERVIZIO OSSERVATORIO E PIANIFICAZIONE PAESAGGISTICA', null, 'TEXT'),	   
		   ('Dirigente','Campionamento', '(Ing. Barbara LOCONSOLE)', null, 'TEXT'),
		   ('FraseFirmaAutografa','Campionamento', 'La firma autografa della dirigente è sostituita a mezzo stampa ai sensi dell''art. 3, comma 2 del d.lgs. 39/1993 e s.m.i.', null, 'TEXT'),
		   ('Logo','Campionamento', '', null, 'IMAGE');
--in corrispondenza di questa riga viene inserito un file nel classpath /jasper/image_template_default/LETTERA_TRASMISSIONE_Logo.png	
INSERT INTO template_doc_sezioni SELECT * FROM template_doc_sezioni_default where template_doc_nome = 'Campionamento'; 
	
INSERT INTO placeholder_doc(codice, info) 
	values ('DATA_REGISTRAZIONE_PRIMO_PIANO', 'Data di avvio del periodo di campionamento'),
		   ('DATA_FINE_CAMPIONAMENTO', 'Ultimo giorno utile del periodo di campionamento')
		   ;	

INSERT INTO placeholder_doc_sezione(placeholder_codice, template_doc_sezione_nome, template_doc_nome)
	VALUES ('DATA_REGISTRAZIONE_PRIMO_PIANO', 'Oggetto','Campionamento' ),
		   ('DATA_REGISTRAZIONE_PRIMO_PIANO', 'Introduzione','Campionamento' ),
		   ('DATA_REGISTRAZIONE_PRIMO_PIANO', 'IntroParametriSelezione','Campionamento' ),
		   ('DATA_REGISTRAZIONE_PRIMO_PIANO', 'IntroTabella1','Campionamento' ),
		   ('DATA_REGISTRAZIONE_PRIMO_PIANO', 'IntroTabella2','Campionamento' ),
		   ('DATA_REGISTRAZIONE_PRIMO_PIANO', 'FraseAvvioVerifiche','Campionamento' ),
		   ('DATA_REGISTRAZIONE_PRIMO_PIANO', 'IntroNonSelezionate','Campionamento' ),
		   ('DATA_REGISTRAZIONE_PRIMO_PIANO', 'IntroTabella3','Campionamento' ),
		   ('DATA_FINE_CAMPIONAMENTO', 'Oggetto','Campionamento' ),
		   ('DATA_FINE_CAMPIONAMENTO', 'Introduzione','Campionamento' ),
		   ('DATA_FINE_CAMPIONAMENTO', 'IntroParametriSelezione','Campionamento' ),
		   ('DATA_FINE_CAMPIONAMENTO', 'IntroTabella1','Campionamento' ),
		   ('DATA_FINE_CAMPIONAMENTO', 'IntroTabella2','Campionamento' ),
		   ('DATA_FINE_CAMPIONAMENTO', 'FraseAvvioVerifiche','Campionamento' ),
		   ('DATA_FINE_CAMPIONAMENTO', 'IntroNonSelezionate','Campionamento' ),
		   ('DATA_FINE_CAMPIONAMENTO', 'IntroTabella3','Campionamento' )
	 	   ;	
UPDATE template_email_default
	SET testo='<p>Si trasmette in allegato la nota prot. della Sezione Regionale Autorizzazioni Paesaggistiche {PROTOCOLLO_SET_CAMPIONAMENTO} del {DATA_PROTOCOLLO_CAMPIONAMENTO}, recante la definizione del campione di Autorizzazioni Paesaggistiche registrate nel periodo dal {DATA_INIZIO_CAMPIONAMENTO} al {DATA_FINE_CAMPIONAMENTO} sottoposte alle verifiche opportune.</p><p></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
	WHERE codice='ESITO_CAMPIONAMENTO';
UPDATE template_email
	SET testo='<p>Si trasmette in allegato la nota prot. della Sezione Regionale Autorizzazioni Paesaggistiche {PROTOCOLLO_SET_CAMPIONAMENTO} del {DATA_PROTOCOLLO_CAMPIONAMENTO}, recante la definizione del campione di Autorizzazioni Paesaggistiche registrate nel periodo dal {DATA_INIZIO_CAMPIONAMENTO} al {DATA_FINE_CAMPIONAMENTO} sottoposte alle verifiche opportune.</p><p></p><p class="ql-align-center">Si prega di non rispondere a questa e-mail in quanto la stessa è prodotta in automatico dal sistema SIT Puglia (www.sit.puglia.it).<br><br></p>'
	WHERE codice='ESITO_CAMPIONAMENTO';	
	 	   