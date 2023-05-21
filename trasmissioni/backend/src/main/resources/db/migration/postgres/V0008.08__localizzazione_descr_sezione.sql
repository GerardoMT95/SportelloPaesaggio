ALTER TABLE particelle_catastali
    ADD COLUMN descr_sezione character varying(80);
    
UPDATE  particelle_catastali set descr_sezione='Bari' WHERE cod_cat='A662' AND sezione='A' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='Carbonara' WHERE cod_cat='A662' AND sezione='B' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='Ceglie del Campo' WHERE cod_cat='A662' AND sezione='C' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='Loseto' WHERE cod_cat='A662' AND sezione='D' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='Palese' WHERE cod_cat='A662' AND sezione='E' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='Santo Spirito' WHERE cod_cat='A662' AND sezione='F' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='Torre a Mare' WHERE cod_cat='A662' AND sezione='G' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='Taranto' WHERE cod_cat='L049' AND sezione='A' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='San Demetrio' WHERE cod_cat='L049' AND sezione='B' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='Morroni' WHERE cod_cat='L049' AND sezione='C' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='Presicce' WHERE cod_cat='M428' AND sezione='A' AND livello='PARTICELLE';
UPDATE  particelle_catastali set descr_sezione='Acquarica del capo' WHERE cod_cat='M428' AND sezione='B' AND livello='PARTICELLE';	 	   