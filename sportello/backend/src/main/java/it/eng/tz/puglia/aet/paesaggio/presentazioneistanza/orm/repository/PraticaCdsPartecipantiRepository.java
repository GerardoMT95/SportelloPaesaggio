package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.*;
import java.math.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.*;

/**
 * Repository for table presentazione_istanza.pratica_cds_partecipanti
 */
@Repository
public class PraticaCdsPartecipantiRepository extends GenericCrudDao<PraticaCdsPartecipantiDTO, PraticaCdsPartecipantiSearch>{

    private final PraticaCdsPartecipantiRowMapper rowMapper = new PraticaCdsPartecipantiRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"username\"")
                                     .append(",\"denominazione\"")
                                     .append(" from \"presentazione_istanza\".\"pratica_cds_partecipanti\"")
                                     .toString();
    @Override
    public List<PraticaCdsPartecipantiDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"pratica_cds_partecipanti\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PraticaCdsPartecipantiDTO find(PraticaCdsPartecipantiDTO pk){
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
    public PaginatedList<PraticaCdsPartecipantiDTO> search(PraticaCdsPartecipantiSearch search){
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
        if(StringUtil.isNotEmpty(search.getDenominazione())){
            sql.append(sep).append("lower(\"denominazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDenominazione()).toLowerCase());
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
            case "denominazione":
                    sql.append(" order by \"denominazione\" ").append(sortType);
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
    public long insert(PraticaCdsPartecipantiDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pratica_cds_partecipanti\"")
                                     .append("(\"id\"")
                                     .append(",\"username\"")
                                     .append(",\"denominazione\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        parameters.add(entity.getUsername());
        parameters.add(entity.getDenominazione());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(PraticaCdsPartecipantiDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_cds_partecipanti\"")
                                     .append(" set \"username\" = ?")
                                     .append(", \"denominazione\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getUsername());
        parameters.add(entity.getDenominazione());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(PraticaCdsPartecipantiDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pratica_cds_partecipanti\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    public int delete(String id){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pratica_cds_partecipanti\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        return super.update(sql, parameters);
    }

}
