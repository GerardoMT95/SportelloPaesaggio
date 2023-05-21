CREATE OR REPLACE VIEW v_fascicolo_public
AS SELECT fascicolo.id,
    fascicolo.ufficio,
    fascicolo.org_creazione,
    fascicolo.tipo_procedimento,
    fascicolo.oggetto_intervento,
    fascicolo.codice,
    fascicolo.codice_interno_ente,
    fascicolo.numero_interno_ente,
    fascicolo.protocollo,
    fascicolo.data_protocollo,
    localizzazione_intervento.comune_id,
    fascicolo.rup,
    fascicolo.numero_provvedimento,
    fascicolo.data_rilascio_autorizzazione,
    fascicolo.esito_data_rilascio_autorizzazione,
    fascicolo.esito,
    fascicolo.esito_verifica,
    fascicolo.stato,
    vtq.id AS tipo_intervento,
    vtq.label AS label_intervento,
    'autpae'::text AS applicazione
   FROM fascicolo
     LEFT JOIN localizzazione_intervento ON fascicolo.id = localizzazione_intervento.pratica_id
     LEFT JOIN v_fascicolo_tipi_e_qualificazioni vtq ON vtq.id = fascicolo.id
  WHERE (fascicolo.stato::text = 'TRANSMITTED'::text OR fascicolo.stato::text = 'SELECTED'::text OR fascicolo.stato::text = 'FINISHED'::text OR fascicolo.stato::text = 'ON_MODIFY'::text);