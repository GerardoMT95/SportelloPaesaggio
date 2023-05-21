ALTER TABLE template_email
    ADD COLUMN sotto_sezione varchar(50) NULL;
COMMENT ON COLUMN template_email.sotto_sezione IS 'popolata nei casi in cui abbiamo bisogno di distinguere il tipo di template es. TRASM_REL_ENTE metteremo CON_AVVIO e SENZA_AVVIO';
update template_email set sotto_sezione='CON_AVVIO' where sezione='TRASM_REL_ENTE';
update template_email set sotto_sezione='SENZA_AVVIO' where sezione='TRASM_REL_ENTE' and codice in ('RICH_PAR_SOP_32','RICH_PAR_SOP_22','RICH_PAR_SOP_12');    
