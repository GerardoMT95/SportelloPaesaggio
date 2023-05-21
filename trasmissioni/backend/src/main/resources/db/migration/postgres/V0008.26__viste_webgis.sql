CREATE OR REPLACE VIEW v_fascicolo_localizzazione_intervento
AS SELECT l_1.pratica_id,
    string_agg(l_1.comune::text, ', '::text) AS comuni
   FROM localizzazione_intervento l_1
  GROUP BY l_1.pratica_id;




CREATE OR REPLACE VIEW v_fascicolo_tipi_e_qualificazioni
AS SELECT f.id,
    teq.zona,
    teq.label
   FROM fascicolo f
     LEFT JOIN fascicolo_intervento ON f.id = fascicolo_intervento.id_fascicolo
     LEFT JOIN tipi_e_qualificazioni teq ON teq.id = fascicolo_intervento.id_tipi_qualificazioni
  WHERE teq.zona = 1 OR teq.id IS NULL;



CREATE OR REPLACE VIEW v_fascicolo_tipo_procedimento
AS SELECT f.id,
    tp.codice,
    tp.descrizione
   FROM fascicolo f
     JOIN tipo_procedimento tp ON f.tipo_procedimento::text = tp.codice::text;
     

drop view if exists layer_view_editing;
CREATE OR REPLACE VIEW layer_view_editing
AS SELECT f.id AS id_fascicolo,
    g.oid,
    f.codice AS codice_pratica,
    g.geometry,
    vfli.comuni,
    f.oggetto_intervento,
    f.note,
    vfteq.label AS tipo_intervento,
    vftp.descrizione AS tipologia_autorizzazione,
    f.rup AS responsabile,
    f.numero_provvedimento AS numero_procedimento,
    f.data_rilascio_autorizzazione AS data_procedimento,
        CASE
            WHEN f.esito::text = 'AUT_CON_PRESCRIZ'::text THEN 'AUTORIZZATO CON PRESCRIZIONE'::text
            WHEN f.esito::text = 'NON_AUTORIZZATO'::text THEN 'NON AUTORIZZATO'::text
            ELSE f.esito::text
        END AS esito_richiesta,
    f.data_trasmissione,
    "substring"(f.codice::text, 3, 5) AS istat_amm_competente,
    f.sanatoria,
    g.is_custom,
    g.is_particella,
    g.user_id,
    g.date_created
   FROM fascicolo f
     JOIN layer_geo g ON f.id = g.id_fascicolo
     JOIN v_fascicolo_localizzazione_intervento vfli ON f.id = vfli.pratica_id
     JOIN v_fascicolo_tipi_e_qualificazioni vfteq ON f.id = vfteq.id
     JOIN v_fascicolo_tipo_procedimento vftp ON f.id = vftp.id;
  
  
drop view if exists layer_view_published;  
CREATE OR REPLACE VIEW layer_view_published
AS SELECT f.id AS id_fascicolo,
    g.oid,
    f.codice AS codice_pratica,
    g.geometry,
    vfli.comuni,
    f.oggetto_intervento,
    f.note,
    vfteq.label AS tipo_intervento,
    vftp.descrizione AS tipologia_autorizzazione,
    f.rup AS responsabile,
    f.numero_provvedimento AS numero_procedimento,
    f.data_rilascio_autorizzazione AS data_procedimento,
        CASE
            WHEN f.esito::text = 'AUT_CON_PRESCRIZ'::text THEN 'AUTORIZZATO CON PRESCRIZIONE'::text
            WHEN f.esito::text = 'NON_AUTORIZZATO'::text THEN 'NON AUTORIZZATO'::text
            ELSE f.esito::text
        END AS esito_richiesta,
    f.data_trasmissione,
    "substring"(f.codice::text, 3, 5) AS istat_amm_competente,
    f.sanatoria,
    g.is_custom,
    g.is_particella,
    g.user_id,
    g.date_created
   FROM fascicolo f
     JOIN layer_geo g ON f.id = g.id_fascicolo
     JOIN v_fascicolo_localizzazione_intervento vfli ON f.id = vfli.pratica_id
     JOIN v_fascicolo_tipi_e_qualificazioni vfteq ON f.id = vfteq.id
     JOIN v_fascicolo_tipo_procedimento vftp ON f.id = vftp.id
  WHERE (f.stato::text = 'TRANSMITTED'::text OR f.stato::text = 'SELECTED'::text OR f.stato::text = 'FINISHED'::text OR f.stato::text = 'ON_MODIFY'::text) AND f.esito::text <> 'NON_AUTORIZZATO'::text;
 
  