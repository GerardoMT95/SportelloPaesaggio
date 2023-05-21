package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.*;

/**
 * Repository for table presentazione_istanza.privacy
 */
@Repository
public class PrivacyRepository extends GenericCrudDao<PrivacyDTO, PrivacySearch>{

    private final PrivacyRowMapper rowMapper = new PrivacyRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"testo\"")
                                     .append(",\"data_inizio\"")
                                     .append(",\"data_fine\"")
                                     .append(",\"user_inserted\"")
                                     .append(",\"user_updated\"")
                                     .append(" from \"presentazione_istanza\".\"privacy\"")
                                     .toString();
    @Override
    public List<PrivacyDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }
    
    public List<PrivacyDTO> selectCurrent(Date data){
    	final List<Object> parameters = new ArrayList<Object>();
        parameters.add(data);
        parameters.add(data);
        return super.queryForListTxRead(selectAll+ " where data_inizio<= ? and (data_fine is null or data_fine>= ? )", this.rowMapper,parameters);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"privacy\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PrivacyDTO find(PrivacyDTO pk){
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
    public PaginatedList<PrivacyDTO> search(PrivacySearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTesto())){
            sql.append(sep).append("lower(\"testo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTesto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataInizio())){
            sql.append(sep).append("lower(\"data_inizio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataInizio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataFine())){
            sql.append(sep).append("lower(\"data_fine\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataFine()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "testo":
                    sql.append(" sort by \"testo\" ").append(sortType);
            case "dataInizio":
                    sql.append(" sort by \"data_inizio\" ").append(sortType);
            case "dataFine":
                    sql.append(" sort by \"data_fine\" ").append(sortType);
                break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        }
        return super.paginatedList(sql.toString(), this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(PrivacyDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"privacy\"")
                                     .append("(\"testo\"")
                                     .append(",\"data_inizio\"")
                                     .append(",\"user_inserted\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getTesto());
        parameters.add(entity.getDataInizio());
        parameters.add(entity.getUserInserted());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(PrivacyDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"privacy\"")
                                     .append(" set ")
                                     .append(" \"data_fine\" = ?")
                                     .append(", \"user_updated\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getDataFine());
        parameters.add(entity.getUserUpdated());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(PrivacyDTO entity){
    	throw new RuntimeException("Non implementato!!!");
    }

}
