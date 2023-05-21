ALTER TABLE ONLY presentazione_istanza.inquadramento
    DROP CONSTRAINT inq_contesto_paesag_fkey,
    DROP CONSTRAINT inq_dest_uso_interv_fkey,
    DROP CONSTRAINT inq_dest_uso_suolo_fkey,
    DROP CONSTRAINT inq_morfologia_paesag_fkey;
