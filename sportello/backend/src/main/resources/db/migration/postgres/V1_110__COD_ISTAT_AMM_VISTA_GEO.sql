drop view if exists presentazione_istanza.layer_view_published; 
create view layer_view_published as 
 SELECT f.id AS id_fascicolo,
    g.oid,
    f.codice_trasmissione AS codice_pratica,
    g.geom geom,
    vfli.comuni,
    f.oggetto oggetto_intervento,
    '' note,
    vfteq.label AS tipo_intervento,
    vftp.descrizione AS tipologia_autorizzazione,
    fp.rup AS responsabile,
    fp.numero_provvedimento AS numero_procedimento,
    fp.data_rilascio_autorizzazione AS data_procedimento,
        CASE
            WHEN fp.esito_provvedimento::text = 'AUT_CON_PRESCRIZ'::text THEN 'AUTORIZZATO CON PRESCRIZIONE'::text
            WHEN fp.esito_provvedimento::text = 'NON_AUTORIZZATO'::text THEN 'NON AUTORIZZATO'::text
            ELSE fp.esito_provvedimento::text
        END AS esito_richiesta,
    f.data_trasmissione_provvedimento_finale data_trasmissione,
     case 
    	when substring(f.codice_trasmissione,1,9)='AUTPAESAF' then '016'
    	else "substring"(f.codice_trasmissione ::text, 3, 5)
    end AS istat_amm_competente,
    f.in_sanatoria sanatoria,
    g.is_custom,
    g.is_particella,
    g.user_id,
    g.date_created
   FROM presentazione_istanza.pratica f
     left JOIN presentazione_istanza.v_fascicolo_provvedimento fp ON f.id = fp.id_pratica
     JOIN presentazione_istanza.layer_geo g ON f.id = g.id_fascicolo
     left JOIN presentazione_istanza.v_fascicolo_localizzazione_intervento vfli ON f.id = vfli.pratica_id
     left JOIN presentazione_istanza.v_fascicolo_tipi_e_qualificazioni vfteq ON f.id = vfteq.id
     left JOIN presentazione_istanza.v_fascicolo_tipo_procedimento vftp ON f.id = vftp.id
  WHERE (f.stato::text = 'TRANSMITTED'::text OR f.stato::text = 'SELECTED'::text OR f.stato::text = 'FINISHED'::text OR f.stato::text = 'ON_MODIFY'::text) AND fp.esito_provvedimento::text <> 'NON_AUTORIZZATO'::text;
  
  