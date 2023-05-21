package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaOwnerHistoryDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PraticaOwnerHistoryRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaOwnerHistorySearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.pratica_owner_history
 */
@Repository
public class PraticaOwnerHistoryRepository extends GenericCrudDao<PraticaOwnerHistoryDTO, PraticaOwnerHistorySearch>{

    private final PraticaOwnerHistoryRowMapper rowMapper = new PraticaOwnerHistoryRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"username\"")
                                     .append(",\"codice_segreto_utilizzato\"")
                                     .append(",\"create_date\"")
                                     .append(",\"cmis_id_delega\"")
                                     .append(",\"file_name_delega\"")
                                     .append(",\"cmis_id_sollevamento_incarico\"")
                                     .append(",\"file_name_sollevamento_incarico\"")
                                     .append(" from \"presentazione_istanza\".\"pratica_owner_history\"")
                                     .toString();
    @Override
    public List<PraticaOwnerHistoryDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"pratica_owner_history\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PraticaOwnerHistoryDTO find(PraticaOwnerHistoryDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id\" = ?")
                                     .append(" and \"username\" = ?")
                                     .append(" and \"codice_segreto_utilizzato\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getId());
        parameters.add(pk.getUsername());
        parameters.add(pk.getCodiceSegretoUtilizzato());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<PraticaOwnerHistoryDTO> search(PraticaOwnerHistorySearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUsername())){
            sql.append(sep).append("lower(\"username\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUsername()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceSegretoUtilizzato())){
            sql.append(sep).append("lower(\"codice_segreto_utilizzato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCodiceSegretoUtilizzato()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCreateDate())){
            sql.append(sep).append("lower(\"create_date\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCreateDate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCmisIdDelega())){
            sql.append(sep).append("lower(\"cmis_id_delega\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCmisIdDelega()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFileNameDelega())){
            sql.append(sep).append("lower(\"file_name_delega\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getFileNameDelega()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCmisIdSollevamentoIncarico())){
            sql.append(sep).append("lower(\"cmis_id_sollevamento_incarico\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCmisIdSollevamentoIncarico()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFileNameSollevamentoIncarico())){
            sql.append(sep).append("lower(\"file_name_sollevamento_incarico\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getFileNameSollevamentoIncarico()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" order by \"id\" ").append(sortType);
                	   break;
            case "username":
                    sql.append(" order by \"username\" ").append(sortType);
                	   break;
            case "codiceSegretoUtilizzato":
                    sql.append(" order by \"codice_segreto_utilizzato\" ").append(sortType);
                	   break;
            case "createDate":
                    sql.append(" order by \"create_date\" ").append(sortType);
                	   break;
            case "cmisIdDelega":
                    sql.append(" order by \"cmis_id_delega\" ").append(sortType);
                	   break;
            case "fileNameDelega":
                    sql.append(" order by \"file_name_delega\" ").append(sortType);
                	   break;
            case "cmisIdSollevamentoIncarico":
                    sql.append(" order by \"cmis_id_sollevamento_incarico\" ").append(sortType);
                	   break;
            case "fileNameSollevamentoIncarico":
                    sql.append(" order by \"file_name_sollevamento_incarico\" ").append(sortType);
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
    public long insert(PraticaOwnerHistoryDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pratica_owner_history\"")
                                     .append("(\"id\"")
                                     .append(",\"username\"")
                                     .append(",\"codice_segreto_utilizzato\"")
                                     .append(",\"create_date\"")
                                     .append(",\"cmis_id_delega\"")
                                     .append(",\"file_name_delega\"")
                                     .append(",\"cmis_id_sollevamento_incarico\"")
                                     .append(",\"file_name_sollevamento_incarico\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",current_timestamp")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        parameters.add(entity.getUsername());
        parameters.add(entity.getCodiceSegretoUtilizzato());
        parameters.add(entity.getCmisIdDelega());
        parameters.add(entity.getFileNameDelega());
        parameters.add(entity.getCmisIdSollevamentoIncarico());
        parameters.add(entity.getFileNameSollevamentoIncarico());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(PraticaOwnerHistoryDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_owner_history\"")
                                     .append(" set \"create_date\" = ?")
                                     .append(", \"cmis_id_delega\" = ?")
                                     .append(", \"file_name_delega\" = ?")
                                     .append(", \"cmis_id_sollevamento_incarico\" = ?")
                                     .append(", \"file_name_sollevamento_incarico\" = ?")
                                     .append(" where \"id\" = ?")
                                     .append(" and \"username\" = ?")
                                     .append(" and \"codice_segreto_utilizzato\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getCreateDate());
        parameters.add(entity.getCmisIdDelega());
        parameters.add(entity.getFileNameDelega());
        parameters.add(entity.getCmisIdSollevamentoIncarico());
        parameters.add(entity.getFileNameSollevamentoIncarico());
        parameters.add(entity.getId());
        parameters.add(entity.getUsername());
        parameters.add(entity.getCodiceSegretoUtilizzato());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(PraticaOwnerHistoryDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pratica_owner_history\"")
                                     .append(" where \"id\" = ?")
                                     .append(" and \"username\" = ?")
                                     .append(" and \"codice_segreto_utilizzato\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        parameters.add(entity.getUsername());
        parameters.add(entity.getCodiceSegretoUtilizzato());
        return super.update(sql, parameters);
    }

}
