package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SubentroDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.SubentroRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SubentroSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.subentro
 */
@Repository
public class SubentroRepository extends GenericCrudDao<SubentroDTO, SubentroSearch>{

    private final SubentroRowMapper rowMapper = new SubentroRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"id_pratica\"")
                                     .append(",\"stato\"")
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
                                     .append(",\"pec\"")
                                     .append(",\"mail\"")
                                     .append(",\"date_create\"")
                                     .append(",\"date_update\"")
                                     .append(",\"cmis_id_modulo\"")
                                     .append(",\"file_name_modulo\"")
                                     .append(",\"cmis_id_sollevamento\"")
                                     .append(",\"file_name_sollevamento\"")
                                     .append(",\"hash_modulo\"")
                                     .append(",\"hash_sollevamento\"")
                                     .append(",\"id_provincia_nascita\"")
                                     .append(",\"provincia_nascita\"")
                                     .append(",\"id_provincia_residenza\"")
                                     .append(",\"provincia_residenza\"")
                                     .append(" from \"presentazione_istanza\".\"subentro\"")
                                     .toString();
    @Override
    public List<SubentroDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"subentro\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public SubentroDTO find(SubentroDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<SubentroDTO> search(SubentroSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdPratica())){
            sql.append(sep).append("lower(\"id_pratica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdPratica()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStato())){
            sql.append(sep).append("lower(\"stato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStato()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCognome())){
            sql.append(sep).append("lower(\"cognome\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCognome()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNome())){
            sql.append(sep).append("lower(\"nome\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNome()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceFiscale())){
            sql.append(sep).append("lower(\"codice_fiscale\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCodiceFiscale()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSesso())){
            sql.append(sep).append("lower(\"sesso\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getSesso()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataNascita())){
            sql.append(sep).append("lower(\"data_nascita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataNascita()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdNazioneNascita())){
            sql.append(sep).append("lower(\"id_nazione_nascita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdNazioneNascita()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNazioneNascita())){
            sql.append(sep).append("lower(\"nazione_nascita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNazioneNascita()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdComuneNascita())){
            sql.append(sep).append("lower(\"id_comune_nascita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdComuneNascita()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getComuneNascita())){
            sql.append(sep).append("lower(\"comune_nascita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getComuneNascita()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getComuneNascitaEstero())){
            sql.append(sep).append("lower(\"comune_nascita_estero\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getComuneNascitaEstero()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdNazioneResidenza())){
            sql.append(sep).append("lower(\"id_nazione_residenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdNazioneResidenza()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNazioneResidenza())){
            sql.append(sep).append("lower(\"nazione_residenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNazioneResidenza()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdComuneResidenza())){
            sql.append(sep).append("lower(\"id_comune_residenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdComuneResidenza()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getComuneResidenza())){
            sql.append(sep).append("lower(\"comune_residenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getComuneResidenza()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getComuneResidenzaEstero())){
            sql.append(sep).append("lower(\"comune_residenza_estero\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getComuneResidenzaEstero()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIndirizzoResidenza())){
            sql.append(sep).append("lower(\"indirizzo_residenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIndirizzoResidenza()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCivicoResidenza())){
            sql.append(sep).append("lower(\"civico_residenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCivicoResidenza()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCapResidenza())){
            sql.append(sep).append("lower(\"cap_residenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCapResidenza()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPec())){
            sql.append(sep).append("lower(\"pec\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPec()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getMail())){
            sql.append(sep).append("lower(\"mail\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getMail()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDateCreate())){
            sql.append(sep).append("lower(\"date_create\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDateCreate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDateUpdate())){
            sql.append(sep).append("lower(\"date_update\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDateUpdate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCmisIdModulo())){
            sql.append(sep).append("lower(\"cmis_id_modulo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCmisIdModulo()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFileNameModulo())){
            sql.append(sep).append("lower(\"file_name_modulo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getFileNameModulo()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCmisIdSollevamento())){
            sql.append(sep).append("lower(\"cmis_id_sollevamento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCmisIdSollevamento()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFileNameSollevamento())){
            sql.append(sep).append("lower(\"file_name_sollevamento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getFileNameSollevamento()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getHashModulo())){
            sql.append(sep).append("lower(\"hash_modulo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getHashModulo()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getHashSollevamento())){
            sql.append(sep).append("lower(\"hash_sollevamento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getHashSollevamento()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdProvinciaNascita())){
            sql.append(sep).append("lower(\"id_provincia_nascita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdProvinciaNascita()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProvinciaNascita())){
            sql.append(sep).append("lower(\"provincia_nascita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProvinciaNascita()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdProvinciaResidenza())){
            sql.append(sep).append("lower(\"id_provincia_residenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdProvinciaResidenza()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProvinciaResidenza())){
            sql.append(sep).append("lower(\"provincia_residenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProvinciaResidenza()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" order by \"id\" ").append(sortType);
                	   break;
            case "idPratica":
                    sql.append(" order by \"id_pratica\" ").append(sortType);
                	   break;
            case "stato":
                    sql.append(" order by \"stato\" ").append(sortType);
                	   break;
            case "cognome":
                    sql.append(" order by \"cognome\" ").append(sortType);
                	   break;
            case "nome":
                    sql.append(" order by \"nome\" ").append(sortType);
                	   break;
            case "codiceFiscale":
                    sql.append(" order by \"codice_fiscale\" ").append(sortType);
                	   break;
            case "sesso":
                    sql.append(" order by \"sesso\" ").append(sortType);
                	   break;
            case "dataNascita":
                    sql.append(" order by \"data_nascita\" ").append(sortType);
                	   break;
            case "idNazioneNascita":
                    sql.append(" order by \"id_nazione_nascita\" ").append(sortType);
                	   break;
            case "nazioneNascita":
                    sql.append(" order by \"nazione_nascita\" ").append(sortType);
                	   break;
            case "idComuneNascita":
                    sql.append(" order by \"id_comune_nascita\" ").append(sortType);
                	   break;
            case "comuneNascita":
                    sql.append(" order by \"comune_nascita\" ").append(sortType);
                	   break;
            case "comuneNascitaEstero":
                    sql.append(" order by \"comune_nascita_estero\" ").append(sortType);
                	   break;
            case "idNazioneResidenza":
                    sql.append(" order by \"id_nazione_residenza\" ").append(sortType);
                	   break;
            case "nazioneResidenza":
                    sql.append(" order by \"nazione_residenza\" ").append(sortType);
                	   break;
            case "idComuneResidenza":
                    sql.append(" order by \"id_comune_residenza\" ").append(sortType);
                	   break;
            case "comuneResidenza":
                    sql.append(" order by \"comune_residenza\" ").append(sortType);
                	   break;
            case "comuneResidenzaEstero":
                    sql.append(" order by \"comune_residenza_estero\" ").append(sortType);
                	   break;
            case "indirizzoResidenza":
                    sql.append(" order by \"indirizzo_residenza\" ").append(sortType);
                	   break;
            case "civicoResidenza":
                    sql.append(" order by \"civico_residenza\" ").append(sortType);
                	   break;
            case "capResidenza":
                    sql.append(" order by \"cap_residenza\" ").append(sortType);
                	   break;
            case "pec":
                    sql.append(" order by \"pec\" ").append(sortType);
                	   break;
            case "mail":
                    sql.append(" order by \"mail\" ").append(sortType);
                	   break;
            case "dateCreate":
                    sql.append(" order by \"date_create\" ").append(sortType);
                	   break;
            case "dateUpdate":
                    sql.append(" order by \"date_update\" ").append(sortType);
                	   break;
            case "cmisIdModulo":
                    sql.append(" order by \"cmis_id_modulo\" ").append(sortType);
                	   break;
            case "fileNameModulo":
                    sql.append(" order by \"file_name_modulo\" ").append(sortType);
                	   break;
            case "cmisIdSollevamento":
                    sql.append(" order by \"cmis_id_sollevamento\" ").append(sortType);
                	   break;
            case "fileNameSollevamento":
                    sql.append(" order by \"file_name_sollevamento\" ").append(sortType);
                	   break;
            case "hashModulo":
                    sql.append(" order by \"hash_modulo\" ").append(sortType);
                	   break;
            case "hashSollevamento":
                    sql.append(" order by \"hash_sollevamento\" ").append(sortType);
                	   break;
            case "idProvinciaNascita":
                	sql.append(" order by \"id_provincia_nascita\" ").append(sortType);
                	break;
            case "provinciaNascita":
                	sql.append(" order by \"provincia_nascita\" ").append(sortType);
                	break;
            case "idProvinciaResidenza":
                	sql.append(" order by \"id_provincia_residenza\" ").append(sortType);
                	break;
            case "provinciaResidenza":
                	sql.append(" order by \"provincia_residenza\" ").append(sortType);    	   
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
    public long insert(SubentroDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"subentro\"")
                                     .append("(\"id\"")
                                     .append(",\"id_pratica\"")
                                     .append(",\"stato\"")
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
                                     .append(",\"pec\"")
                                     .append(",\"mail\"")
                                     .append(",\"date_create\"")
                                     .append(",\"cmis_id_modulo\"")
                                     .append(",\"file_name_modulo\"")
                                     .append(",\"cmis_id_sollevamento\"")
                                     .append(",\"file_name_sollevamento\"")
                                     .append(",\"hash_modulo\"")
                                     .append(",\"hash_sollevamento\"")
                                     .append(",\"id_provincia_nascita\"")
                                     .append(",\"provincia_nascita\"")
                                     .append(",\"id_provincia_residenza\"")
                                     .append(",\"provincia_residenza\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",current_timestamp")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        parameters.add(entity.getIdPratica());
        parameters.add(entity.getStato());
        parameters.add(entity.getCognome());
        parameters.add(entity.getNome());
        parameters.add(entity.getCodiceFiscale());
        parameters.add(entity.getSesso());
        parameters.add(entity.getDataNascita());
        parameters.add(entity.getIdNazioneNascita());
        parameters.add(entity.getNazioneNascita());
        parameters.add(entity.getIdComuneNascita());
        parameters.add(entity.getComuneNascita());
        parameters.add(entity.getComuneNascitaEstero());
        parameters.add(entity.getIdNazioneResidenza());
        parameters.add(entity.getNazioneResidenza());
        parameters.add(entity.getIdComuneResidenza());
        parameters.add(entity.getComuneResidenza());
        parameters.add(entity.getComuneResidenzaEstero());
        parameters.add(entity.getIndirizzoResidenza());
        parameters.add(entity.getCivicoResidenza());
        parameters.add(entity.getCapResidenza());
        parameters.add(entity.getPec());
        parameters.add(entity.getMail());
        parameters.add(entity.getCmisIdModulo());
        parameters.add(entity.getFileNameModulo());
        parameters.add(entity.getCmisIdSollevamento());
        parameters.add(entity.getFileNameSollevamento());
        parameters.add(entity.getHashModulo());
        parameters.add(entity.getHashSollevamento());
        parameters.add(entity.getIdProvinciaNascita());
        parameters.add(entity.getProvinciaNascita());
        parameters.add(entity.getIdProvinciaResidenza());
        parameters.add(entity.getProvinciaResidenza());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(SubentroDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"subentro\"")
                                     .append(" set \"id_pratica\" = ?")
                                     .append(", \"stato\" = ?")
                                     .append(", \"cognome\" = ?")
                                     .append(", \"nome\" = ?")
                                     .append(", \"codice_fiscale\" = ?")
                                     .append(", \"sesso\" = ?")
                                     .append(", \"data_nascita\" = ?")
                                     .append(", \"id_nazione_nascita\" = ?")
                                     .append(", \"nazione_nascita\" = ?")
                                     .append(", \"id_comune_nascita\" = ?")
                                     .append(", \"comune_nascita\" = ?")
                                     .append(", \"comune_nascita_estero\" = ?")
                                     .append(", \"id_nazione_residenza\" = ?")
                                     .append(", \"nazione_residenza\" = ?")
                                     .append(", \"id_comune_residenza\" = ?")
                                     .append(", \"comune_residenza\" = ?")
                                     .append(", \"comune_residenza_estero\" = ?")
                                     .append(", \"indirizzo_residenza\" = ?")
                                     .append(", \"civico_residenza\" = ?")
                                     .append(", \"cap_residenza\" = ?")
                                     .append(", \"pec\" = ?")
                                     .append(", \"mail\" = ?")
                                     .append(", \"date_create\" = ?")
                                     .append(", \"date_update\" = ?")
                                     .append(", \"cmis_id_modulo\" = ?")
                                     .append(", \"file_name_modulo\" = ?")
                                     .append(", \"cmis_id_sollevamento\" = ?")
                                     .append(", \"file_name_sollevamento\" = ?")
                                     .append(", \"hash_modulo\" = ?")
                                     .append(", \"hash_sollevamento\" = ?")
                                     .append(", \"id_provincia_nascita\" = ?")
                                     .append(", \"provincia_nascita\" = ?")
                                     .append(", \"id_provincia_residenza\" = ?")
                                     .append(", \"provincia_residenza\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdPratica());
        parameters.add(entity.getStato());
        parameters.add(entity.getCognome());
        parameters.add(entity.getNome());
        parameters.add(entity.getCodiceFiscale());
        parameters.add(entity.getSesso());
        parameters.add(entity.getDataNascita());
        parameters.add(entity.getIdNazioneNascita());
        parameters.add(entity.getNazioneNascita());
        parameters.add(entity.getIdComuneNascita());
        parameters.add(entity.getComuneNascita());
        parameters.add(entity.getComuneNascitaEstero());
        parameters.add(entity.getIdNazioneResidenza());
        parameters.add(entity.getNazioneResidenza());
        parameters.add(entity.getIdComuneResidenza());
        parameters.add(entity.getComuneResidenza());
        parameters.add(entity.getComuneResidenzaEstero());
        parameters.add(entity.getIndirizzoResidenza());
        parameters.add(entity.getCivicoResidenza());
        parameters.add(entity.getCapResidenza());
        parameters.add(entity.getPec());
        parameters.add(entity.getMail());
        parameters.add(entity.getDateCreate());
        parameters.add(entity.getDateUpdate());
        parameters.add(entity.getCmisIdModulo());
        parameters.add(entity.getFileNameModulo());
        parameters.add(entity.getCmisIdSollevamento());
        parameters.add(entity.getFileNameSollevamento());
        parameters.add(entity.getHashModulo());
        parameters.add(entity.getHashSollevamento());
        parameters.add(entity.getIdProvinciaNascita());
        parameters.add(entity.getProvinciaNascita());
        parameters.add(entity.getIdProvinciaResidenza());
        parameters.add(entity.getProvinciaResidenza());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(SubentroDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"subentro\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    /**
     * 
     * @author acolaianni
     *
     * @param cfUsername
     * @param idPratica
     * @param stato
     * @return
     */
    public SubentroDTO findByCfUserAndPraticaAndStato(String cfUsername,UUID idPratica,String stato){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id_pratica\" = ?")
                                     .append(" and \"codice_fiscale\" = ?")
                                     .append(" and \"stato\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        parameters.add(cfUsername);
        parameters.add(stato);
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
	public String subentroExists(final String codicePratica, final String codiceFiscaleRichiedente, final String codiceSegreto) throws Exception
	{
	    final String sql = new StringBuilder("SELECT p.id")
	    				 .append(" FROM presentazione_istanza.pratica p")
	    				 .append(" JOIN presentazione_istanza.referenti r")
	    				 .append(" ON r.pratica_id = p.id")
		    			 .append(" WHERE p.codice_pratica_appptr = :codicePratica")
		    			 .append(" AND r.codice_fiscale = :codiceFiscaleRichiedente")
		    			 .append(" AND p.codice_segreto = :codiceSegreto")
		    			 .append(" AND p.owner != :usernameLoggato")
		    			 .append(" AND r.codice_fiscale != :cfLoggato")
		    			 .toString();
	    final Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put("codicePratica", codicePratica);
	    parameters.put("codiceFiscaleRichiedente", codiceFiscaleRichiedente);
	    parameters.put("codiceSegreto", codiceSegreto);
	    parameters.put("usernameLoggato", SecurityUtil.getUsername());
	    parameters.put("cfLoggato", SecurityUtil.getUserDetail().getFiscalCode());

	    logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
	    return namedJdbcTemplateRead.queryForObject(sql, parameters, String.class);
	}
	
    public SubentroDTO getCurrentSubentro(final UUID idPratica){
        final String sql = new StringBuilder(selectAll)
                .append(" WHERE \"id_pratica\" = ?")
                .append(" AND \"stato\" = 'B'")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    public int endSubentro(final UUID idPratica){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"subentro\"")
                                     .append(" set \"stato\" = 'C'")
                                     .append(", \"date_update\" = current_timestamp")
                                     .append(" where \"id_pratica\" = ?")
                                     .append(" and \"stato\" = 'B'")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.update(sql, parameters);
    }
    
    public int cancellaSubentriNonConclusi(final UUID idPratica){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"subentro\"")
                					.append(" where \"id_pratica\" = ?")
                					.append(" and \"stato\" = 'B'")
                                    .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.update(sql, parameters);
    }
    
    public int addModulo(final UUID idPratica, final String cmisIdModulo, final String fileNameModulo, final String hash){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"subentro\"")
                                     .append(" set \"cmis_id_modulo\" = ?")
                                     .append(", \"file_name_modulo\" = ?")
                                     .append(", \"hash_modulo\" = ?")
                                     .append(", \"date_update\" = current_timestamp")
                                     .append(" where \"id_pratica\" = ?")
                                     .append(" and \"stato\" = 'B'")
                                     .append(" and \"codice_fiscale\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(cmisIdModulo);
        parameters.add(fileNameModulo);
        parameters.add(hash);
        parameters.add(idPratica);
        parameters.add(SecurityUtil.getUserDetail().getFiscalCode());
        return super.update(sql, parameters);
    }
    
    public int deleteModulo(final UUID idPratica){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"subentro\"")
                                     .append(" set \"cmis_id_modulo\" = null")
                                     .append(", \"file_name_modulo\" = null")
                                     .append(", \"hash_modulo\" = null")
                                     .append(", \"date_update\" = current_timestamp")
                                     .append(" where \"id_pratica\" = ?")
                                     .append(" and \"stato\" = 'B'")
                                     .append(" and \"codice_fiscale\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        parameters.add(SecurityUtil.getUserDetail().getFiscalCode());
        return super.update(sql, parameters);
    }
    
    public int addSollevamento(final UUID idPratica, final String cmisIdSollevamento, final String fileNameSollevamento, final String hash){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"subentro\"")
                                     .append(" set \"cmis_id_sollevamento\" = ?")
                                     .append(", \"file_name_sollevamento\" = ?")
                                     .append(", \"hash_sollevamento\" = ?")
                                     .append(", \"date_update\" = current_timestamp")
                                     .append(" where \"id_pratica\" = ?")
                                     .append(" and \"stato\" = 'B'")
                                     .append(" and \"codice_fiscale\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(cmisIdSollevamento);
        parameters.add(fileNameSollevamento);
        parameters.add(hash);
        parameters.add(idPratica);
        parameters.add(SecurityUtil.getUserDetail().getFiscalCode());
        return super.update(sql, parameters);
    }
    
    public int deleteSollevamento(final UUID idPratica){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"subentro\"")
                                     .append(" set \"cmis_id_sollevamento\" = null")
                                     .append(", \"file_name_sollevamento\" = null")
                                     .append(", \"hash_sollevamento\" = null")
                                     .append(", \"date_update\" = current_timestamp")
                                     .append(" where \"id_pratica\" = ?")
                                     .append(" and \"stato\" = 'B'")
                                     .append(" and \"codice_fiscale\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        parameters.add(SecurityUtil.getUserDetail().getFiscalCode());
        return super.update(sql, parameters);
    }
    
    public String getCmisModulo(final UUID idPratica){
        final String sql = new StringBuilder("select \"cmis_id_modulo\"")
                                     .append(" from \"presentazione_istanza\".\"subentro\"")
                                     .append(" where \"id_pratica\" = :idPratica")
                                     .toString();
	    final Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put("idPratica", idPratica);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, String.class);
    }
    
    public String getCmisSollevamento(final UUID idPratica){
        final String sql = new StringBuilder("select \"cmis_id_sollevamento\"")
                                     .append(" from \"presentazione_istanza\".\"subentro\"")
                                     .append(" where \"id_pratica\" = :idPratica")
                                     .toString();
	    final Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put("idPratica", idPratica);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, String.class);
    }

}
