DROP VIEW v_report_comune;
DROP VIEW v_report_provincia;


CREATE OR REPLACE VIEW v_report_comune as
select 
       p.id as id_pratica
      ,p.ente_delegato
	  ,p.data_presentazione as data_presentazione
      ,tp.id as id_tipo_procedimento
      ,tp.descrizione as nome_tipo_procedimento
      ,li.comune_id as id_comune
      ,li.comune as nome_comune
FROM presentazione_istanza.pratica p
JOIN presentazione_istanza.tipo_procedimento tp 
	ON tp.id::text = p.tipo_procedimento::text
JOIN presentazione_istanza.localizzazione_intervento li 
	ON li.pratica_id::text = p.id::text
WHERE p.data_presentazione is not null;

comment on view v_report_comune is 'View per filtrare le pratiche per localizzazione comune';

CREATE OR REPLACE VIEW v_report_provincia as
select 
       p.id as id_pratica
      ,p.ente_delegato
	  ,p.data_presentazione as data_presentazione
      ,tp.id as id_tipo_procedimento
      ,tp.descrizione as nome_tipo_procedimento
      ,e_provincia.id as id_provincia
      ,e_provincia.nome as nome_provincia
FROM presentazione_istanza.pratica p
JOIN presentazione_istanza.tipo_procedimento tp 
	ON tp.id::text = p.tipo_procedimento::text
JOIN presentazione_istanza.localizzazione_intervento li 
	ON li.pratica_id::text = p.id::text
JOIN common.ente e_comune 
	ON li.comune_id::text = e_comune.id::text
JOIN common.ente e_provincia 
	ON e_provincia.id::text = e_comune.parent_id::text
WHERE p.data_presentazione is not null;

comment on view v_report_provincia is 'View per filtrare le pratiche per localizzazione provincia';