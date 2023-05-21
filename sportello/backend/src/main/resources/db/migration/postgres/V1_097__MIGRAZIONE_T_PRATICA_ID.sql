ALTER TABLE presentazione_istanza.pratica
    ADD COLUMN t_pratica_id bigint default null;

COMMENT ON COLUMN presentazione_istanza.pratica.t_pratica_id IS 'utilizzata per mappare la chiave primaria delle pratiche migrate';    
    
