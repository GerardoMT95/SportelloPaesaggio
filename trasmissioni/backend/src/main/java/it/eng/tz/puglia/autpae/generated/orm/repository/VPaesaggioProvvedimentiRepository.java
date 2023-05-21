package it.eng.tz.puglia.autpae.generated.orm.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;

import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import it.eng.tz.puglia.autpae.generated.orm.search.*;
import it.eng.tz.puglia.autpae.generated.orm.rowmapper.*;

/**
 * Repository for table procedimenti_ambientali.v_paesaggio_provvedimenti
 */
@Repository
public class VPaesaggioProvvedimentiRepository extends GenericCrudDao<VPaesaggioProvvedimentiDTO, VPaesaggioProvvedimentiSearch>{

    private final VPaesaggioProvvedimentiRowMapper rowMapper = new VPaesaggioProvvedimentiRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id_doc\"")
                                     .append(",\"id_pratica\"")
                                     .append(",\"id_tipo_doc\"")
                                     .append(",\"descrizione_tipo_doc\"")
                                     .append(",\"nome_procedimento\"")
                                     .append(",\"nome_famiglia\"")
                                     .append(",\"nome_autorita_procedente\"")
                                     .append(",\"nome_soggetto_coinvolto\"")
                                     .append(",\"codice_trasmissione\"")
                                     .append(",\"oggetto\"")
                                     .append(",\"id_tipo_carica_documento\"")
                                     .append(",\"numero_protocollo_esterno\"")
                                     .append(",\"data_protocollo_esterno\"")
                                     .append(",\"cmis_id\"")
                                     .append(",\"file_name\"")
                                     .append(",\"hash_file\"")
                                     .append(",\"codice_fiscale_ente\"")
                                     .append(",\"ente_id\"")
                                     .append(",\"tipo\"")
                                     .append(",\"id\"")
                                     .append(",\"indice\"")
                                     .append(",\"cognome\"")
                                     .append(",\"nome\"")
                                     .append(",\"codice_fiscale\"")
                                     .append(",\"sesso\"")
                                     .append(",\"data_nascita\"")
                                     .append(",\"id_nazione_nascita\"")
                                     .append(",\"nazione_nascita\"")
                                     .append(",\"id_comune_nascita\"")
                                     .append(",\"comune_nascita\"")
                                     .append(",\"comune_nascita_estero\"")
                                     .append(",\"id_nazione_residenza\"")
                                     .append(",\"nazione_residenza\"")
                                     .append(",\"id_comune_residenza\"")
                                     .append(",\"comune_residenza\"")
                                     .append(",\"comune_residenza_estero\"")
                                     .append(",\"indirizzo_residenza\"")
                                     .append(",\"civico_residenza\"")
                                     .append(",\"cap_residenza\"")
                                     .append(",\"telefono\"")
                                     .append(",\"pec\"")
                                     .append(",\"mail\"")
                                     .append(",\"ditta\"")
                                     .append(",\"id_ruolo_azienda\"")
                                     .append(",\"nome_ruolo_azienda\"")
                                     .append(",\"altro_ruolo_azienda\"")
                                     .append(",\"piva_azienda\"")
                                     .append(",\"codice_fiscale_azienda\"")
                                     .append(",\"id_tipo_documento\"")
                                     .append(",\"numero_documento\"")
                                     .append(",\"data_rilascio_documento\"")
                                     .append(",\"data_scadenza_documento\"")
                                     .append(",\"cmis_documento\"")
                                     .append(",\"nome_documento\"")
                                     .append(",\"titolarita\"")
                                     .append(",\"titolarita_altro\"")
                                     .append(",\"hash_documento\"")
                                     .append(",\"azienda\"")
                                     .append(",\"data_documento_identita\"")
                                     .append(",\"ente_rilascio_documento\"")
                                     .append(",\"date_create\"")
                                     .append(",\"id_tipo_azienda\"")
                                     .append(",\"tipo_azienda\"")
                                     .append(",\"c_ipa\"")
                                     .append(",\"c_uo\"")
                                     .append(" from  \"procedimenti_ambientali\".\"v_paesaggio_provvedimenti\"")
                                     .toString();
    @Override
    public List<VPaesaggioProvvedimentiDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"v_paesaggio_provvedimenti\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public VPaesaggioProvvedimentiDTO find(VPaesaggioProvvedimentiDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<VPaesaggioProvvedimentiDTO> search(VPaesaggioProvvedimentiSearch search){
        final Map<String,Object>  parameters = new HashMap<>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getIdDoc())){
            sql.append(sep).append("lower(\"id_doc\"::text) like :idDoc");
            parameters.put("idDoc",StringUtil.convertLike(search.getIdDoc()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceTrasmissione())){
            sql.append(sep).append("lower(\"codice_trasmissione\"::text) like :codiceTrasmissione");
            parameters.put("codiceTrasmissione",StringUtil.convertLike(search.getCodiceTrasmissione().toLowerCase()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getOggetto())){
            sql.append(sep).append("lower(\"oggetto\"::text) like :oggetto ");
            parameters.put("oggetto",StringUtil.convertLike(search.getOggetto().toLowerCase()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdTipoCaricaDocumento())){
            sql.append(sep).append("lower(\"id_tipo_carica_documento\"::text) like :idTipoCaricaDocumento ");
            parameters.put("idTipoCaricaDocumento",StringUtil.convertLike(search.getIdTipoCaricaDocumento().toLowerCase()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEnteId())){
            sql.append(sep).append("( \"ente_id\"::text = :enteId ");
            parameters.put("enteId",search.getEnteId());
            sep = " and ";
            if(StringUtil.isNotEmpty(search.getCodiceFiscaleEnte())){
                sql
                .append(" OR ")
                .append(" \"codice_fiscale_ente\" = :codiceFiscaleEnte )");
                parameters.put("codiceFiscaleEnte",search.getCodiceFiscaleEnte());
            }else {
            	sql.append(" OR false )");
            }
        }
        if(search.isSoloNonUtilizzati()){
            sql
            .append(sep)
            .append(" id_doc::text not in ")
            .append(" (SELECT id_doc_caricato::text FROM doc_caricati_sportello WHERE deleted=false ) ");
            sep = " and ";
        }
        if(ListUtil.isNotEmpty(search.getIdTipoCaricaDocumentoAmmessi())){
            sql.append(sep).append(" id_tipo_carica_documento in (:idTipoCaricaDocumentoAmmessi) ");
            parameters.put("idTipoCaricaDocumentoAmmessi",search.getIdTipoCaricaDocumentoAmmessi());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "idDoc":
                    sql.append(" sort by \"id_doc\" ").append(sortType);
            case "idPratica":
                    sql.append(" sort by \"id_pratica\" ").append(sortType);
            case "codiceTrasmissione":
                    sql.append(" sort by \"codice_trasmissione\" ").append(sortType);
            case "oggetto":
                    sql.append(" sort by \"oggetto\" ").append(sortType);
            case "idTipoCaricaDocumento":
                    sql.append(" sort by \"id_tipo_carica_documento\" ").append(sortType);
            case "numeroProtocolloEsterno":
                    sql.append(" sort by \"numero_protocollo_esterno\" ").append(sortType);
            case "dataProtocolloEsterno":
                    sql.append(" sort by \"data_protocollo_esterno\" ").append(sortType);
            case "cmisId":
                    sql.append(" sort by \"cmis_id\" ").append(sortType);
            case "fileName":
                    sql.append(" sort by \"file_name\" ").append(sortType);
            case "hashFile":
                    sql.append(" sort by \"hash_file\" ").append(sortType);
            case "codiceFiscaleEnte":
                    sql.append(" sort by \"codice_fiscale_ente\" ").append(sortType);
            case "enteId":
                    sql.append(" sort by \"ente_id\" ").append(sortType);
            case "tipo":
                    sql.append(" sort by \"tipo\" ").append(sortType);
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "indice":
                    sql.append(" sort by \"indice\" ").append(sortType);
            case "cognome":
                    sql.append(" sort by \"cognome\" ").append(sortType);
            case "nome":
                    sql.append(" sort by \"nome\" ").append(sortType);
            case "codiceFiscale":
                    sql.append(" sort by \"codice_fiscale\" ").append(sortType);
            case "sesso":
                    sql.append(" sort by \"sesso\" ").append(sortType);
            case "dataNascita":
                    sql.append(" sort by \"data_nascita\" ").append(sortType);
            case "idNazioneNascita":
                    sql.append(" sort by \"id_nazione_nascita\" ").append(sortType);
            case "nazioneNascita":
                    sql.append(" sort by \"nazione_nascita\" ").append(sortType);
            case "idComuneNascita":
                    sql.append(" sort by \"id_comune_nascita\" ").append(sortType);
            case "comuneNascita":
                    sql.append(" sort by \"comune_nascita\" ").append(sortType);
            case "comuneNascitaEstero":
                    sql.append(" sort by \"comune_nascita_estero\" ").append(sortType);
            case "idNazioneResidenza":
                    sql.append(" sort by \"id_nazione_residenza\" ").append(sortType);
            case "nazioneResidenza":
                    sql.append(" sort by \"nazione_residenza\" ").append(sortType);
            case "idComuneResidenza":
                    sql.append(" sort by \"id_comune_residenza\" ").append(sortType);
            case "comuneResidenza":
                    sql.append(" sort by \"comune_residenza\" ").append(sortType);
            case "comuneResidenzaEstero":
                    sql.append(" sort by \"comune_residenza_estero\" ").append(sortType);
            case "indirizzoResidenza":
                    sql.append(" sort by \"indirizzo_residenza\" ").append(sortType);
            case "civicoResidenza":
                    sql.append(" sort by \"civico_residenza\" ").append(sortType);
            case "capResidenza":
                    sql.append(" sort by \"cap_residenza\" ").append(sortType);
            case "telefono":
                    sql.append(" sort by \"telefono\" ").append(sortType);
            case "pec":
                    sql.append(" sort by \"pec\" ").append(sortType);
            case "mail":
                    sql.append(" sort by \"mail\" ").append(sortType);
            case "ditta":
                    sql.append(" sort by \"ditta\" ").append(sortType);
            case "idRuoloAzienda":
                    sql.append(" sort by \"id_ruolo_azienda\" ").append(sortType);
            case "nomeRuoloAzienda":
                    sql.append(" sort by \"nome_ruolo_azienda\" ").append(sortType);
            case "altroRuoloAzienda":
                    sql.append(" sort by \"altro_ruolo_azienda\" ").append(sortType);
            case "pivaAzienda":
                    sql.append(" sort by \"piva_azienda\" ").append(sortType);
            case "codiceFiscaleAzienda":
                    sql.append(" sort by \"codice_fiscale_azienda\" ").append(sortType);
            case "idTipoDocumento":
                    sql.append(" sort by \"id_tipo_documento\" ").append(sortType);
            case "numeroDocumento":
                    sql.append(" sort by \"numero_documento\" ").append(sortType);
            case "dataRilascioDocumento":
                    sql.append(" sort by \"data_rilascio_documento\" ").append(sortType);
            case "dataScadenzaDocumento":
                    sql.append(" sort by \"data_scadenza_documento\" ").append(sortType);
            case "cmisDocumento":
                    sql.append(" sort by \"cmis_documento\" ").append(sortType);
            case "nomeDocumento":
                    sql.append(" sort by \"nome_documento\" ").append(sortType);
            case "titolarita":
                    sql.append(" sort by \"titolarita\" ").append(sortType);
            case "titolaritaAltro":
                    sql.append(" sort by \"titolarita_altro\" ").append(sortType);
            case "hashDocumento":
                    sql.append(" sort by \"hash_documento\" ").append(sortType);
            case "azienda":
                    sql.append(" sort by \"azienda\" ").append(sortType);
            case "dataDocumentoIdentita":
                    sql.append(" sort by \"data_documento_identita\" ").append(sortType);
            case "enteRilascioDocumento":
                    sql.append(" sort by \"ente_rilascio_documento\" ").append(sortType);
            case "dateCreate":
                    sql.append(" sort by \"date_create\" ").append(sortType);
            case "idTipoAzienda":
                    sql.append(" sort by \"id_tipo_azienda\" ").append(sortType);
            case "tipoAzienda":
                    sql.append(" sort by \"tipo_azienda\" ").append(sortType);
            case "cIpa":
                    sql.append(" sort by \"c_ipa\" ").append(sortType);
            case "cUo":
                    sql.append(" sort by \"c_uo\" ").append(sortType);
                break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        }
        return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(VPaesaggioProvvedimentiDTO entity){
    	throw new RuntimeException("Non implementato!!!");    
    }

    /**
     * update by pk method
     */
    @Override
    public int update(VPaesaggioProvvedimentiDTO entity){
    	throw new RuntimeException("Non implementato!!!");    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(VPaesaggioProvvedimentiDTO entity){
    	throw new RuntimeException("Non implementato!!!");
    }

	/**
	 * @autor Adriano Colaianni
	 * @date 25 lug 2022
	 * @param idEnte
	 * @param codiceFiscaleEnte
	 * @param codiceTrasmissione
	 * @param idDoc opzionale... se null non nel where
	 * @return
	 */
	public List<VPaesaggioProvvedimentiDTO> findByIdEnteCodiceTrasmissioneCodiceFiscaleEnteIdProvvedimento(Long idEnte,
			String codiceFiscaleEnte, String codiceTrasmissione, UUID idDoc) {
		StringBuilder query=new StringBuilder(selectAll);
		Map<String,Object> parameters=new HashMap<>();
		query
		.append(" WHERE ")
		.append(" ( ente_id= :idEnte  OR ")
		.append("  (codice_fiscale_ente= :codiceFiscaleEnte  AND not codice_fiscale_ente IS NULL )) ")
		.append(" AND ")
		.append(" codice_trasmissione= :codiceTrasmissione ");
		parameters.put("idEnte", idEnte);
		parameters.put("codiceFiscaleEnte", codiceFiscaleEnte);
		parameters.put("codiceTrasmissione", codiceTrasmissione);
		if(idDoc!=null) {
			query.append(" AND id_doc::text = :idDoc ");
			parameters.put("idDoc", idDoc.toString());
		}
		query
		.append(" AND id_doc::text not in ")
		.append(" (SELECT id_doc_caricato::text FROM doc_caricati_sportello WHERE deleted=false ) ")
		;
		return super.namedQueryForListTxRead(query.toString(), this.rowMapper,parameters);
	}
    
    
    

}
