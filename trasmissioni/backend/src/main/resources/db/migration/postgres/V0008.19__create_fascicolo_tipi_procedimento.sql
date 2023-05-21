--###################### Creazione tabella fascicolo_tipi_procedimento ############################
CREATE TABLE "fascicolo_tipi_procedimento"
(
	"id" bigserial primary key,
	"id_fascicolo" bigint not null references "fascicolo"("id"),
	"codice_tipo_procedimento" character varying(50) not null references "tipo_procedimento"("codice"),
	"inizio_validita" date not null,
	"fine_validita" date not null
);
---------------- Commenti ---------------
COMMENT ON TABLE fascicolo_tipi_procedimento IS 'Tiene traccia dei tipi di procedimento presenti alla creazione di un fascicolo, per ritrovarli al momento di un eventuale edit del fascicolo';
COMMENT ON COLUMN fascicolo_tipi_procedimento.id IS 'Id del record';
COMMENT ON COLUMN fascicolo_tipi_procedimento.codice_tipo_procedimento IS 'Codice indetificatibo del tipo procedimento';
COMMENT ON COLUMN fascicolo_tipi_procedimento.id_fascicolo IS 'Id del fascicolo a cui è associato il tipo procedimento';
COMMENT ON COLUMN fascicolo_tipi_procedimento.inizio_validita IS 'Data inizio validità del tipo procedimento al momento della creazione del fascicolo';
COMMENT ON COLUMN fascicolo_tipi_procedimento.fine_validita IS 'Data fine validità del tipo procedimento al momento della creazione del fascicolo';
--############################################################################################


--############################################################################################### 
--    Allineamento fascicoli esistenti, inserendo, nella nuova tabella e per ogni fascicolo,	# 
--    tutti i tipi procedimento attualmente presenti (per ogni modulo: autpae,putt,parere.		#
--###############################################################################################
-- Insert con select da fascicolo e tipo procedimento con le condizioni in base al modulo 
INSERT INTO fascicolo_tipi_procedimento  ("id_fascicolo","codice_tipo_procedimento", "inizio_validita","fine_validita")
SELECT f.id, tp.codice ,tp.inizio_validita, tp.fine_Validita 
FROM fascicolo f, tipo_procedimento tp 
-- CONDIZIONI FASCICOLI AUTPAE
WHERE (substr(f.tipo_procedimento,1,1) like 'A%' and substr(f.tipo_procedimento,1,1)=substr(tp.codice,1,1))
-- CONDIZIONI FASCICOLI PUTT
OR (substr(f.tipo_procedimento,1,4) like 'PUTT%' and substr(f.tipo_procedimento,1,4)=substr(tp.codice,1,4))
-- CONDIZIONI FASCICOLI PARERE
OR (substr(f.tipo_procedimento,1,6) like 'PARERE%' and substr(f.tipo_procedimento,1,6)=substr(tp.codice,1,6))
ORDER by f.id,tp.codice;