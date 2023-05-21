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
 * Repository for table presentazione_istanza.configurazione
 */
@Repository
public class ConfigurazioneRepository extends GenericCrudDao<ConfigurazioneDTO, ConfigurazioneSearch>{

    private final ConfigurazioneRowMapper rowMapper = new ConfigurazioneRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"chiave\"")
                                     .append(",\"valore\"")
                                     .append(",\"start_date\"")
                                     .append(",\"end_date\"")
                                     .append(",\"user_create\"")
                                     .append(",\"user_update\"")
                                     .append(" from \"presentazione_istanza\".\"configurazione\"")
                                     .toString();
    @Override
    public List<ConfigurazioneDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"configurazione\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public ConfigurazioneDTO find(ConfigurazioneDTO pk){
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
    public PaginatedList<ConfigurazioneDTO> search(ConfigurazioneSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getChiave())){
            sql.append(sep).append("lower(\"chiave\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getChiave()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getValore())){
            sql.append(sep).append("lower(\"valore\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getValore()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStartDate())){
            sql.append(sep).append("lower(\"start_date\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStartDate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEndDate())){
            sql.append(sep).append("lower(\"end_date\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEndDate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUserCreate())){
            sql.append(sep).append("lower(\"user_create\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUserCreate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUserUpdate())){
            sql.append(sep).append("lower(\"user_update\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUserUpdate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" order by \"id\" ").append(sortType);
                	   break;
            case "chiave":
                    sql.append(" order by \"chiave\" ").append(sortType);
                	   break;
            case "valore":
                    sql.append(" order by \"valore\" ").append(sortType);
                	   break;
            case "startDate":
                    sql.append(" order by \"start_date\" ").append(sortType);
                	   break;
            case "endDate":
                    sql.append(" order by \"end_date\" ").append(sortType);
                	   break;
            case "userCreate":
                    sql.append(" order by \"user_create\" ").append(sortType);
                	   break;
            case "userUpdate":
                    sql.append(" order by \"user_update\" ").append(sortType);
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
    public long insert(ConfigurazioneDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"configurazione\"")
                                     .append("(\"chiave\"")
                                     .append(",\"valore\"")
                                     .append(",\"start_date\"")
                                     .append(",\"user_create\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getChiave());
        parameters.add(entity.getValore());
        parameters.add(entity.getStartDate());
        parameters.add(entity.getUserCreate());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ConfigurazioneDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"configurazione\"")
                                     .append(" set ")
                                     .append(" \"end_date\" = ?")
                                     .append(" ,\"user_update\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getEndDate());
        parameters.add(entity.getUserUpdate());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(ConfigurazioneDTO entity){
       throw new RuntimeException("Non implementato!!!");
    }

    
    public ConfigurazioneDTO findByChiaveEData(String chiave,Date data){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"chiave\" = ?")
                                     .append(" AND \"start_date\" <= ?")
                                     .append(" AND (\"end_date\" is null OR \"end_date\">= ? )")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(chiave);
        parameters.add(data);
        parameters.add(data);
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    public ConfigurazioneDTO findByChiaveLast(String chiave){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"chiave\" = ?")
                                     .append(" AND \"end_date\" is null ")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(chiave);
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    
}
