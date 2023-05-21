-- rimetto a tutti i tipi procedimento di AUTPAE il valore temporaneo rimovibile per poi farlo sacede al 2017
INSERT INTO procedimento_qualificazioni(codice_tipo_procedimento,id_tipi_qualificazioni) values('AUT_PAES_ORD',227);
INSERT INTO procedimento_qualificazioni(codice_tipo_procedimento,id_tipi_qualificazioni) values('AUT_PAES_SEMPL_DPR_139_2010',227);
INSERT INTO procedimento_qualificazioni(codice_tipo_procedimento,id_tipi_qualificazioni) values('AUT_PAES_SEMPL_DPR_31_2017',227);
INSERT INTO procedimento_qualificazioni(codice_tipo_procedimento,id_tipi_qualificazioni) values('ACCERT_COMPAT_PAES_DLGS_42_2004',227);
INSERT INTO procedimento_qualificazioni(codice_tipo_procedimento,id_tipi_qualificazioni) values('ACCERT_COMPAT_PAES_DPR_31_2017',227);
INSERT INTO procedimento_qualificazioni(codice_tipo_procedimento,id_tipi_qualificazioni) values('ACCERT_COMPAT_PAES_DPR_139_2010',227);

ALTER TABLE procedimento_qualificazioni
    ADD COLUMN date_start timestamp without time zone NOT NULL default now();

ALTER TABLE procedimento_qualificazioni
    ADD COLUMN date_end timestamp without time zone;

ALTER TABLE procedimento_qualificazioni
    ADD COLUMN escluso_eti boolean NOT NULL DEFAULT false;

COMMENT ON COLUMN procedimento_qualificazioni.escluso_eti
    IS 'se a true questo valore viene escluso per gli enti NON DELEGATI nella compilazione del pannello intervento (caso PUTT per gli ETI di tipo Comune)';
ALTER TABLE procedimento_qualificazioni DROP CONSTRAINT procedimento_qualificazioni_pk;

ALTER TABLE procedimento_qualificazioni
    ADD CONSTRAINT procedimento_qualificazioni_pk PRIMARY KEY (codice_tipo_procedimento, date_start,id_tipi_qualificazioni);
--migrazione dei dati
UPDATE procedimento_qualificazioni set date_start='1970-01-01 00:00:00.000', date_end='2017-12-31 23:59:59.999';
INSERT into procedimento_qualificazioni(codice_tipo_procedimento, id_tipi_qualificazioni,date_start,date_end,escluso_eti) 
SELECT codice_tipo_procedimento, id_tipi_qualificazioni,date_end+interval '1 milliseconds',null,false from procedimento_qualificazioni;
DELETE FROM procedimento_qualificazioni where date_end=null and id_tipi_qualificazioni=227; 
      