ALTER TABLE presentazione_istanza.tipo_procedimento ADD accertamento boolean NULL;
COMMENT ON COLUMN presentazione_istanza.tipo_procedimento.accertamento IS 'indica se il procedimento è un accertamento di coompatibilità';
UPDATE presentazione_istanza.tipo_procedimento SET accertamento=true WHERE descrizione like 'ACCERTAMENTO DI COMPATIBILITA%'; 