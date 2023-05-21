package it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.dto.LogProtocolloDTO;
import it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.rowmapper.LogProtocolloRowMapper;
import it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.search.LogProtocolloSearch;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table putt.log_protocollo
 */
@Repository
public class LogProtocolloRepository extends GenericCrudDao<LogProtocolloDTO, LogProtocolloSearch>{

    private final LogProtocolloRowMapper rowMapper = new LogProtocolloRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"request\"")
                                     .append(",\"response\"")
                                     .append(",\"protocollo\"")
                                     .append(",\"verso\"")
                                     .append(",\"data_protocollo\"")
                                     .append(",\"data_esecuzione\"")
                                     .append(",\"id_allegato\"")
                                     .append(" from \"log_protocollo\"")
                                     .toString();
    @Override
    public List<LogProtocolloDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"log_protocollo\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public LogProtocolloDTO find(LogProtocolloDTO pk){
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
    public PaginatedList<LogProtocolloDTO> search(LogProtocolloSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getRequest())){
            sql.append(sep).append("lower(\"request\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getRequest()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getResponse())){
            sql.append(sep).append("lower(\"response\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getResponse()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocollo())){
            sql.append(sep).append("lower(\"protocollo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocollo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getVerso())){
            sql.append(sep).append("lower(\"verso\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getVerso()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataProtocollo())){
            sql.append(sep).append("lower(\"data_protocollo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataProtocollo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataEsecuzione())){
            sql.append(sep).append("lower(\"data_esecuzione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataEsecuzione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdAllegato())){
            sql.append(sep).append("lower(\"id_allegato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdAllegato()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "request":
                    sql.append(" sort by \"request\" ").append(sortType);
            case "response":
                    sql.append(" sort by \"response\" ").append(sortType);
            case "protocollo":
                    sql.append(" sort by \"protocollo\" ").append(sortType);
            case "verso":
                    sql.append(" sort by \"verso\" ").append(sortType);
            case "dataProtocollo":
                    sql.append(" sort by \"data_protocollo\" ").append(sortType);
            case "dataEsecuzione":
                    sql.append(" sort by \"data_esecuzione\" ").append(sortType);
            case "idAllegato":
                    sql.append(" sort by \"id_allegato\" ").append(sortType);
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
    public long insert(LogProtocolloDTO entity){
        final String sql = new StringBuilder("insert into \"log_protocollo\"")
                                     .append("(\"request\"")
                                     .append(",\"response\"")
                                     .append(",\"protocollo\"")
                                     .append(",\"verso\"")
                                     .append(",\"data_protocollo\"")
                                     .append(",\"data_esecuzione\"")
                                     .append(",\"id_allegato\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getRequest());
        parameters.add(entity.getResponse());
        parameters.add(entity.getProtocollo());
        parameters.add(entity.getVerso());
        parameters.add(entity.getDataProtocollo());
        parameters.add(entity.getDataEsecuzione());
        parameters.add(entity.getIdAllegato());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(LogProtocolloDTO entity){
        final String sql = new StringBuilder("update \"log_protocollo\"")
                                     .append(" set \"request\" = ?")
                                     .append(", \"response\" = ?")
                                     .append(", \"protocollo\" = ?")
                                     .append(", \"verso\" = ?")
                                     .append(", \"data_protocollo\" = ?")
                                     .append(", \"data_esecuzione\" = ?")
                                     .append(", \"id_allegato\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getRequest());
        parameters.add(entity.getResponse());
        parameters.add(entity.getProtocollo());
        parameters.add(entity.getVerso());
        parameters.add(entity.getDataProtocollo());
        parameters.add(entity.getDataEsecuzione());
        parameters.add(entity.getIdAllegato());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(LogProtocolloDTO entity){
        final String sql = new StringBuilder("delete from \"log_protocollo\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

}
