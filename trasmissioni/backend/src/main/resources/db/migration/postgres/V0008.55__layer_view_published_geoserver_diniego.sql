DROP VIEW  IF EXISTS layer_view_published_dinieghi_geoserver;
CREATE OR REPLACE VIEW layer_view_published_dinieghi_geoserver
AS SELECT 
    f.codice AS codice_pratica,
    g.geometry,
    vfli.comuni,
    f.oggetto_intervento,
    f.note,
    vfteq.label AS tipo_intervento,
    vftp.descrizione AS tipologia_autorizzazione,
    f.rup AS responsabile,
    f.numero_provvedimento AS numero_procedimento,
    to_char(f.data_rilascio_autorizzazione,'dd/mm/yyyy') AS data_procedimento,
        CASE
            WHEN f.esito::text = 'AUT_CON_PRESCRIZ'::text THEN 'AUTORIZZATO CON PRESCRIZIONE'::text
            WHEN f.esito::text = 'NON_AUTORIZZATO'::text THEN 'NON AUTORIZZATO'::text
            ELSE f.esito::text
        END AS esito_richiesta,
    to_char(f.data_trasmissione,'dd/mm/yyyy') AS data_trasmissione,
        CASE
            WHEN "substring"(f.codice::text, 1, 9) = 'AUTPAESAF'::text THEN '016'::text
            ELSE "substring"(f.codice::text, 3, 5)
        END AS istat_amm_competente,
       CASE
            WHEN f.sanatoria = true THEN 'Si'::text
            ELSE 'No'
        END AS sanatoria, 
    '<a href="/'||'${schemaApplicazione}' ||'-fe/public/fascicolo?codiceFascicolo='||trim(f.codice::text)||'" target="_blank">Intercetta negli elenchi</a>' as url_dettaglio
   FROM fascicolo f
     JOIN layer_geo g ON f.id = g.id_fascicolo
     left JOIN v_fascicolo_localizzazione_intervento vfli ON f.id = vfli.pratica_id
     left JOIN v_fascicolo_tipi_e_qualificazioni vfteq ON f.id = vfteq.id
     left JOIN v_fascicolo_tipo_procedimento vftp ON f.id = vftp.id
  WHERE (f.stato::text = 'TRANSMITTED'::text OR f.stato::text = 'SELECTED'::text OR f.stato::text = 'FINISHED'::text OR f.stato::text = 'ON_MODIFY'::text) AND f.esito::text = 'NON_AUTORIZZATO'::text;



 