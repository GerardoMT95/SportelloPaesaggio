DROP VIEW IF EXISTS v_fascicolo_public;
CREATE  VIEW v_fascicolo_public as 
select 
fascicolo.id, 
fascicolo.ufficio, 
fascicolo.org_creazione, 
fascicolo.tipo_procedimento, 
fascicolo.oggetto_intervento,
fascicolo.codice,
fascicolo.codice_interno_ente ,
fascicolo.numero_interno_ente,
fascicolo.protocollo ,
fascicolo.data_protocollo, 
localizzazione_intervento.comune_id ,
fascicolo.rup ,
fascicolo.numero_provvedimento ,
fascicolo.data_rilascio_autorizzazione ,
fascicolo.esito_data_rilascio_autorizzazione ,
fascicolo.esito ,
fascicolo.esito_verifica ,
fascicolo.stato ,
-- per filtro:
--tipo intervento (zona=1 va fatto mapping con codice in presentazione_istanza (TIPO_INT_X)
fascicolo_intervento.id_tipi_qualificazioni as tipo_intervento,
tipi_e_qualificazioni.label as label_intervento,
'autpae' as applicazione
from 
fascicolo
left join localizzazione_intervento on fascicolo.id = localizzazione_intervento.pratica_id 
left join fascicolo_intervento on fascicolo.id = fascicolo_intervento.id_fascicolo 
left join tipi_e_qualificazioni on tipi_e_qualificazioni.id = fascicolo_intervento.id_tipi_qualificazioni 
where tipi_e_qualificazioni.zona = 1
AND ( fascicolo.stato  = 'TRANSMITTED' or fascicolo.stato  = 'SELECTED' or fascicolo.stato  = 'FINISHED' or fascicolo.stato  = 'ON_MODIFY' ) 
AND ( fascicolo.esito != 'NON_AUTORIZZATO'  );