ALTER TABLE responsabile
    ALTER COLUMN riconoscimento_tipo TYPE character varying (255) COLLATE pg_catalog."default";
COMMENT ON COLUMN responsabile.riconoscimento_tipo
    IS 'enum java 
CARTA_DI_IDENTITA("Carta di identità"),
PATENTE("Patente"),
PASSAPORTO("Passaporto");';    	

UPDATE responsabile	SET  riconoscimento_tipo='CARTA_DI_IDENTITA' WHERE riconoscimento_tipo='1';
UPDATE responsabile	SET  riconoscimento_tipo='PATENTE' WHERE riconoscimento_tipo='2';
UPDATE responsabile	SET  riconoscimento_tipo='PASSAPORTO' WHERE riconoscimento_tipo='3';