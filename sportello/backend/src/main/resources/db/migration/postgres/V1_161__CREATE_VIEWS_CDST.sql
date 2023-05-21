
CREATE OR REPLACE VIEW presentazione_istanza.v_cds_attivita
AS SELECT attivita.codice AS value,
    attivita.descrizione_lunga AS label
   FROM cdst.attivita;

COMMENT ON VIEW v_cds_attivita IS 'Vista che restituisce il codice e la descrizione delle attività';

CREATE OR REPLACE VIEW presentazione_istanza.v_cds_azione
AS SELECT azione.codice AS value,
    azione.descrizione_lunga AS label,
    azione.fk_attivita AS attivita
   FROM cdst.azione;

COMMENT ON VIEW v_cds_azione IS 'Vista che restituisce il codice la descrizione e la chiave corrispondente all''attività correlata';
   
CREATE OR REPLACE VIEW presentazione_istanza.v_cds_tipo
AS SELECT tipologia_conferenza_specializzazione.codice AS value,
        CASE
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.semplificata'::text THEN 'Semplificata'::text
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.simultanea'::text THEN 'Simultanea'::text
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.regionale'::text THEN 'Regionale'::text
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.BULDecisoriaSimultanea'::text THEN 'BUL-decisoria'::text
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.BUListruttoriaSimultanea'::text THEN 'BUL-preistruttoria'::text
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.incontroOperativo'::text THEN 'Incontro operativo'::text
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.decisoriaAmbienteVIA'::text THEN 'Decisoria Ambiente VIA'::text
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.istruttoriaAmbienteVIA'::text THEN 'Istruttoria Ambiente VIA'::text
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.decisoriaAmbienteAIA'::text THEN 'Decisoria Ambiente AIA'::text
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.istruttoriaAmbienteAIA'::text THEN 'Istruttoria Ambiente AIA'::text
            WHEN tipologia_conferenza_specializzazione.descrizione::text = 'tipologiaConferenzaSpec.domus'::text THEN 'Regionale (DOMUS)'::text
            ELSE '-'::text
        END AS label
   FROM cdst.tipologia_conferenza_specializzazione;
  
COMMENT ON VIEW v_cds_tipo IS 'Vista che restituisce il codice e la descrizione della tipologia di conferenza specializzata';