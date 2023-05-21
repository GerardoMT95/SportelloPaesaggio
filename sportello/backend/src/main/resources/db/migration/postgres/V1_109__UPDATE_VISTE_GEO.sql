drop view if exists v_fascicolo_tipo_procedimento;
create view v_fascicolo_tipo_procedimento as
 select p.id ,tp.codice ,tp.descrizione from presentazione_istanza.pratica p left join 
 presentazione_istanza.tipo_procedimento tp on p.tipo_procedimento =tp.id  ;
 
drop view if exists v_fascicolo_tipi_e_qualificazioni;
create view  v_fascicolo_tipi_e_qualificazioni as
 SELECT f.id,
    teq.sezione as zona,
    teq."label"
   FROM presentazione_istanza.pratica f
     LEFT JOIN presentazione_istanza.tipo_intervento ti ON f.id = ti.id_pratica  
     LEFT JOIN presentazione_istanza.tipi_e_qualificazioni teq ON teq.codice = ti.codice 
  WHERE teq.raggruppamento = 'TIPO_INT' OR teq.codice IS NULL;

drop view if exists v_fascicolo_localizzazione_intervento ;
create view  v_fascicolo_localizzazione_intervento as
SELECT f.id AS pratica_id,
    string_agg(l_1.comune::text, ', '::text) AS comuni
   FROM presentazione_istanza.pratica f
     LEFT JOIN presentazione_istanza.localizzazione_intervento l_1 ON f.id = l_1.pratica_id 
  GROUP BY f.id;
 
drop view if exists v_fascicolo_provvedimento ;
create view v_fascicolo_provvedimento as 
select   
pf.id_pratica, 
pf.numero_provvedimento,
pf.esito_provvedimento,
pf.data_rilascio_autorizzazione ,
pf.rup 
from presentazione_istanza.pratica p
LEFT JOIN presentazione_istanza.provvedimento_finale pf ON p.id = pf.id_pratica;


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
    	when substring(f.codice_trasmissione,1,9)='AUTPAESAF' then '00016'
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
  
  