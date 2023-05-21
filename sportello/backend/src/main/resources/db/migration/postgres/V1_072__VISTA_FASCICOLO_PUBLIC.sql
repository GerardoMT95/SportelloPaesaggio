DROP VIEW IF EXISTS v_fascicolo_public;
CREATE  VIEW v_fascicolo_public as 
select 
-1 as id, 
pratica.ente_delegato::text as ufficio, 
pratica.ente_delegato::numeric , 
tipo_procedimento.codice as tipo_procedimento, 
pratica.oggetto as oggetto_intervento,
pratica.codice_pratica_appptr as codice, 
null as codice_interno_ente ,
null as numero_interno_ente,
pratica.numero_protocollo as protocollo,
pratica.data_protocollo::date as data_protocollo, 
localizzazione_intervento.comune_id ,
provvedimento_finale.rup as rup,
provvedimento_finale.numero_provvedimento as numero_provvedimento ,
provvedimento_finale.data_rilascio_autorizzazione as data_rilascio_autorizzazione ,
null::date esito_data_rilascio_autorizzazione ,
provvedimento_finale.esito_provvedimento as esito ,
'NOT_SAMPLED' as esito_verifica ,
pratica.stato as stato ,
-- per filtro:
--tipo intervento (zona=1 va fatto mapping con codice in presentazione_istanza (TIPO_INT_X)
(CASE 
  WHEN tipo_intervento.codice='TIPO_INT_1' then 1
  WHEN tipo_intervento.codice='TIPO_INT_2' then 2
  WHEN tipo_intervento.codice='TIPO_INT_3' then 3
  WHEN tipo_intervento.codice='TIPO_INT_4' then 4
  WHEN tipo_intervento.codice='TIPO_INT_5' then 5
  ELSE 0
  END)   as tipo_intervento,
tipi_e_qualificazioni."label" as label_intervento,
'pae_pres_ist' as applicazione
from 
pratica
left join tipo_procedimento on pratica.tipo_procedimento =tipo_procedimento.id 
left join localizzazione_intervento on pratica.id = localizzazione_intervento.pratica_id 
left join provvedimento_finale on pratica.id =provvedimento_finale.id_pratica 
left join tipo_intervento on pratica.id = tipo_intervento.id_pratica  
left join tipi_e_qualificazioni on tipi_e_qualificazioni.codice = tipo_intervento.codice  
where tipi_e_qualificazioni.raggruppamento = 'TIPO_INT'
AND ( pratica.stato  = 'TRANSMITTED') 
AND ( provvedimento_finale.esito_provvedimento != 'NON_AUTORIZZATO'  );

GRANT SELECT ON TABLE presentazione_istanza.v_fascicolo_public TO autpae;
GRANT USAGE ON SCHEMA presentazione_istanza TO autpae;