package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RupDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.RupRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.RupSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.rup
 */
@Repository
public class RupRepository extends GenericCrudDao<RupDTO, RupSearch>{

    private final RupRowMapper rowMapper = new RupRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id_organizzazione\"")
                                     .append(",\"username\"")
                                     .append(",\"denominazione\"")
                                     .append(",\"data_scadenza\"")
                                     .append(" from \"presentazione_istanza\".\"rup\"")
                                     .toString();
    @Override
    public List<RupDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"rup\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public RupDTO find(RupDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"username\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getIdOrganizzazione());
        parameters.add(pk.getUsername());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<RupDTO> search(RupSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getIdOrganizzazione())){
            sql.append(sep).append("lower(\"id_organizzazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdOrganizzazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUsername())){
            sql.append(sep).append("lower(\"username\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUsername()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDenominazione())){
            sql.append(sep).append("lower(\"denominazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDenominazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataScadenza())){
            sql.append(sep).append("lower(\"data_scadenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataScadenza()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "idOrganizzazione":
                    sql.append(" order by \"id_organizzazione\" ").append(sortType);
                	   break;
            case "username":
                    sql.append(" order by \"username\" ").append(sortType);
                	   break;
            case "denominazione":
                    sql.append(" order by \"denominazione\" ").append(sortType);
                	   break;
            case "dataScadenza":
                    sql.append(" order by \"data_scadenza\" ").append(sortType);
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
    public long insert(RupDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"rup\"")
                                     .append("(\"id_organizzazione\"")
                                     .append(",\"username\"")
                                     .append(",\"denominazione\"")
                                     .append(",\"data_scadenza\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getUsername());
        parameters.add(entity.getDenominazione());
        parameters.add(entity.getDataScadenza());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(RupDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"rup\"")
                                     .append(" set \"denominazione\" = ?")
                                     .append(", \"data_scadenza\" = ?")
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"username\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getDenominazione());
        parameters.add(entity.getDataScadenza());
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getUsername());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(RupDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"rup\"")
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"username\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getUsername());
        return super.update(sql, parameters);
    }
    
    public Boolean validRup(String username, Date dataValidita, Integer idOrganizzazione) throws Exception
    {
    	String sql = StringUtil.concateneString("select count(*) > 0 from ",
    											"\"presentazione_istanza\".\"rup\" ",
    											"where \"username\" = :username ",
    											"and \"id_organizzazione\" = :id_organizzazione ",
    											"and \"data_scadenza\" >= :data_validita");
    	Map<String, Object> parameters = new HashMap<String, Object>();
    	parameters.put("username", username);
    	parameters.put("id_organizzazione", idOrganizzazione);
    	parameters.put("data_validita", dataValidita);
    	logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return namedJdbcTemplateRead.queryForObject(sql, parameters, Boolean.class);
    }

}
