CREATE TABLE presentazione_istanza.dichiarazione
(
    id 			integer NOT NULL,
    testo 		text	NOT NULL,
    label_cb 	text	NOT NULL,
    data_inizio date 	NOT NULL,
    data_fine 	date	NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE presentazione_istanza.dichiarazione
    OWNER to presentazione_istanza;
COMMENT ON TABLE presentazione_istanza.dichiarazione
    IS 'dichiarazione di responsabilità per Scheda Tecnica';
COMMENT ON COLUMN presentazione_istanza.dichiarazione.testo
    IS 'html della dichiarazione';
COMMENT ON COLUMN presentazione_istanza.dichiarazione.label_cb
    IS 'testo della checkBox';
    
INSERT INTO presentazione_istanza.dichiarazione (id, testo, label_cb, data_inizio, data_fine) 
   	 VALUES (1,
    	    'Il richiedente e/o il tecnico di riferimento per l''istanza, consapevole/i delle penalità previste in caso di false attestazioni, dichiarazioni mendaci o che affermano fatti non conformi al vero, ai sensi dell’articolo 76 del d.P.R. 28 dicembre 2000, n. 445 e degli artt. 483,495 e 496 del Codice Penale, sotto la propria responsabilità,',
    	    'DICHIARA/DICHIARANO quanto specificato nella scheda tecnica',
			'2000-01-01',
			'2050-12-31');

ALTER TABLE ONLY presentazione_istanza.pratica ADD COLUMN dichiaraz_sch_tec_accettata boolean NOT NULL DEFAULT false;
