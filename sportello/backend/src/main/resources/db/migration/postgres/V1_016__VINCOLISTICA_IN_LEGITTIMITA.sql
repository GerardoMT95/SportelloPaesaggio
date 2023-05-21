ALTER TABLE ONLY presentazione_istanza.leggittimita_titoli
    DROP CONSTRAINT leggittimita_titoli_pkey,
     ADD CONSTRAINT leggittimita_titoli_pkey PRIMARY KEY (pratica_id,id);


ALTER TABLE ONLY presentazione_istanza.leggittimita_provvedimenti    
    DROP CONSTRAINT leggittimita_provv_pkey,
     ADD CONSTRAINT leggittimita_provv_pkey PRIMARY KEY (pratica_id,id);

