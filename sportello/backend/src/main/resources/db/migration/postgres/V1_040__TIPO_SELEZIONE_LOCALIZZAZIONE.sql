ALTER TABLE "presentazione_istanza"."pratica" 
	ADD COLUMN tipo_selezione_localizzazione character varying(20);
ALTER TABLE "presentazione_istanza"."pratica" 
	ADD COLUMN has_shape boolean;	
COMMENT ON COLUMN "presentazione_istanza"."pratica"."tipo_selezione_localizzazione" 
  IS 'valori ammessi: PTC=selezione particelle SHPD=Shape editing SHPF shape file';