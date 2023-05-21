ALTER TABLE presentazione_istanza.corrispondenza ADD t_mailinviate_id bigint NULL;
COMMENT ON COLUMN presentazione_istanza.corrispondenza.t_mailinviate_id IS 
'riferimento T_MAIL_INVIATE_ID su CIVILIA_CS.T_MAIL_INVIATE';

ALTER TABLE presentazione_istanza.corrispondenza ADD t_pptr_mailinviate_id bigint NULL;
COMMENT ON COLUMN presentazione_istanza.corrispondenza.t_pptr_mailinviate_id IS 
'riferimento TNO_PPTR_MAIL_INVIATE su CIVILIA_CS.TNO_PPTR_MAIL_INVIATE';
  