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
 * Repository for table presentazione_istanza.web_content
 */
@Repository
public class WebContentRepository extends GenericCrudDao<WebContentDTO, WebContentSearch>{

    private final WebContentRowMapper rowMapper = new WebContentRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"codice_contenuto\"")
                                     .append(",\"tooltip\"")
                                     .append(",\"contenuto\"")
                                     .append(" from \"presentazione_istanza\".\"web_content\"")
                                     .toString();
    @Override
    public List<WebContentDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"web_content\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public WebContentDTO find(WebContentDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"codice_contenuto\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getCodiceContenuto());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<WebContentDTO> search(WebContentSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getCodiceContenuto())){
            sql.append(sep).append("lower(\"codice_contenuto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCodiceContenuto()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTooltip())){
            sql.append(sep).append("lower(\"tooltip\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTooltip()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getContenuto())){
            sql.append(sep).append("lower(\"contenuto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getContenuto()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "codiceContenuto":
                    sql.append(" order by \"codice_contenuto\" ").append(sortType);
                	   break;
            case "tooltip":
                    sql.append(" order by \"tooltip\" ").append(sortType);
                	   break;
            case "contenuto":
                    sql.append(" order by \"contenuto\" ").append(sortType);
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
    public long insert(WebContentDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"web_content\"")
                                     .append("(\"codice_contenuto\"")
                                     .append(",\"tooltip\"")
                                     .append(",\"contenuto\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getCodiceContenuto());
        parameters.add(entity.getTooltip());
        parameters.add(entity.getContenuto());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(WebContentDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"web_content\"")
                                     .append(" set \"tooltip\" = ?")
                                     .append(", \"contenuto\" = ?")
                                     .append(" where \"codice_contenuto\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getTooltip());
        parameters.add(entity.getContenuto());
        parameters.add(entity.getCodiceContenuto());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(WebContentDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"web_content\"")
                                     .append(" where \"codice_contenuto\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getCodiceContenuto());
        return super.update(sql, parameters);
    }

}
