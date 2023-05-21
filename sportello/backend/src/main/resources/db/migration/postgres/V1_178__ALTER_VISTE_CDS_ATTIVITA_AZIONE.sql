--effettuare grant su cdst prima di avviare questa flyway
--grant select  on table cdst.tipologia_pratica  to presentazione_istanza;
CREATE OR REPLACE VIEW presentazione_istanza.v_cds_attivita AS
SELECT attivita.codice AS value,
    attivita.descrizione_lunga AS label
   FROM cdst.attivita where fk_tipologia_pratica in (select codice from cdst.tipologia_pratica tp where lower(descrizione)='paesaggio');

CREATE OR REPLACE VIEW presentazione_istanza.v_cds_azione  AS
SELECT azione.codice AS value,
    azione.descrizione_lunga AS label,
    azione.fk_attivita AS attivita
   FROM cdst.azione where fk_attivita  in (
  SELECT attivita.codice
   FROM cdst.attivita where fk_tipologia_pratica in 
  (select codice from cdst.tipologia_pratica tp where lower(descrizione)='paesaggio')
   );