ALTER TABLE ONLY presentazione_istanza.pareri_e_atti_assenso
    DROP CONSTRAINT pareri_e_atti_assenso_pkey,
     ADD CONSTRAINT pareri_e_atti_assenso_pkey PRIMARY KEY (pratica_id,id);

DROP SEQUENCE IF EXISTS pareri_e_atti_assenso_id_seq CASCADE;
