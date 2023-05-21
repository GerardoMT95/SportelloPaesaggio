ALTER TABLE presentazione_istanza.allegati
    ADD COLUMN checksum character varying;

ALTER TABLE presentazione_istanza.allegati
    ADD COLUMN size bigint;
    
CREATE INDEX checksum_idx
    ON presentazione_istanza.allegati USING btree
    (checksum bpchar_pattern_ops ASC NULLS LAST)
    TABLESPACE pg_default;    