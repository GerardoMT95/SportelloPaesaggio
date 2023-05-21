CREATE OR REPLACE VIEW presentazione_istanza.v_fascicolo_public
AS SELECT '-1'::integer AS id,
    pratica.ente_delegato::text AS ufficio,
    pratica.ente_delegato::numeric AS org_creazione,
    tipo_procedimento.codice AS tipo_procedimento,
    pratica.oggetto AS oggetto_intervento,
    pratica.codice_trasmissione AS codice,
    NULL::unknown AS codice_interno_ente,
    NULL::unknown AS numero_interno_ente,
    pratica.numero_protocollo AS protocollo,
    pratica.data_protocollo::date AS data_protocollo,
    localizzazione_intervento.comune_id,
    provvedimento_finale.rup,
    provvedimento_finale.numero_provvedimento,
    provvedimento_finale.data_rilascio_autorizzazione,
    NULL::date AS esito_data_rilascio_autorizzazione,
    provvedimento_finale.esito_provvedimento AS esito,
    'NOT_SAMPLED' AS esito_verifica,
    pratica.stato,
        CASE
            WHEN tipo_intervento.codice::text = 'TIPO_INT_1'::text THEN 1
            WHEN tipo_intervento.codice::text = 'TIPO_INT_2'::text THEN 2
            WHEN tipo_intervento.codice::text = 'TIPO_INT_3'::text THEN 3
            WHEN tipo_intervento.codice::text = 'TIPO_INT_4'::text THEN 4
            WHEN tipo_intervento.codice::text = 'TIPO_INT_5'::text THEN 5
            ELSE 0
        END AS tipo_intervento,
    tipi_e_qualificazioni.label AS label_intervento,
    'pae_pres_ist' AS applicazione
   FROM presentazione_istanza.pratica
     LEFT JOIN presentazione_istanza.tipo_procedimento ON pratica.tipo_procedimento = tipo_procedimento.id
     LEFT JOIN presentazione_istanza.localizzazione_intervento ON pratica.id = localizzazione_intervento.pratica_id
     LEFT JOIN presentazione_istanza.provvedimento_finale ON pratica.id = provvedimento_finale.id_pratica
     LEFT JOIN presentazione_istanza.tipo_intervento ON pratica.id = tipo_intervento.id_pratica
     LEFT JOIN presentazione_istanza.tipi_e_qualificazioni ON tipi_e_qualificazioni.codice::text = tipo_intervento.codice::text
  WHERE tipi_e_qualificazioni.raggruppamento::text = 'TIPO_INT'::text AND pratica.stato::text = 'TRANSMITTED'::text;
 
GRANT SELECT ON TABLE presentazione_istanza.v_fascicolo_public TO autpae;
GRANT USAGE ON SCHEMA presentazione_istanza TO autpae;

  